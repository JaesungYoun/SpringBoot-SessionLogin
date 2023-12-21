# SpringBoot-SessionLogin
스프링부트 세션로그인 구현


# 세션: 서버에 사용자 인증 정보를 임시 저장하여 인증 상태를 유지하는 시간대 
일반적으로 세션은 컴퓨터 시스템의 관리자(OS 또는 서버)가 자신의 자산을 이용하는 것이 허락된 사용자를 식별할 일정한 기간을 가리키는 것이다.

위에서 설명한 쿠키를 이용해서 사용자의 아이디와 비밀번호를 쿠키에 저장한다고 가정해보자. 그러나 쿠키를 통해 아이디와 비밀번호를 주고 받으면 개인 컴퓨터가 아닌 경우 누구나 그 사용자의 개인 정보를 확인할 수 있고, 조작 될 수 있는 보안상 매우 큰 문제를 야기할 수 있다.

그래서 비밀번호 등의 민감한 인증 정보는 쿠키에 저장하지 않고 사용자의 식별자인 Session Id를 생성해 서버에 저장한다. 서버에는 인증 정보와 더불어 이 세션 ID에 해당하는 로그인 상태, 마지막 로그인 시간, 닉네임, 만료기한 등의 정보를 저장한다. 보안상 서버는 사용자의 개인 컴퓨터보다는 훨씬 안전하다.

  
![image](https://github.com/JaesungYoun/SpringBoot-SessionLogin/assets/73388615/41e0ae5a-890e-425e-9350-561644e3a5e7)

