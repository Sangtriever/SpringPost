package com.example.springpost.repository;


import com.example.springpost.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {// <연결할 클래스 이름, long-> id값>

    List<Post> findAllByOrderByModifiedAtDesc();


}