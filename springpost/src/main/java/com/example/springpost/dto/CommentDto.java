package com.example.springpost.dto;

import com.example.springpost.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long commentId;
    private String comment;
    private String name;

    private LocalDateTime modifiedAt;

    private LocalDateTime createdAt;

    public CommentDto(Comment  comment) {
        this.commentId = comment.getCommentId();
        this.comment = comment.getComment();
        this.name = comment.getName();
        this.modifiedAt = comment.getModifiedAt();
        this.createdAt = comment.getCreatedAt();
    }
}
