package kr.ac.kopo.midtermproject.repository.search;

import kr.ac.kopo.midtermproject.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {
    Board search1();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);

}
