package hello.hello_spring.controller;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import java.util.List;

@Controller
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
//    @ResponseStatus(HttpStatus.FOUND)
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

//        System.out.println("member="+member.getName());

        memberService.join(member);

        return "redirect:/";
//        return "home";
    }
    
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMember();
        model.addAttribute("members", members);
        return "members/memberList";
    }
//    @Autowired private MemberService memberService;
//필드 주입

//
//    private final MemberService memberService;
//
//
//    @Autowired
//    public MemberController(MemberService memberService) {
//        this.memberService = memberService;
//    }

}