package com.example.springpost.entity;

import com.example.springpost.dto.CommentDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.~ 종류가 있다
    private Long commentId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id")
    private Post post;


    @Column(nullable = false) // @column은 왜 썼을까
    private String name;
    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    private String comment;

    public void update(CommentDto commentDto) {
        this.comment =commentDto.getComment();
    }
}