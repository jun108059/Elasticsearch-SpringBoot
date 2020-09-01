package com.searchengine.yjpark.controller;

import com.searchengine.yjpark.domain.Member;
import com.searchengine.yjpark.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    // private final MemberService memberService = new MemberService();
    // 이렇게 new로 만들게 되면 여러 다른 컨트롤러에서 사용할 때도 계속 새로 생성해야 됨
    // 비효율적이니까 한번만 생성하고 계속 불러올 수 있음!

    private final MemberService memberService;

    // Auto Wired 하면 자동으로 스프링 컨테이너에서 관리하는 멤버 서비스를 연결해줌
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/search/new")
    public String createForm() {
        return "search/createServiceForm";
    }

    @PostMapping("/search/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }


}