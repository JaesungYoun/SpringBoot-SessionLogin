package sessionLogin.MemberLogin.Member.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sessionLogin.MemberLogin.Member.Member;
import sessionLogin.MemberLogin.Member.api.request.*;
import sessionLogin.MemberLogin.Member.api.response.MemberLoginResponse;
import sessionLogin.MemberLogin.Member.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입/로그인 페이지
     */
    @GetMapping("/loginPage")
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("member/login.html");
        return modelAndView;
    }

    /**
     * 회원가입
     */
    @PostMapping("/signUp")
    public Long signUp(@RequestBody @Valid MemberSignUpRequest memberSignUpRequest) throws Exception {
        return memberService.signUp(memberSignUpRequest);
    }


    /**
     * 회원 로그인
     */

    @PostMapping("/login")
    public String login(@RequestBody @Valid MemberLoginRequest memberLoginRequest, HttpServletRequest httpServletRequest) throws Exception {

        // 1. 회원 정보 조회 
        Member loginMember = memberService.login(memberLoginRequest);

        // 2. 세션에 회원 정보 저장 & 세션 유지 시간 설정
        if (loginMember != null) {
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("loginMember",loginMember);
            session.setMaxInactiveInterval(60 * 30); // 기본 30분 설정
        }

        return "로그인성공";
    }

    /**
     * 회원 로그아웃
     */
    @PostMapping("/members/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }


    /**
     * 회원정보 수정
     */
    @PutMapping("members/update")
    public void updateMemberInfo(@RequestBody @Valid UpdateInfoRequest updateInfoRequest, HttpServletRequest httpServletRequest) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        Member member = (Member) session.getAttribute("loginMember");

        memberService.updateMemberInfo(updateInfoRequest,member.getEmail());
    }

    /**
     * 패스워드 변경
     */

    @PutMapping("members/password")
    public void updatePassword(@RequestBody @Valid UpdatePasswordRequest updatePasswordRequest,HttpServletRequest httpServletRequest) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        Member member = (Member) session.getAttribute("loginMember");
        memberService.updatePassword(member.getEmail(), updatePasswordRequest.getAsIspassword(), updatePasswordRequest.getToBePassword());

    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("members/delete")
    public void delete(@RequestBody @Valid MemberDeleteRequest memberDeleteRequest,HttpServletRequest httpServletRequest)throws Exception {
        HttpSession session = httpServletRequest.getSession();
        Member member = (Member) session.getAttribute("loginMember");
        memberService.deleteMember(memberDeleteRequest.getPassword(),member.getEmail());
    }

    /**
     * 유저가 자신의 정보 조회
     */

    @GetMapping("/members")
    public ResponseEntity<MemberInfo> getMyInfo(HttpServletRequest httpServletRequest) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        Member member = (Member) session.getAttribute("loginMember");
        MemberInfo memberInfo = memberService.getMyinfo(member.getEmail());
        return ResponseEntity.ok(memberInfo);
    }


}
