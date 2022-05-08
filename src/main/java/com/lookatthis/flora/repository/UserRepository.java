package com.lookatthis.flora.repository;

import com.lookatthis.flora.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 로그인 아이디로 유저 정보 조회
    Optional<User> findByLoginId(String loginId);

    //유저 로그인 아이디 중복 체크
    boolean existsByLoginId(String loginId);

}
