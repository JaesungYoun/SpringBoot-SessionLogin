package sessionLogin.MemberLogin.Member.api.request;


import lombok.Builder;
import lombok.Getter;
import sessionLogin.MemberLogin.Member.Member;

@Getter
public class MemberInfo {

    private String email;
    private String nickName;
    private String name;
    private int age;


    @Builder
    public MemberInfo(Member member) {
        this.email = member.getEmail();
        this.nickName = member.getNickname();
        this.name = member.getName();
        this.age = member.getAge();
    }

}
