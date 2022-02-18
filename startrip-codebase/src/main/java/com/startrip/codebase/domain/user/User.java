package com.startrip.codebase.domain.user;

import com.startrip.codebase.constant.Role;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.*;

@Entity
@Table(name = "\"User\"")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column( nullable = false)
    private String name;

    @Column(unique = true) // 중복 이메일이 안되도록 설정
    private String email;

    private String password;

    private String nickname;

    private String picture_url;

    private Boolean receive_email;

    @Enumerated(EnumType.STRING)
    private Role authorities;

    @ResponseBody
    @GetMapping("/kakao")
    public void  kakaoCallback(@RequestParam String code)  {

        System.out.println(code);

    }
}
