package sessionLogin.MemberLogin.Member;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
public class Member {

    /** 순번 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 회원가입 필수 항목
     */

    /** 이메일 */
    @Column(nullable = false, unique = true)
    private String email;

    /** 패스워드 */
    @Column(nullable = false)
    private String password;

    /** 닉네임 */
    @Column(nullable = false)
    private String nickname;

    /**
     * 회원가입 선택 항목
     */

    /** 유저 이름 */
    @Column
    private String name;

    /** 나이 */
    private int age;


    /**
     * 권한
     */

    @Enumerated(EnumType.STRING)
    private Role role;

    /** 회원 권한 설정 */
    public void authorizeMember() {
        this.role = Role.ROLE_MEMBER;
    }

    /** 회원 정보 수정 */
    public void updatePassword(PasswordEncoder passwordEncoder, String password) {
        this.password = passwordEncoder.encode(password);
    }

    public void updateNickName(String nickname){
        this.nickname = nickname;
    }

    public void updateAge(int age) {
        this.age = age;
    }

    public void updateName(String name) {
        this.name = name;
    }


    /**
     * 패스워드 일치하는지 확인
     * @param passwordEncoder
     * @param checkPassword
     * @return
     */
    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword){
        return passwordEncoder.matches(checkPassword, getPassword());
    }


    /** 패스워드 암호화 */
    public void passwordEncode(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }


}
