package com.example.springpost.controller;

import com.example.springpost.dto.PostListResponseDto;
import com.example.springpost.dto.PostRequestDto;
import com.example.springpost.dto.PostResponseDto;
import com.example.springpost.dto.ResponseDto;
import com.example.springpost.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService; // 서비스 연결

    @GetMapping("/")  // 홈 인덱스 연결
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @GetMapping("/postwrite") // postwrite html연결
    public ModelAndView postwrite() {
        return new ModelAndView("postwrite");
    }

    @PostMapping("/api/postwrite") //게시글 등록
    public ResponseDto savepost(@RequestBody PostRequestDto requestDto){
        return postService.savePost(requestDto);
    }

    @GetMapping("/api/read/posts") // 게시글 불러오기
    public PostListResponseDto getPosts(){
        return postService.getPosts();
    }

    @GetMapping("/api/view/post") // 게시글 조회
    public PostResponseDto getPost(@RequestParam Long id){
        return postService.getPostSelect(id);
    }

    @PutMapping("/api/update/post/{id}") // 게시글 업데이트
    public ResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto){
        return postService.updatePost(id,requestDto);
    }

    @DeleteMapping("/api/delete/post/{id}/{pw}") // 게시글 삭제
    public ResponseDto deleteCourse(@PathVariable Long id, @PathVariable String pw){
        return postService.deletePost(id,pw);
    }


    //    @PostMapping("/api/postwrite") // 구버전 게시글 등록
//     public Post createPost(@RequestBody PostRequestDto requestDto) {
//        return postService.createPost(requestDto);
//    }
//    @GetMapping("/api/posts") // 구버전 게시글 불러오기
//    public List<Post> getPost(){
//        return postService.getPosts();
//    }
    //    @GetMapping("/api/postview/{id}") // 게시글조회 구버전
//    public String postView(@PathVariable Long id, @RequestBody PostRequestDto requestDto){
//        return postService.postView(id,requestDto);
//    }
//    @PutMapping("/api/posts/{id}/{pw}") // 업데이트
//    public Long updateMemo(@PathVariable Long id,@PathVariable String pw, @RequestBody PostRequestDto requestDto){
//        return postService.update(id,pw,requestDto);
//    }
//    @DeleteMapping("/api/posts/{id}/{pw}") // 삭제
//    public Long deletememo(@PathVariable Long id,@PathVariable String pw,@RequestBody PostRequestDto requestDto){
//
//        return  postService.deletePost(id,pw,requestDto);
//    }
}
