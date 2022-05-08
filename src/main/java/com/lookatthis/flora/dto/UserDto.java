package com.lookatthis.flora.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lookatthis.flora.model.Authority;
import com.lookatthis.flora.model.LoginType;
import com.lookatthis.flora.model.User;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String loginId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    private String phone;

    @NotNull
    private String email;

    @NotNull
    private LoginType loginType;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .loginId(loginId)
                .password(passwordEncoder.encode(password))
                .username(username)
                .phone(phone)
                .email(email)
                .loginType(LoginType.CUSTOMER)
                .authority(Authority.ROLE_USER)
                .build();
    }

}
