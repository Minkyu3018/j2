package org.zerock.j2.repository.search;

import org.zerock.j2.dto.FileBoardListDTO;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;

public interface FileBoardSearch {
    
    // fileBoardSearch에 검색기능
    PageResponseDTO<FileBoardListDTO> list(PageRequestDTO pageRequestDTO);

}



