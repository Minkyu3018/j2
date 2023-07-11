package org.zerock.j2.repository;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.entity.FileBoard;
import org.zerock.j2.entity.FileBoardImage;

import jakarta.transaction.Transactional;

@SpringBootTest
public class RepositoryTests {

    @Autowired
    FileBoardRepository repository;

    @Test
    public void insert(){

        for(int i = 0; i < 100; i++) {
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

            repository.save(fileBoard);

        }//end for
        


    }

    @Test // 삭제TEST
    @Transactional
    @Commit
    public void testRemove() {

        Long bno = 2L;

        repository.deleteById(bno);
    }

    @Test // 조회
    @Transactional
    public void testRead() {

        Long bno = 100L;

        Optional<FileBoard> result = repository.findById(bno);

        FileBoard board = result.orElseThrow();

        System.out.println(board);

    }

    @Test
    @Transactional
    public void testList() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<FileBoard> result = repository.findAll(pageable);

        // System.out.println(result);

        result.get().forEach(board -> {
        System.out.println(board);
        System.out.println(board.getImages());
        });
    }

    
    @Test
    @Transactional
    public void testListQuertdsl(){

        PageRequestDTO requestDTO = new PageRequestDTO();

        repository.list(requestDTO);
    }
}
