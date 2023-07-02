package com.digitalhouse.digitalexpirience.service;

import com.digitalhouse.digitalexpirience.dto.request.S3BucketDTO;
import org.springframework.web.multipart.MultipartFile;

public interface S3BucketService {
    String uploadFileS3Bucket(MultipartFile file, S3BucketDTO s3BucketDTO);

    String uploadUserFilesInBase64(S3BucketDTO s3BucketDTO);

}
