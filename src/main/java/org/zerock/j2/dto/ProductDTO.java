package org.zerock.j2.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long pno;
    private String pname;
    private String pdesc;
    private int price;

    //DB 처리 용도
    @Builder.Default
    private List<String> images = new ArrayList<>(); // 문자열

    //등록,수정 업로드된 파일 데이터를 수집하는 용도
    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>(); //업로드 파일
    
}
