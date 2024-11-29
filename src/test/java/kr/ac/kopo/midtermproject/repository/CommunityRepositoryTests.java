package kr.ac.kopo.midtermproject.repository;

import kr.ac.kopo.midtermproject.entity.Community;
import kr.ac.kopo.midtermproject.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class CommunityRepositoryTests {
    @Autowired
    private CommunityRepository communityRepository;

    @Test
    public void insertCommunity(){
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Member member = Member.builder()
                    .email("user"+i+"@kopo.ac.kr")
                    .build();

            Community community = Community.builder()
                    .title("Title" + i)
                    .content("Content" + i)
                    .writer(member)
                    .build();

            communityRepository.save(community);
        });
    }

    @Transactional
    @Test
    public void testRead(){
        Optional<Community> result = communityRepository.findById(5L);
        Community community = result.get();

        System.out.println(community);
        System.out.println(community.getWriter());
    }

    @Test
    public void testReadWithWriter(){
        Object result = communityRepository.getCommunityWithWriter(10L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testReadWithReply(){
        List<Object[]> result = communityRepository.getCommunityWithReply(77L);
        for (Object[] arr: result){
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testBoardWithReplyCount(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Object[]> result = communityRepository.getCommunityWithReplyCount(pageable);

        result.get().forEach(row ->{
            Object[] arr = (Object[]) row;
            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testRead3(){
        Object result = communityRepository.getCommunityByBno(99L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));

    }

    @Test
    public void testSearch1(){
        communityRepository.search1();
    }

    @Test
    public void testSearchPage(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending().and(Sort.by("title").ascending()));
        communityRepository.searchPage("t", "1", pageable);
    }
}
