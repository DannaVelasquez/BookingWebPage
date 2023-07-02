package com.digitalhouse.digitalexpirience.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.digitalhouse.digitalexpirience.dto.request.S3BucketDTO;
import com.digitalhouse.digitalexpirience.exception.BusinessException;
import com.digitalhouse.digitalexpirience.model.enums.FileType;
import com.digitalhouse.digitalexpirience.service.S3BucketService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("S3BucketService")
public class S3BucketServiceImpl implements S3BucketService {


    @Value("${bucketName}")
    private String bucketName;

    @Value("${s3Region}")
    private String s3Region;

    @Value("${endpointUrl}")
    private String endpointUrl;


    @Autowired
    private AmazonS3 s3Client;


    final String S3_BUCKET = "web-files/";

    @SneakyThrows
    @Override
    public String uploadFileS3Bucket(MultipartFile file, S3BucketDTO s3BucketDTO) {

        try {
            StringBuilder directory = new StringBuilder(createDirectoryUserData(s3BucketDTO));
            log.info("Ejecutando Servicio para la carga de archivos en s3 Bucket");
            var s3Directory = uploadFile(file, directory, s3BucketDTO);

            StringBuilder urlObject = new StringBuilder("https://");

            urlObject.append(bucketName).append(".s3.").append(s3Region).append(".")
                    .append("amazonaws.com/").append(s3Directory);

            return String.valueOf(urlObject);

        } catch (Exception e) {
            throw new BusinessException("Error en el servicio upload en AWS S3");
        }

    }




    private StringBuilder createDirectoryUserData(S3BucketDTO s3BucketDTO) {
        StringBuilder userDirectory = new StringBuilder();
        StringBuilder fileUrl = new StringBuilder();


        switch (s3BucketDTO.getFileType()) {

            case "PROFILE_IMAGE" -> {
                userDirectory.append("profile-image/");
                fileUrl.append(S3_BUCKET).append(userDirectory);

                log.info("Directorio de foto de perfil creado con exito: '{}'", fileUrl);
            }

            case "UPLOAD_FILE" -> {

                userDirectory.append("files/");
                fileUrl.append(S3_BUCKET).append(userDirectory);

                log.info("Directorio de archivos creado con exito: '{}'", fileUrl);
            }

            default -> log.info("Formato de archivo incorrecto");

        }
        return fileUrl;
    }


    private String uploadFile(MultipartFile file, StringBuilder directory, S3BucketDTO s3BucketDTO) {

        List<String> files = getObjectFromS3(String.valueOf(directory));


        if (s3BucketDTO.getFileType().equals(FileType.PROFILE_IMAGE.toString())) {

            if (!files.isEmpty()) {
                String fileName = files.get(0);
                log.info("Eliminando archivo anterior: '{}'", fileName);
                deleteFile(fileName);

            }

        }

        File createdFile = convertMultiPartFileToFile(file);
        String finalDirectory = directory + createdFile.getName();

        log.info("Subiendo archivo con la URI -> : " + bucketName + "/" + finalDirectory);
        PutObjectRequest request = new PutObjectRequest(bucketName, finalDirectory, createdFile);
        s3Client.putObject(request);
        log.info("Se subió correctamente el archivo con la URI S3 -> : " + bucketName + "/" + finalDirectory);
        createdFile.delete();

        return finalDirectory;

    }

    public List<String> getObjectFromS3(String prefix) {

        ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName).withPrefix(prefix)
                .withDelimiter("/");
        ListObjectsV2Result result = s3Client.listObjectsV2(req);

        List<S3ObjectSummary> objects = result.getObjectSummaries();
        List<String> list = objects.stream().map(item -> {
            return item.getKey();
        }).collect(Collectors.toList());
        return list;
    }


    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error al convertir multipartFile a file ", e);
        }
        return convertedFile;
    }

    private void deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
    }


    @SneakyThrows
    @Override
    public String uploadUserFilesInBase64(S3BucketDTO s3BucketDTO) {


        try {

            StringBuilder directory = new StringBuilder(createDirectoryUserData(s3BucketDTO));

            byte[] bytes = Base64.getDecoder().decode(s3BucketDTO.getEncodeBase64());
            InputStream inputStream = new ByteArrayInputStream(bytes);

            String finalDirectory = directory + s3BucketDTO.getFileName();

            log.info("Subiendo archivo con la URI -> : " + bucketName + "/" + finalDirectory);
            s3Client.putObject(new PutObjectRequest(bucketName, finalDirectory, inputStream, new ObjectMetadata()));
            log.info("Se subió correctamente el archivo con la URI S3 -> : " + bucketName + "/" + finalDirectory);


            StringBuilder urlObject = new StringBuilder("https://");

            urlObject.append(bucketName).append(".s3.").append(s3Region).append(".")
                    .append("amazonaws.com/").append(finalDirectory);

            return String.valueOf(urlObject);

        } catch (RuntimeException e) {
            throw new BusinessException("Error al intentar subir una imagen al bucket AWS S3");
        }

    }

}
