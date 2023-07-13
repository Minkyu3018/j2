package org.zerock.j2.controller;


import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ProductDTO;
import org.zerock.j2.dto.ProductListDTO;
import org.zerock.j2.service.ProductService;
import org.zerock.j2.util.FileUploader;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin
@RequestMapping("/api/products/")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    private final ProductService service;
    private final FileUploader uploader;

    @PostMapping("")
    public Map<String, Long> register(ProductDTO productDTO) {

        log.info(productDTO);

        List<String> fileNames = uploader.uploadFiles(productDTO.getFiles(), true);
        productDTO.setImages(fileNames);

        Long pno = service.register(productDTO);


        return Map.of("result", pno);
    }

    @PostMapping("modify")
    public Map<String, Long> modify(ProductDTO productDTO) {

        log.info("-------------modify----------------------");
        log.info("-------------modify----------------------");
        log.info("-------------modify----------------------");
        log.info(productDTO);

        if(productDTO.getFiles() != null && productDTO.getFiles().size() > 0) {

            List<String> uploadFileNames = uploader.uploadFiles(productDTO.getFiles(),true);

            // 원래 있던 파일들
            List<String> oldFileNames = productDTO.getImages();

            uploadFileNames.forEach(fname -> oldFileNames.add(fname));
        }



        log.info("After..........................");
        log.info(productDTO);

        log.info("After..........................");

        service.modify(productDTO);

        return Map.of("result", 111L);
    }

    @GetMapping(value = "list")
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {

        log.info("------------------------------");
        log.info(pageRequestDTO);

        return service.list(pageRequestDTO);
    }

    @GetMapping("{pno}")
    public ProductDTO getOne(@PathVariable("pno") Long pno) {

        log.info("PNO......" + pno);

        return service.readOne(pno);
    }

    @DeleteMapping("{pno}")
    public Map<String, Long> delete(@PathVariable("pno") Long pno) {

        log.info("PNO......" + pno);
        service.remove(pno);

        return Map.of("result", pno);

    }


}
