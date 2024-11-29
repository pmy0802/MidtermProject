package kr.ac.kopo.midtermproject.service;


import kr.ac.kopo.midtermproject.DTO.CommunityDTO;
import kr.ac.kopo.midtermproject.DTO.PageRequestDTO;
import kr.ac.kopo.midtermproject.DTO.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommunityServiceTests {

    @Autowired
    private CommunityService communityService;

    @Test
    public void testRegister(){
        CommunityDTO dto = CommunityDTO.builder()
                .title("Board Test...")
                .content("Board Test Board Test Board Test")
                .writerEmail("user7@kopo.ac.kr")
                .build();
        Long bno = communityService.register(dto);
        System.out.println("정상적으로 글이 저장되었습니다. : "+bno);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<CommunityDTO, Object[]> result = communityService.getList(pageRequestDTO);

        for (CommunityDTO communityDTO : result.getDtoList()){
            System.out.println(communityDTO);
        }
    }

    @Test
    public void testGet(){
        Long bno = 3L;
        CommunityDTO communityDTO = communityService.get(bno);
        System.out.println(communityDTO);
    }

    @Test
    public void testRemove(){
        Long bno = 3L;
        communityService.removeWithReplies(bno);
    }

    @Test
    public void testModify(){
        CommunityDTO communityDTO = CommunityDTO.builder()
                .bno(5L)
                .title("수정한 제목 연습")
                .content("수정된 내용 연습")
                .build();

        communityService.modify(communityDTO);
    }


}
