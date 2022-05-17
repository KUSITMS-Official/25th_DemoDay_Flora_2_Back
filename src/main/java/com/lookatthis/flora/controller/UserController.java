package com.lookatthis.flora.controller;

import com.lookatthis.flora.dto.*;
import com.lookatthis.flora.model.User;
import com.lookatthis.flora.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@Api(tags = {"User API"})
public class UserController {

    private final UserService userService;

    // 로그인 Controller
    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(userService.login(loginDto));
    }

    @ApiOperation(value = "토큰 재발행")
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(userService.reissue(tokenRequestDto));
    }

    // 회원가입 Controller
    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody UserDto userDto) throws ParseException {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    // 아이디 중복 확인 controller (존재하면 true)
    @ApiOperation(value = "아이디 중복 확인")
    @GetMapping("/check/{loginId}")
    public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable String loginId) {
        return ResponseEntity.ok(userService.checkIdDuplication(loginId));
    }

    // SpringContext 에서 유저 정보 조회
    @ApiOperation(value = "유저 정보 조회 - SpringContext 내")
    @GetMapping("/myInfo")
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyInfo());
    }

    // 로그인 아이디로 유저 정보 조회
    @ApiOperation(value = "유저 정보 조회 - 로그인 아이디")
    @GetMapping("/userInfo/{loginId}")
    public ResponseEntity<User> getUserInfo(@PathVariable String loginId) {
        return ResponseEntity.ok(userService.getUserInfo(loginId));
    }

    // 회원 정보 수정
//    @ApiOperation(value = "회원 정보 수정")
//    @PostMapping("/update")
//    public ResponseEntity<Object> signup(@Valid @RequestBody UserDto userDto) throws ParseException {
//        return ResponseEntity.ok(userService.update(userDto));
//    }

    // 회원 주소 저장
    @ApiOperation(value = "회원 주소 변경")
    @GetMapping("/address/{userAddress}/{latitude}/{longitude}")
    public ResponseEntity updateAddress(@PathVariable String userAddress, @PathVariable Double latitude, @PathVariable Double longitude) throws ParseException {
        User user = userService.getMyInfo();
        String pointWKT = String.format("POINT(%s %s)", longitude, latitude);
        Point userPoint = (Point) new WKTReader().read(pointWKT);
        userService.updateAddress(user, userAddress, userPoint);
        return new ResponseEntity(HttpStatus.OK);
    }

}

