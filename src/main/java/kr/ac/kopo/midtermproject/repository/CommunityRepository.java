package kr.ac.kopo.midtermproject.repository;

import kr.ac.kopo.midtermproject.entity.Community;
import kr.ac.kopo.midtermproject.repository.search.SearchCommunityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long>, SearchCommunityRepository {
    @Query("select c, w from Community c left join c.writer w where c.bno=:bno")
    Object getCommunityWithWriter(@Param("bno") Long bno);

    @Query("select c, r from Community c left join Reply r ON r.community = c where c.bno=:bno")
    List<Object[]> getCommunityWithReply(@Param("bno") Long bno);

    @Query(value = "select c, w, count(r) from Community c " +
            "left join c.writer w " +
            "left join Reply r on r.community = c " +
            "group by c, w",
            countQuery = "select count(c) from Community c")
    Page<Object[]> getCommunityWithReplyCount(Pageable pageable);

    @Query("select c, w, count(r) " +
            "from Community c left join c.writer w " +
            "left outer join Reply r ON r.community=c " +
            "where c.bno=:bno group by c, w")
    Object getCommunityByBno(@Param("bno")Long bno);
}
