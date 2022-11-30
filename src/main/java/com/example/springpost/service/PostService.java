package com.example.springpost.service;

import com.example.springpost.dto.PostListResponseDto;
import com.example.springpost.dto.PostRequestDto;
import com.example.springpost.dto.PostResponseDto;
import com.example.springpost.dto.ResponseDto;
import com.example.springpost.entity.Post;
import com.example.springpost.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;


    @Transactional
    public ResponseDto savePost(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        postRepository.save(post);
        return new ResponseDto("게시글 등록 성공", HttpStatus.OK.value());
    } // 게시글 등록
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
    public PostResponseDto getPostSelect(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new PostResponseDto(post);

    } //게시글조회

    @Transactional
    public ResponseDto updatePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        String password = post.getPassword();
        if(password.equals(requestDto.getPassword())){
            post.update(requestDto);
            return  new ResponseDto("게시물 수정 완료.",HttpStatus.OK.value());
        }else{
            return new ResponseDto("비밀번호가 일치하지 않습니다.",HttpStatus.UNAUTHORIZED.value());
        }
    } // 업데이트

    @Transactional
    public ResponseDto deletePost(Long id, String pw) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        String password = post.getPassword();
        if (password.equals(pw)) {
            postRepository.delete(post);
            return new ResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
        } else {
            return new ResponseDto("비밀번호가 일치하지 않습니다.",HttpStatus.UNAUTHORIZED.value());
        }
    } //삭제



    //    @Transactional // 쓰기
//    public Post createPost(PostRequestDto requestDto) {
//        Post post = new Post(requestDto);
//        postRepository.save(post);
//        return post;
//    } // 구버전

//    @Transactional(readOnly = true) // 읽기?
//    public List<Post> getPosts() {
//        return postRepository.findAllByOrderByModifiedAtDesc();
//    }// 구버전 게시판 목록 불러오기
//    @Transactional // 업데이트 transactional 없이?
//    public Long update(Long id, String pw,PostRequestDto requestDto) {
//        Post post = postRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//        );
//        String password = requestDto.getPassword();
//        if(password.equals(pw)){
//            post.update(requestDto);
//        }else{
//            System.out.println("비밀번호가 일치하지 않습니다.");
//        }
//
//        return post.getId();
//    }// 구 업데이트
//    @Transactional // 삭제
//    public Long deletePost(Long id, String pw, PostRequestDto requestDto) {
//        String password = requestDto.getPassword();
//        if(password.equals(pw)){
//            postRepository.deleteById(id);
//        }else{
//            System.out.println("비밀번호가 일치하지 않습니다.");
//        }
//        return id;
//    }//구 삭제
//    @Transactional
//    public String postView(Long id, PostRequestDto requestDto) {
//        Post post = postRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//        );
//        String content = requestDto.getContents();
//        String name = requestDto.getName();
//        String password = requestDto.getPassword();
//        String title = requestDto.getTitle();
//        String result = "title : " + title+", name : "+ name+ ", password : "+ password + ", content : " + content;
//
//        return result;
//    } // 구 게시글 조회

}
