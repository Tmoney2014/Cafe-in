package com.team2.cafein.service;

import com.team2.cafein.infra.FileService;
import com.team2.cafein.infra.UploadFile;
import com.team2.cafein.model.CoffeeImg;
import com.team2.cafein.model.Post;
import com.team2.cafein.repository.CoffeeImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoffeeImgService {
    private final CoffeeImgRepository coffeeImgRepository;
    private final FileService fileService;
    private final String IMAGE_URL_PREFIX = "/images/";

//    @Transactional
//    public CoffeeImg savePostImage(Post post) throws IOException {
//        return savePostImageMethod(post);
//    }
//
//    @Transactional
//    public CoffeeImg savePostImageMethod(Post post) throws IOException {
//
//        UploadFile uploadFile = fileService.storeFile(postImageFile);
//        String fileUploadUrl = uploadFile.getFileUploadUrl();
//        // 저장된 파일 이름
//        String storeFileName = uploadFile != null ? uploadFile.getStoreFileName() : "";
//        // 원본 파일 이름
//        String originalFilename = uploadFile != null ? uploadFile.getOriginalFileName() : "";
//        // 파일 저장 경로
//        String imageUrl = uploadFile != null ? IMAGE_URL_PREFIX + storeFileName : "";
//
//        CoffeeImg coffeeImg = CoffeeImg.builder()
//                .imageName(storeFileName)
//                .originalImageName(originalFilename)
//                .imageUrl(imageUrl)
//                .post(post)
//                .build();
//        System.out.println("coffeeImg.toString() : " + coffeeImg.getPost().toString());
//
//        CoffeeImg savePostImage = CoffeeImg.createPostImage(coffeeImg, post);
//        System.out.println("savePostImage : " + savePostImage.getPost().toString()); // 둘이 일치 확인완료
//
//        return coffeeImgRepository.save(savePostImage);
//    }
//
//    public List<CoffeeImg> findByPostOrderByIdAsc(Post post) {
//        return coffeeImgRepository.findByPostOrderByIdAsc(post);
//    }
//
//    @Transactional
//    public void updatePostImage(CoffeeImg coffeeImg, MultipartFile itemImageFile) throws IOException {
//        // 기존 상품 이미지 파일이 존재하는 경우 파일 삭제
//        if(StringUtils.hasText(coffeeImg.getImageName())) {
//            System.out.println(coffeeImg.getImageUrl());
//            fileService.deleteFile(coffeeImg.getImageUrl());
//        }
//
//        // 새로운 이미지 파일 등록
//        UploadFile uploadFile = fileService.storeFile(itemImageFile);
//        String originalFilename = uploadFile.getOriginalFileName();
//        String storeFileName = uploadFile.getStoreFileName();
//        String imageUrl = IMAGE_URL_PREFIX + storeFileName;
//
//        // 상품 이미지 파일 정보 업데이트
//        coffeeImg.updatePostImage(originalFilename, storeFileName, imageUrl);
//    }
//
//    @Transactional
//    public void deletePostImage(CoffeeImg coffeeImg) throws IOException {
//        // 기존 이미지 파일 삭제
//        String fileUploadUrl = fileService.getFullFileUploadPath(coffeeImg.getImageName());
//        System.out.println(fileUploadUrl);
//        fileService.deleteFile(fileUploadUrl);
//        // 이미지 정보 초기화
//        coffeeImg.initPostInfo();
//    }
//
//    public CoffeeImg findByPost(Post post) {
//        return coffeeImgRepository.findByPost(post);
//    }
//    //
}
