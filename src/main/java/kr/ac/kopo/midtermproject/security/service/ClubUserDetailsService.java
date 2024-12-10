package kr.ac.kopo.midtermproject.security.service;

import kr.ac.kopo.midtermproject.entity.Member;
import kr.ac.kopo.midtermproject.repository.MemberRepository;
import kr.ac.kopo.midtermproject.security.dto.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("♣ ClubUserDetailsService: " + username);
        Optional<Member> result = memberRepository.findByEmail(username, false);
        if(result.isEmpty()){
            throw new UsernameNotFoundException("Check Email or social");
        }
        Member member = result.get();
        log.info(member);

        // 비밀번호 검증: 사용자가 입력한 비밀번호와 DB의 암호화된 비밀번호 비교
        String encodedPassword = member.getPassword();


        // 사용자 역할 설정
        AuthMemberDTO AuthMemberDTO = new AuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                member.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet()));

        AuthMemberDTO.setName(member.getName());

        return AuthMemberDTO;
    }


}
