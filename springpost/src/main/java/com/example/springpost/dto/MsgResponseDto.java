package com.example.springpost.dto;

import com.example.springpost.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class MsgResponseDto{

    private Long postId;
    private String title;
    private String name;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public MsgResponseDto(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.name = post.getName();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }



}
