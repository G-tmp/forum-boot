package com.gtmp.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class UploadUtil {


    private static final String[] WHITELIST = new String[]{"png", "jpg", "bmp", "gif", "svg", "webp", "jpeg"};

    public static String upload(MultipartFile image, String path) throws IOException {
        if (image == null)
            throw new IllegalArgumentException("file can not be null");

        String fileName = image.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        List<String> imageList = Arrays.asList(WHITELIST);

        if (suffix.length() == 0 || imageList.contains(suffix)) {
            throw new IllegalArgumentException("file format not support");
        }

        String saveName = System.currentTimeMillis() + suffix;
        File saveFile = new File(path, saveName);
        System.out.println("save file : " + saveFile);

        image.transferTo(saveFile);

        return saveName;
//        return saveFile.toString();
    }
}
