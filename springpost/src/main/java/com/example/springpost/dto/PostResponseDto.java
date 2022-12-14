package com.example.springpost.dto;

import com.example.springpost.entity.Comment;
import com.example.springpost.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto{

    private Long postId;
    private String title;
    private String name;
    private String contents;
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private List<Comment> list = new ArrayList<>();

    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.name = post.getName();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.list = post.getComments();
    }
}
