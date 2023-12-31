package org.zerock.j2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.j2.dto.MemberDTO;
import org.zerock.j2.service.MemberService;
import org.zerock.j2.service.SocialService;
import org.zerock.j2.util.JWTUtil;

import java.util.Map;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/member/")
@Log4j2
public class MemberController {

    private final MemberService memberService;

    private final SocialService socialService;

    private final JWTUtil jwtUtil;

    // 카카오 로그인 설정하기
    @GetMapping("kakao")
    public MemberDTO getAuthCode(String code) {

        log.info("--------------------------");
        log.info(code);

        String email = socialService.getKakaoEmail(code);

        MemberDTO memberDTO = memberService.getMemberWithEmail(email);

        return memberDTO;
    }


    // 일반 로그인 설정
    @PostMapping("login")
    public MemberDTO login(@RequestBody MemberDTO memberDTO){

        log.info("Parameter: " + memberDTO);

        // test용 코드
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        MemberDTO result = memberService.login(
                memberDTO.getEmail(),
                memberDTO.getPw()
        );

        // 토큰 생성 (Access, Refresh)
        result.setAccessToken(jwtUtil.generate(Map.of("email",result.getEmail()),10));

        result.setRefreshToken(jwtUtil.generate(Map.of("email",result.getEmail()),60*24));

        log.info("Return: " + result);

        return result;
    }
}
