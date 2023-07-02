package com.digitalhouse.digitalexpirience.controller;

import com.digitalhouse.digitalexpirience.dto.request.S3BucketDTO;
import com.digitalhouse.digitalexpirience.service.S3BucketService;
import com.digitalhouse.digitalexpirience.dto.response.ApiResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Locale;


@CrossOrigin
@RestController
@RequestMapping("/files")
public class S3BucketController implements Serializable {


    @Autowired
    S3BucketService s3BucketService;

    @PostMapping(value = "/upload")
    public ResponseEntity<?> uploadFile(@RequestPart(value = "file") MultipartFile file,
                                        @RequestPart(value = "data") @Valid S3BucketDTO s3BucketDTO) {
        var uploadURL = s3BucketService.uploadFileS3Bucket(file, s3BucketDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(uploadURL, "Upload Success ", HttpStatus.CREATED));

    }


    @PostMapping(value = "/upload-base64")
    public ResponseEntity<?> uploadUserFilesInBase64(@Valid @RequestBody S3BucketDTO s3BucketDTO) {
        var uploadURL = s3BucketService.uploadUserFilesInBase64(s3BucketDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(uploadURL, "Upload Success ", HttpStatus.CREATED));

    }
}
