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
        fileValidator.validateFields(fileEntity);
        return fileRepository.save(fileEntity);
    }

    public List<FileEntity> getAllFiles(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new ObjectNotFoundException("Usuário não encontrado com ID: " + userId);
        }

        List<FileEntity> files = fileRepository.findAll();

        if (files.isEmpty()) {
            return List.of();
        }
        return files;
    }
}