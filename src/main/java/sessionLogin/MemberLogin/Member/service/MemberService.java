package sessionLogin.MemberLogin.Member.service;


import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sessionLogin.MemberLogin.Member.Member;
import sessionLogin.MemberLogin.Member.Role;
import sessionLogin.MemberLogin.Member.api.request.MemberInfo;
import sessionLogin.MemberLogin.Member.api.request.MemberLoginRequest;
import sessionLogin.MemberLogin.Member.api.request.MemberSignUpRequest;
import sessionLogin.MemberLogin.Member.api.request.UpdateInfoRequest;
import sessionLogin.MemberLogin.Member.api.response.MemberLoginResponse;
import sessionLogin.MemberLogin.Member.repository.MemberRepository;


@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    public Long signUp(MemberSignUpRequest memberSignUpRequest) throws Exception {

        if (memberRepository.findByEmail(memberSignUpRequest.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (memberRepository.findByNickname(memberSignUpRequest.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        if (!memberSignUpRequest.getPassword().equals(memberSignUpRequest.getCheckPassword())) {
            throw new Exception("비밀번호와 확인용 비밀번호가 일치하지 않습니다.");
        }

        // 요청 정보로 멤버 객체 만들기
        Member member = Member.builder()
                .email(memberSignUpRequest.getEmail())
                .password(memberSignUpRequest.getPassword())
                .nickname(memberSignUpRequest.getNickname())
                .name(memberSignUpRequest.getName())
                .age(memberSignUpRequest.getAge())
                .role(Role.ROLE_MEMBER)
                .build();

        System.out.println(Role.ROLE_MEMBER);

        member.passwordEncode(passwordEncoder);

        // 회원정보 저장(회원가입)
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 회원 로그인
     */
    public Member login(MemberLoginRequest memberLoginRequest) throws Exception {
         Member member = memberRepository.findByEmail(memberLoginRequest.getEmail())
                .orElseThrow(() -> new Exception("존재하지 않는 이메일입니다."));


        // 비밀번호 일치 여부 확인
        if(!member.matchPassword(passwordEncoder, memberLoginRequest.getPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다. 다시 입력해주십시오");
        }

        // 로그인 정보 리턴
        return member;

    }


    /**
     * 비밀번호 변경
     */
    
    public void updatePassword(String email, String asIsPassword, String toBePassword) throws Exception {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("존재하지 않는 이메일입니다."));



        // 비밀번호 일치 여부 확인
        if(!member.matchPassword(passwordEncoder, asIsPassword) ) {
            throw new Exception("비밀번호가 일치하지 않습니다. 다시 입력해주십시오");
        }

        // 비밀번호 수정
        member.updatePassword(passwordEncoder,toBePassword);

    }

    /**
     * 회원정보 수정
     */

    public void updateMemberInfo(UpdateInfoRequest updateInfoRequest, String email) throws Exception {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("존재하지 않는 이메일입니다."));

        if (updateInfoRequest.getNickname() != null) {
            member.updateNickName(updateInfoRequest.getNickname());
        }
        if (updateInfoRequest.getName() != null) {
            member.updateName(updateInfoRequest.getName());
        }
        if (updateInfoRequest.getAge() != 0) {
            member.updateAge(updateInfoRequest.getAge());
        }

    }


    /**
     * 회원 탈퇴
     */

    public void deleteMember(String checkPassword, String email) throws Exception {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new Exception("존재하지 않는 이메일입니다."));


        // 비밀번호 일치 여부 확인
        if(!member.matchPassword(passwordEncoder, checkPassword) ) {
            throw new Exception("비밀번호가 일치하지 않습니다. 다시 입력해주십시오");
        }

        // 비밀번호 수정
        memberRepository.delete(member);


    }

    /**
     * 유저가 자신의 정보 조회
     */

    public MemberInfo getMyinfo(String email) throws Exception {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new Exception("존재허지 않는 이메일입니다."));
        return new MemberInfo(member);
    }

}

