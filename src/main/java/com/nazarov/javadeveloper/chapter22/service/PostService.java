package com.nazarov.javadeveloper.chapter22.service;

import com.nazarov.javadeveloper.chapter22.entity.Post;

import java.util.List;

public interface PostService {

    Post get(Long id);
    Post get(String content);
    List<Post> getAll(Long writerId);
    Post update(Post post);
    Post save(Post post);
    void remove(Post post);
}
