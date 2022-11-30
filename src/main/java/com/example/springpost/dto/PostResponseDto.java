package com.example.springpost.dto;

import com.example.springpost.entity.Post;
import com.example.springpost.entity.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto extends Post {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String name;
    private String contents;
    private String password ;
    private String title;



    public PostResponseDto(Post post) {
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.id = post.getId();
        this.name = post.getName();
        this.contents = post.getContents();
        this.password = "****";
        this.title = post.getTitle();

    }
}
