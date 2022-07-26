package com.team2.cafein.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FileService {

//    @Value("C:/Temp/images")
    @Value("${file.upload.path}")
    private String fileUploadPath;

    public String getFullFileUploadPath(String filename) {
        return fileUploadPath + filename;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {

        if(multipartFile.isEmpty()) {
            return null;
        }

        // 원본 파일 이름
        String originalFilename = multipartFile.getOriginalFilename();
        // 저장된 파일 이름
        String storeFileName = createStoreFileName(originalFilename);
        // 파일 저장 경로
        String fileUploadUrl = getFullFileUploadPath(storeFileName);
        multipartFile.transferTo(new File(getFullFileUploadPath(storeFileName)));

        return new UploadFile(originalFilename, storeFileName, fileUploadUrl);
    }

    private String  createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        System.out.println("uuid : " + uuid);
        String ext = extractExt(originalFilename);
        System.out.println("ext : " + ext);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        System.out.println("pos : " + pos);
        System.out.println("originalFilename.substring : " + originalFilename.substring(pos + 1));
        return originalFilename.substring(pos + 1);
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                UploadFile uploadFile = storeFile(multipartFile);
                storeFileResult.add(uploadFile);
            }
        }
        return storeFileResult;
    }

    public void deleteFile(String fileUploadUrl) {
        File deleteFile = new File(fileUploadUrl);
        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}