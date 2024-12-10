package kr.ac.kopo.midtermproject.controller;

import kr.ac.kopo.midtermproject.DTO.PageRequestDTO;
import kr.ac.kopo.midtermproject.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/homepage/")
@RequiredArgsConstructor
public class pageController {

    // 메인 페이지
    @GetMapping("/main")
    public String mainPage(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // 로그인된 사용자의 이름을 Model에 추가
            model.addAttribute("username", authentication.getName());
        }
        return "homepage/main"; // main.html 반환
    }

    @GetMapping("/best")
    public String bestPage() {
        // best.html을 반환
        return "homepage/best";
    }

    @GetMapping("/login")
    public String loginPage() {
        // login.html을 반환
        return "homepage/login";
    }

    @GetMapping("/join")
    public String joinPage() {
        // join.html을 반환
        return "homepage/join";
    }

    @GetMapping("/detailedpage")
    public String detailedPage() {
        // detailedpage.html을 반환
        return "homepage/detailedpage";
    }






}
