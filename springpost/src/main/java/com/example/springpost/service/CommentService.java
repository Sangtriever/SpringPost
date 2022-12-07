package com.example.springpost.service;

import com.example.springpost.dto.*;
import com.example.springpost.entity.Comment;
import com.example.springpost.entity.Post;
import com.example.springpost.entity.User;
import com.example.springpost.jwt.JwtUtil;
import com.example.springpost.repository.CommentRepository;
import com.example.springpost.repository.PostRepository;
import com.example.springpost.repository.UserRepository;
import com.example.springpost.util.exception.ErrorCode;
import com.example.springpost.util.exception.RequestException;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@Builder
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public CommentDto saveComment(CommentDto commentDto, HttpServletRequest request, Long postId) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );
            Post post = postRepository.findById(postId).orElseThrow(
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
            );
            Comment comment = Comment.builder()
                    .comment(commentDto.getComment())
                    .name(user.getUsername())
                    .password(user.getPassword())
                    .post(post)
                    .build();
            commentRepository.save(comment);
            return new CommentDto(comment);
        } else {
            throw new RequestException(ErrorCode.BAD_TOKKEN_400);
        }


    }

    @Transactional  // 데이터 베이스 정보가 바뀔때
    public CommentDto updateComment(CommentDto commentDto, HttpServletRequest request, Long commentId) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );
            Comment comment = commentRepository.findById(commentId).orElseThrow(
                    () -> new IllegalArgumentException("아이디를 찾을 수 없습니다.")
            );
            if(user.getRole().toString().equals("ADMIN")){
                comment.update(commentDto);
                return new CommentDto(comment);
            }
            String password = user.getPassword();
            if (comment.getName().equals(user.getUsername())) {
                if(password.equals(comment.getPassword())){
                    comment.update(commentDto);
                    return new CommentDto(comment);
                }else{
                    throw new RequestException(ErrorCode.BAD_ID_400);
                }
            } else {
                throw new RequestException(ErrorCode.BAD_PW_400);
            }
        } else {
            throw new RequestException(ErrorCode.BAD_TOKKEN_400);
        }
    }

    public ResponseDto deleteComment(HttpServletRequest request, Long commentId) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );

            Comment comment = commentRepository.findById(commentId).orElseThrow(
                    () -> new IllegalArgumentException("아이디를 찾을 수 없습니다.")
            );

            if(user.getRole().toString().equals("ADMIN")){
                commentRepository.delete(comment);
                return new ResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
            }
            String password = user.getPassword();
            if (comment.getName().equals(user.getUsername())) {
                if(password.equals(comment.getPassword())){
                    commentRepository.delete(comment);
                }else{
                    return new ResponseDto("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED.value());
                }
                return new ResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
            } else {
                return new ResponseDto("아이디가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED.value());
            }
        } else {
            throw new RequestException(ErrorCode.BAD_TOKKEN_400);
        }
    }
}

