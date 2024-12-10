package kr.ac.kopo.midtermproject.security;

import kr.ac.kopo.midtermproject.entity.Member;
import kr.ac.kopo.midtermproject.entity.MemberRole;
import kr.ac.kopo.midtermproject.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberTests {
    @Autowired
    private MemberRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void insertDummies(){
//        user1-user80: USER
//        user81-user90: USER, MANAGER
//        user91-user100: USER, MANAGER, ADMIN
        IntStream.rangeClosed(1, 100).forEach(i ->{
            Member member = Member.builder()
                    .email("user"+i+"@kopo.ac.kr")
                    .name("사용자"+i)
                    .password(passwordEncoder.encode("1234"))
                    .fromSocial(false)
                    .build();
            member.addMemberRole(MemberRole.USER);
            if(i > 80){
                member.addMemberRole(MemberRole.MANAGER);
            }
            if(i > 90){
                member.addMemberRole(MemberRole.ADMIN);
            }
            repository.save(member);
        });
    }
    @Test
    public void testRead(){
        Optional<Member> result = repository.findByEmail("user99@kopo.ac.kr", false);
        Member member = result.get();
        System.out.println("★★★" + member);
    }
}
