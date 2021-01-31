package com.nazarov.javadeveloper.chapter22.controllers;

import com.nazarov.javadeveloper.chapter22.entity.Post;
import com.nazarov.javadeveloper.chapter22.service.PostService;
import com.nazarov.javadeveloper.chapter22.service.impl.PostServiceImpl;

import java.util.List;

public class PostController {
    private final PostService postService;

    public PostController() {
        this.postService = new PostServiceImpl();
    }

    public Post save(Post post){
        return postService.save(post);
    }

    public Post update(Post post){
        return postService.update(post);
    }

    public Post get(Post post){
        return postService.get(post.getId());
    }

    public Post get(String content){
        return postService.get(content);
    }

    public List<Post> getAll(Long writerId){
        return postService.getAll(writerId);
    }

    public void remove(Post post){
        postService.remove(post);
    }
}
