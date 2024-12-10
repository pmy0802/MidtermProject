package kr.ac.kopo.midtermproject.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Log4j2
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // 패스워드 암호화
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((auth) -> {
            // 정적 리소스 경로 허용 설정
            auth.requestMatchers("/css/**", "/js/**", "/images/**", "/assets/**").permitAll();

            // /homepage/main은 누구나 접근 가능
            auth.requestMatchers("/homepage/main").permitAll();
            auth.requestMatchers("/homepage/login").permitAll();
            auth.requestMatchers("/homepage/join").permitAll();
            auth.requestMatchers("/homepage/detailedpage").permitAll();
            auth.requestMatchers("/homepage/best").permitAll();
            // /community/list, /board/list는 USER 권한 필요
            auth.requestMatchers("/community/list").hasRole("USER");
            auth.requestMatchers("/board/list").hasRole("USER");
            auth.requestMatchers("/board/modify").hasRole("ADMIN");
            auth.requestMatchers("/board/read").hasRole("USER");
            auth.requestMatchers("/board/register").hasRole("ADMIN");
            auth.requestMatchers("/board/modify").hasRole("ADMIN");
            auth.requestMatchers("/community/read").hasRole("USER");
            auth.requestMatchers("/community/modify").hasRole("USER");
            auth.requestMatchers("/community/register").hasRole("USER");
            auth.requestMatchers("/replies/**").hasRole("USER");
        });

        // 로그인 폼 설정
        httpSecurity.formLogin(form -> form
                .loginPage("/homepage/login") // 사용자 정의 로그인 페이지
                .loginProcessingUrl("/login") // 로그인 처리 URL
                .defaultSuccessUrl("/homepage/main", true) // 로그인 성공 후 이동 페이지
                .failureUrl("/login?error=true") // 로그인 실패 시 이동 페이지
                .permitAll() // 로그인 페이지는 모든 사용자가 접근 가능
                .successHandler((request, response, authentication) -> {
                    // 추가 성공 핸들러
                    response.sendRedirect("/homepage/main");
                })
        );


        // CSRF 비활성화 (테스트 환경에서 필요할 때만 사용)
        httpSecurity.csrf(csrf -> csrf.disable());
        // 로그아웃 설정
        httpSecurity.logout(logout -> logout
                .logoutUrl("/logout") // 로그아웃 요청 URL
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID") // 쿠키 삭제
                .logoutSuccessUrl("/homepage/main") // 로그아웃 성공 후 이동 페이지
                .permitAll()
        );

        return httpSecurity.build();
    }


}
