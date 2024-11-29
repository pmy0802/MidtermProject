package kr.ac.kopo.midtermproject.service;

import kr.ac.kopo.midtermproject.DTO.CommunityDTO;
import kr.ac.kopo.midtermproject.DTO.PageRequestDTO;
import kr.ac.kopo.midtermproject.DTO.PageResultDTO;
import kr.ac.kopo.midtermproject.entity.Community;
import kr.ac.kopo.midtermproject.entity.Member;
import kr.ac.kopo.midtermproject.repository.CommunityRepository;
import kr.ac.kopo.midtermproject.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityRepository communityRepository;
    private final ReplyRepository replyRepository;
    @Override
    public Long register(CommunityDTO dto) {
        Community community = dtoToEntity(dto);
        communityRepository.save(community);

        return community.getBno();
    }

    @Override
    public PageResultDTO<CommunityDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        Function<Object[], CommunityDTO> fn = (en -> entityToDTO((Community) en[0], (Member) en[1],(Long) en[2]));
//        Page<Object[]> result = repository.getCommunityWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));
        Page<Object[]> result = communityRepository.searchPage(pageRequestDTO.getType(), pageRequestDTO.getKeyword(), pageRequestDTO.getPageable(Sort.by("bno").descending()));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public CommunityDTO get(Long bno) {
        Object result = communityRepository.getCommunityByBno(bno);

        Object[] arr = (Object[]) result;
        CommunityDTO communityDTO = entityToDTO((Community) arr[0], (Member) arr[1], (Long) arr[2]);

        return communityDTO;
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        // 댓글삭제
        replyRepository.deleteByBno(bno);
        // 원글삭제
        communityRepository.deleteById(bno);
    }

    @Transactional
    @Override
    public void modify(CommunityDTO communityDTO) {
        Community community = communityRepository.getReferenceById(communityDTO.getBno());
        community.changeTitle(communityDTO.getTitle());
        community.changeContent(communityDTO.getContent());

        communityRepository.save(community);
    }


}
