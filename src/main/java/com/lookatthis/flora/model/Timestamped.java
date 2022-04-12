package com.lookatthis.flora.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass // 상속 시 컬럼으로 인식
@Getter
public abstract class Timestamped {

    @CreatedDate // 생성일자 임을 나타냄
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate // 수정일자 임을 나타냄
    private LocalDateTime lastModifiedDate;
}

