package com.example.springpost.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 공통
    COMMON_BAD_REQUEST_400(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    BAD_TOKKEN_400(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),

    BAD_PW_400(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    BAD_ID_400(HttpStatus.BAD_REQUEST, "아이디가 일치하지 않습니다.");
    // 메모 오류

    // 유저 관련

    // 코멘트 관련


    private final HttpStatus httpStatus;
    private final String message;

}