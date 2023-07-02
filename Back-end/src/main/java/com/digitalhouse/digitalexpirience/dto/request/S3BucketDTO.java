package com.digitalhouse.digitalexpirience.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class S3BucketDTO {

    private String fileName;
    private String fileType;
    private String encodeBase64;


}
