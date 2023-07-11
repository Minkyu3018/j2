package org.zerock.j2.repository;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.j2.entity.FileBoard;
import org.zerock.j2.entity.FileBoardImage;

@SpringBootTest
public class RepositoryTests {

    @Autowired
    FileBoardRepository repository;

    @Test
    public void insert(){

        FileBoard fileBoard = FileBoard.builder()
        .title("aa")
        .content("aa")
        .writer("aa")
        .build();

        FileBoardImage img1 = FileBoardImage.builder()
        .uuid(UUID.randomUUID().toString())
        .fname("aaa.jpg")
        .build();

        FileBoardImage img2 = FileBoardImage.builder()
        .uuid(UUID.randomUUID().toString())
        .fname("bbb.jpg")
        .build();

        fileBoard.addImage(img1);

        fileBoard.addImage(img2);

        


    }
}
