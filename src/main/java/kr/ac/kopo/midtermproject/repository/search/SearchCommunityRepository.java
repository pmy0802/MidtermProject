package kr.ac.kopo.midtermproject.repository.search;

import kr.ac.kopo.midtermproject.entity.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchCommunityRepository {
    Community search1();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);

}
