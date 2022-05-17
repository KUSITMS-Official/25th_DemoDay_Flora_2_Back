package com.lookatthis.flora.service;

import com.lookatthis.flora.dto.LoginDto;
import com.lookatthis.flora.dto.TokenDto;
import com.lookatthis.flora.dto.TokenRequestDto;
import com.lookatthis.flora.dto.UserDto;
import com.lookatthis.flora.jwt.TokenProvider;
import com.lookatthis.flora.model.RefreshToken;
import com.lookatthis.flora.model.User;
import com.lookatthis.flora.repository.RefreshTokenRepository;
import com.lookatthis.flora.repository.UserRepository;
import com.lookatthis.flora.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
//import org.locationtech.jts.geom.Point;
//import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenDto login(LoginDto loginDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }

    @Transactional
    public User signup(UserDto userDto) throws ParseException {
        if (userRepository.existsByLoginId(userDto.getLoginId())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        User user = userDto.toUser(passwordEncoder);
        return userRepository.save(user);
    }

    // 유저 로그인 아이디 중복 체크
    @Transactional(readOnly = true)
    public boolean checkIdDuplication(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    // 현재 SecurityContext 에 있는 유저 정보 조회
    @Transactional(readOnly = true)
    public User getMyInfo() {
        return userRepository.findByLoginId(SecurityUtil.getCurrentUsername())
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    // 회원 정보 수정
//    @Transactional
//    public Object update(UserDto userDto) {
//    }

    // 로그인 아이디로 유저 정보 조회
    @Transactional(readOnly = true)
    public User getUserInfo(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    @Transactional
    public void updateAddress(User user, String userAddress, Point userPoint) {

        user.setUserAddress(userAddress);
        user.setUserPoint(userPoint);
    }

}