package com.example.springpost.entity;

import com.example.springpost.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.~ 종류가 있다
    private Long postId;

    @Column(nullable = false) // @column은 왜 썼을까
    private String name;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "post",fetch =FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Comment> comments;



    public Post(String username, String password, PostRequestDto requestDto) {
        this.name = username;
        this.contents = requestDto.getContents();
        this.password = password;
        this.title = requestDto.getTitle();
    }

//    public void savepost(String username, String password, PostRequestDto requestDto, Long id) {
//        this.name = username;
//        this.password = password;
//        this.contents = requestDto.getContents();
//        this.title = requestDto.getTitle();
//        this.userId = id;
//
//    }

    public void update(Post post, PostRequestDto requestDto) {
        this.name =post.getName();
        this.contents = requestDto.getContents();
        this.password = post.getPassword();
        this.title = requestDto.getTitle();
//        this.postId = postId;
    }


}