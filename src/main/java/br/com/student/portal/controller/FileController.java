package br.com.student.portal.controller;

import br.com.student.portal.entity.FileEntity;
import br.com.student.portal.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileEntity> uploadVideo(@RequestBody FileEntity fileEntity){
        return ResponseEntity.status(CREATED).body(fileService.createFile(fileEntity));
    }

}
