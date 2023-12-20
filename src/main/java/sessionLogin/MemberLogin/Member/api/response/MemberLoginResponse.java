package sessionLogin.MemberLogin.Member.api.response;

import lombok.Getter;
import sessionLogin.MemberLogin.Member.Member;

@Getter
public class MemberLoginResponse {

    private String email;
    private String nickName;
    private String name;
    private int age;

    public MemberLoginResponse(Member member) {
        this.email = member.getEmail();
        this.nickName = member.getNickname();
        this.name = member.getName();
        this.age = member.getAge();
    }

}
