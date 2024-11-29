package kr.ac.kopo.midtermproject.repository;

import kr.ac.kopo.midtermproject.entity.Community;
import kr.ac.kopo.midtermproject.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testListByCommunity(){
        List<Reply> replyList = replyRepository.getRepliesByCommunityOrderByRno(Community.builder().bno(99L).build());
        replyList.forEach(reply -> {
            System.out.println(reply);
        });
    }

    @Test
    public void insertReply(){
        IntStream.rangeClosed(1, 50).forEach(i -> {
            long bno = (long) (Math.random() * 50 + 1); // 1~100 임의의 long 형의 정수 값

            Community community = Community.builder()
                    .bno(bno)
                    .build();

            Reply reply = Reply.builder()
                    .text("Reply" + i)
                    .community(community)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);
        });
    }
}
