package com.example.springpost.service;

import com.example.springpost.dto.*;
import com.example.springpost.entity.Post;
import com.example.springpost.entity.User;
import com.example.springpost.jwt.JwtUtil;
import com.example.springpost.repository.PostRepository;
import com.example.springpost.repository.UserRepository;
import com.example.springpost.util.exception.ErrorCode;
import com.example.springpost.util.exception.RequestException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Transactional
    public PostResponseDto savePost(PostRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;
        System.out.println("token = " + token);

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new RequestException(ErrorCode.BAD_TOKKEN_400);
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Post post = new Post(user.getUsername(), user.getPassword(),requestDto);
//            post.savepost(user.getUsername(), user.getPassword(), requestDto, user.getId());
//            System.out.println(post);
            postRepository.save(post);
            return new PostResponseDto(post);
            // 게시글 등록
        } else {
            throw new RequestException(ErrorCode.BAD_TOKKEN_400);
        }
    }

    @Transactional
    public PostListResponseDto getPosts() {
        PostListResponseDto postListResponseDto = new PostListResponseDto();
        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            postListResponseDto.addPost(new PostResponseDto(post));
        }
        return postListResponseDto;
    } //게시판 목록 불러오기


    @Transactional
    public PostResponseDto getPostSelect(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new PostResponseDto(post);

    } //게시글조회

    @Transactional
    public MsgResponseDto updatePost(Long postId, PostRequestDto requestDto, HttpServletRequest request) {
        // 글의 name과 token값의 아이디가 일치하면?

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
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );

            if(user.getRole().toString().equals("ADMIN")){
                post.update(post, requestDto);
                return new MsgResponseDto(post);
            }

            String password = user.getPassword();
            if (post.getName().equals(user.getUsername())) {
                if(password.equals(post.getPassword())){
                    post.update(post, requestDto);
                    return new MsgResponseDto(post);
                }else{
                    throw new RequestException(ErrorCode.BAD_ID_400);
                }
            } else {
                throw new RequestException(ErrorCode.BAD_PW_400);
            }
        } else {
            throw new RequestException(ErrorCode.BAD_TOKKEN_400);
        }

    } // 업데이트

    @Transactional
    public ResponseDto deletePost(Long postId, HttpServletRequest request) {

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

            Post post = postRepository.findById(postId).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );

            if(user.getRole().toString().equals("ADMIN")){
                postRepository.delete(post);
                return new ResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
            }
            String password = user.getPassword();
            if (post.getName().equals(user.getUsername())) {
                if(password.equals(post.getPassword())){
                    postRepository.delete(post);
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
    //삭제
}
