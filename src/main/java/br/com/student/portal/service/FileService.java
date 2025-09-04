package br.com.student.portal.service;

import br.com.student.portal.entity.FileEntity;

import br.com.student.portal.exception.ForbiddenException;
import br.com.student.portal.repository.FileRepository;
import br.com.student.portal.repository.UserRepository;
import br.com.student.portal.validation.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;



@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileValidator fileValidator;



    public FileEntity createFile(FileEntity fileEntity) {
        fileValidator.validateFields(fileEntity);
        return fileRepository.save(fileEntity);
    }

    public List<FileEntity> getAllFiles(UUID id){
        if(userRepository.findById(id).get().isAccessEnable()){
            if (fileRepository.findAll().isEmpty()){
                throw new NullPointerException("There's no file in this repository");
            }return fileRepository.findAll();
        }else{
            throw new ForbiddenException("You don't have access to this feature");

        }
    }



}

