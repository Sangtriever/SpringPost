package com.example.springpost.controller;

import com.example.springpost.dto.*;
import com.example.springpost.service.CommentService;
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
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}") //댓글
    public CommentDto savecomment(@RequestBody CommentDto commentDto, HttpServletRequest request,@PathVariable Long postId){
        return commentService.saveComment(commentDto, request,postId);
    }

    @PutMapping("/{commentId}")
    public CommentDto updatecomment(@RequestBody CommentDto commentDto, HttpServletRequest request,@PathVariable Long commentId){
        return commentService.updateComment(commentDto, request,commentId);
    }

    @DeleteMapping("/{commentId}")
    public ResponseDto deletecomment(HttpServletRequest request,@PathVariable Long commentId){
        return commentService.deleteComment(request,commentId);
    }


}
