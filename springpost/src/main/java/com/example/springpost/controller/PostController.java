package com.example.springpost.controller;

import com.example.springpost.dto.*;
import com.example.springpost.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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
    public PostResponseDto savepost(@RequestBody PostRequestDto requestDto, HttpServletRequest request){

        return postService.savePost(requestDto, request);
    }
    @GetMapping("/api/read/posts") // 게시글 불러오기
    public PostListResponseDto getPosts(){
        return postService.getPosts();
    }

    @GetMapping("/api/view/post") // 게시글 조회
    public PostResponseDto getPost(@RequestParam Long postId){
        return postService.getPostSelect(postId);
    }

    @PutMapping("/api/update/post/{postId}") // 게시글 업데이트
    public MsgResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto , HttpServletRequest request){
        return postService.updatePost(postId,requestDto,request);
    }

    @DeleteMapping("/api/delete/post/{postId}") // 게시글 삭제
    public ResponseDto deleteCourse(@PathVariable Long postId, HttpServletRequest request){
        return postService.deletePost(postId,request);
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
