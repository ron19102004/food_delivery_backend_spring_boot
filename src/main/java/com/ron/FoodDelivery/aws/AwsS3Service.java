package com.ron.FoodDelivery.aws;

import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {
    String upload(MultipartFile multipartFile, String folderName);

    void delete(String fileName, String folder);
}
