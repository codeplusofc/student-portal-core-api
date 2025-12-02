package br.com.student.portal.service;

import br.com.student.portal.dto.material.MaterialRequest;
import br.com.student.portal.dto.material.MaterialResponse;
import br.com.student.portal.entity.MaterialEntity;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final Path rootLocation = Paths.get("uploads");

    public MaterialResponse getMaterialById(UUID id) {
        log.info("Buscando material por ID: {}", id);
        MaterialEntity material = materialRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Material não encontrado"));
        return mapToResponse(material);
    }

    @Transactional(readOnly = true)
    public List<MaterialResponse> getAllMaterials() {
        log.info("Buscando todos os materiais");
        return materialRepository.findAllOrderByNewest()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<MaterialResponse> getAllMaterials(Pageable pageable) {
        log.info("Buscando materiais paginados");
        return materialRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public MaterialResponse createMaterial(MaterialRequest request, MultipartFile file, UserEntity uploadedBy) throws IOException {
        log.info("Criando material: {}", request.getName());

        // Garantir diretório de uploads
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }

        // Salvar arquivo
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path destinationFile = rootLocation.resolve(filename);
        Files.copy(file.getInputStream(), destinationFile);

        MaterialEntity material = MaterialEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(MaterialEntity.MaterialCategory.valueOf(request.getCategory().toUpperCase()))
                .filename(filename)
                .uploadedBy(uploadedBy)
                .uploadDate(LocalDateTime.now())
                .downloads(0L)
                .build();

        MaterialEntity savedMaterial = materialRepository.save(material);
        return mapToResponse(savedMaterial);
    }

    public MaterialResponse updateMaterial(UUID id, MaterialRequest request, UserEntity uploadedBy) {
        log.info("Atualizando material ID: {}", id);

        MaterialEntity existingMaterial = materialRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Material não encontrado"));

        // Verificar se o usuário é o uploader
        if (!existingMaterial.getUploadedBy().getId().equals(uploadedBy.getId())) {
            throw new RuntimeException("Apenas o uploader pode editar o material");
        }

        existingMaterial.setName(request.getName());
        existingMaterial.setDescription(request.getDescription());
        if (request.getCategory() != null) {
            existingMaterial.setCategory(MaterialEntity.MaterialCategory.valueOf(request.getCategory().toUpperCase()));
        }

        MaterialEntity updatedMaterial = materialRepository.save(existingMaterial);
        return mapToResponse(updatedMaterial);
    }

    public void deleteMaterial(UUID id, UserEntity requester) {
        log.info("Deletando material ID: {}", id);

        MaterialEntity material = materialRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Material não encontrado"));

        // Verificar se o usuário é o uploader ou admin
        boolean isUploader = material.getUploadedBy().getId().equals(requester.getId());
        boolean isAdmin = requester.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        if (!isUploader && !isAdmin) {
            throw new RuntimeException("Não autorizado a deletar este material");
        }

        // Tentar deletar o arquivo físico
        try {
            Path filePath = rootLocation.resolve(material.getFilename());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.warn("Não foi possível deletar o arquivo físico: {}", material.getFilename());
        }

        materialRepository.delete(material);
    }

    @Transactional(readOnly = true)
    public List<MaterialResponse> getMaterialsByCategory(String category) {
        log.info("Buscando materiais por categoria: {}", category);

        try {
            MaterialEntity.MaterialCategory categoryEnum =
                    MaterialEntity.MaterialCategory.valueOf(category.toUpperCase());

            return materialRepository.findByCategory(categoryEnum)
                    .stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new ObjectNotFoundException("Categoria não encontrada: " + category);
        }
    }

    @Transactional(readOnly = true)
    public List<MaterialResponse> searchMaterials(String term) {
        log.info("Buscando materiais com termo: {}", term);
        return materialRepository.searchByName(term)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MaterialResponse> getMaterialsByUploader(UUID userId) {
        log.info("Buscando materiais do usuário ID: {}", userId);
        return materialRepository.findByUploadedById(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MaterialResponse> getMostDownloadedMaterials(int limit) {
        log.info("Buscando {} materiais mais baixados", limit);

        // ✅ CRITICAL FIX: Usar Pageable em vez de LIMIT no JPQL
        Pageable pageable = PageRequest.of(0, limit);
        return materialRepository.findAllByOrderByDownloadsDesc(pageable)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void incrementDownloads(UUID id) {
        log.info("Incrementando downloads do material ID: {}", id);
        MaterialEntity material = materialRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Material não encontrado"));

        material.setDownloads(material.getDownloads() + 1);
        materialRepository.save(material);
    }

    public byte[] downloadMaterial(UUID id) throws IOException {
        log.info("Download do material ID: {}", id);
        MaterialEntity material = materialRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Material não encontrado"));

        Path filePath = rootLocation.resolve(material.getFilename());
        if (!Files.exists(filePath)) {
            throw new ObjectNotFoundException("Arquivo físico não encontrado");
        }

        // Incrementar contador de downloads
        incrementDownloads(id);

        return Files.readAllBytes(filePath);
    }

    private MaterialResponse mapToResponse(MaterialEntity entity) {
        return MaterialResponse.builder()
                .id(entity.getId().toString())
                .name(entity.getName())
                .description(entity.getDescription())
                .filename(entity.getFilename())  // ✅ AGORA funciona!
                .category(entity.getCategory().name())
                .uploaderId(entity.getUploadedBy().getId().toString())
                .uploaderName(entity.getUploadedBy().getName())
                .downloads(entity.getDownloads())
                .uploadDate(entity.getUploadDate() != null ? entity.getUploadDate().toString() : "")
                .build();
    }
}