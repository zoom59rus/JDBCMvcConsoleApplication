package com.nazarov.javadeveloper.chapter22.repository;

import com.nazarov.javadeveloper.chapter22.entity.Post;

import java.util.List;

public interface PostRepository extends GenericRepository<Post, Long>{
    List<Post> getAllByWriterId(Long writerId);
    Post get(String content);

}
