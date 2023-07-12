package org.zerock.j2.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

@Component
@Log4j2
public class FileUploader {

    public static class UploadException extends RuntimeException{
        
        public UploadException(String msg){
            super(msg);
        }
    }
    
    @Value("${org.zerock.upload.path}")
    private String path;

    public List<String> uploadFiles(List<MultipartFile> files, boolean makeThumbnail){

        if(files == null || files.size() == 0) {
            throw new UploadException("No File");
        }

        List<String> uploadFileNames = new ArrayList<>();
        
        log.info("path: " + path);

        log.info(files);

        //loop
        for (MultipartFile mFile : files) {

            String originalFileName = mFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();

            // save할 파일이름
            String saveFileName = uuid+"_"+originalFileName;
            File saveFile = new File(path, saveFileName);

            
            // 예외처리
            try ( InputStream in = mFile.getInputStream();
                  OutputStream out = new FileOutputStream(saveFile);
            ) {

                // 파일 Copy
                FileCopyUtils.copy(in, out);

                // 섬네일이 필요한지 필요하지 않은지...
                if(makeThumbnail) {

                    // 섬네일 생성
                    File thumOutFile = new File(path, "s_" + saveFileName);
                    Thumbnailator.createThumbnail(saveFile, thumOutFile, 200, 200);

                }                

                uploadFileNames.add(saveFileName);
                
            } catch (Exception e) {
                throw new UploadException("Upload Fail: " + e.getMessage());
            }
            
        }

        return uploadFileNames;
    }
}
