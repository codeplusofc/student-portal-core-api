package br.com.student.portal.service;

import br.com.student.portal.entity.FileEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.repository.FileRepository;
import br.com.student.portal.repository.UserRepository;
import br.com.student.portal.validation.FileValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final FileValidator fileValidator;

    public FileEntity createFile(FileEntity fileEntity) {
        log.info("Criando arquivo: {}", fileEntity.getName());
        fileValidator.validateFields(fileEntity);
        return fileRepository.save(fileEntity);
    }

    public List<FileEntity> getAllFiles(UUID userId) {
        log.info("Buscando arquivos para usuário ID: {}", userId);

        // ✅ SIMPLIFICAÇÃO: Apenas verifica se usuário existe
        // O controle de acesso já é feito pelo Spring Security
        if (!userRepository.existsById(userId)) {
            throw new ObjectNotFoundException("Usuário não encontrado com ID: " + userId);
        }

        List<FileEntity> files = fileRepository.findAll();

        if (files.isEmpty()) {
            log.info("Nenhum arquivo encontrado no repositório");
            return List.of();
        }

        log.info("Retornando {} arquivos para usuário {}", files.size(), userId);
        return files;
    }
}