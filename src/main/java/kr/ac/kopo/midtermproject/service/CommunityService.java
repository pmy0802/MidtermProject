package kr.ac.kopo.midtermproject.service;


import kr.ac.kopo.midtermproject.DTO.CommunityDTO;
import kr.ac.kopo.midtermproject.DTO.PageRequestDTO;
import kr.ac.kopo.midtermproject.DTO.PageResultDTO;
import kr.ac.kopo.midtermproject.entity.Community;
import kr.ac.kopo.midtermproject.entity.Member;

public interface CommunityService {
    // 새글을 등록하는 기능
    Long register(CommunityDTO dto);

    // 게시목록 처리 기능
    PageResultDTO<CommunityDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    // 특정 게시글 하나를 조회하는 기능
    CommunityDTO get(Long bno);
    // 삭제 기능
    void removeWithReplies(Long bno);
    // 수정 기능
    void modify(CommunityDTO communityDTO);

    // Entity를 DTO로 변환하는 메소드
    default CommunityDTO entityToDTO(Community community, Member member, Long replyCount){
        CommunityDTO communityDTO = CommunityDTO.builder()
                .bno(community.getBno())
                .title(community.getTitle())
                .content(community.getContent())
                .regDate(community.getRegDate())
                .modDate(community.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .build();

        return communityDTO;
    }

    // DTO를 Entity로 변환하는 메소드



    default Community dtoToEntity(CommunityDTO dto){
        Member member = Member.builder()
                .email(dto.getWriterEmail())
                .build();

        Community community = Community.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return community;
    }
}
