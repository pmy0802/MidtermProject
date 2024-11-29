package kr.ac.kopo.midtermproject.controller;


import kr.ac.kopo.midtermproject.DTO.CommunityDTO;
import kr.ac.kopo.midtermproject.DTO.PageRequestDTO;
import kr.ac.kopo.midtermproject.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/community/")
@RequiredArgsConstructor
public class communityController {
    private final CommunityService communityService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", communityService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String registerPost(CommunityDTO dto, RedirectAttributes redirectAttributes){
        Long bno = communityService.register(dto);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/community/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long bno, Model model){
        CommunityDTO communityDTO = communityService.get(bno);

        model.addAttribute("dto", communityDTO);
    }

    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes redirectAttributes){
        communityService.removeWithReplies(bno);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/community/list";
    }

    @PostMapping("/modify")
    public String modify(CommunityDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        communityService.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());

        redirectAttributes.addAttribute("bno", dto.getBno());

        return "redirect:/community/read";
    }

}
