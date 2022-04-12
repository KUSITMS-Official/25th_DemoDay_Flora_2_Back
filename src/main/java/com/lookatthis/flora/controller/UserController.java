package com.lookatthis.flora.controller;

import com.lookatthis.flora.dto.UserDto;
import com.lookatthis.flora.model.User;
import com.lookatthis.flora.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@Api(tags = {"User API"})
public class UserController {

    private final UserService userService;

    // 회원가입 Controller
    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<User> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    // GET User 정보
    @GetMapping("/user")
    @ApiOperation(value = "사용자 정보 조회")
    @PreAuthorize("hasAnyRole('USER','ADMIN')") // USER & ADMIN 권한 가진 사람만 이 api 호출 가능
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{username}")
    @ApiIgnore
    @PreAuthorize("hasAnyRole('ADMIN')") // ADMIN 권한 있어야 호출 가능
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }
}
