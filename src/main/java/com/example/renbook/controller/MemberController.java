package com.example.renbook.controller;

import com.example.renbook.domain.LoginDto;
import com.example.renbook.domain.Member;
import com.example.renbook.domain.MemberDto;
import com.example.renbook.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/join")
    String showJoinPage(Model model) {
        model.addAttribute("memberDto", new MemberDto());

        return "join";
    }

    @PostMapping("/join")
    String join(@Validated @ModelAttribute MemberDto memberDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("memberDto", memberDto);
            return "join";
        }

        memberService.join(memberDto);

        return "redirect:/";
    }

    @GetMapping("/login")
    String showLoginPage(HttpServletRequest request, Model model) {
        String preUrl = request.getHeader("Referer");
        HttpSession session = request.getSession();

        if (preUrl == null || preUrl.contains("/login") == false) {
            session.setAttribute("preUrl", preUrl);
        }

        if (model.containsAttribute("loginDto")) {
            LoginDto preLoginDto = (LoginDto) model.getAttribute("loginDto");
//            LoginDto preLoginDto = (LoginDto) model.asMap().get("loginDto");
            model.addAttribute("loginDto", preLoginDto);
        } else {
            model.addAttribute("loginDto", new LoginDto());
        }

        return "login";
    }

    @PostMapping("/login")
    String login(@Validated @ModelAttribute LoginDto loginDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        String preUrl = (String) session.getAttribute("preUrl");


        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            log.info("loginDto={} , {}", loginDto.getMemberId(), loginDto.getPassword());
            loginDto.setPassword(null);
            model.addAttribute("loginDto", loginDto);
            return "login";
        }

        Member loginedMember = memberService.login(loginDto);

        //로그인 실패시
        if (loginedMember == null) {
            log.info("loginfail");
            log.info("loginDto={} , {}", loginDto.getMemberId(), loginDto.getPassword());
            loginDto.setPassword(null);
            redirectAttributes.addFlashAttribute("loginDto", loginDto);
            return "redirect:/login";
        }

        //main 페이지에서 로그인 시도한 경우
        if (preUrl == null) {
            return "main";
        }

        log.info("login susses");

        return "redirect:" + preUrl;
    }

}
