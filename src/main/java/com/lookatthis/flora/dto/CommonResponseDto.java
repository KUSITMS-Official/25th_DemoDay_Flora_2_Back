package com.lookatthis.flora.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommonResponseDto<T> extends ResponseDto {
    private int count;
    private T data;

    public CommonResponseDto(T data) {
        this.data = data;
        if (data instanceof List) {
            this.count = ((List<?>) data).size();
        } else {
            this.count = 1;
        }
    }

}
