package com.nazarov.javadeveloper.chapter22.service.impl;

import com.nazarov.javadeveloper.chapter22.entity.Post;
import com.nazarov.javadeveloper.chapter22.repository.PostRepository;
import com.nazarov.javadeveloper.chapter22.repository.impl.PostRepositoryImpl;
import com.nazarov.javadeveloper.chapter22.service.PostService;

import java.util.List;

public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl() {
        this.postRepository = new PostRepositoryImpl();
    }

    @Override
    public Post get(Long id) {
        return postRepository.get(id);
    }

    @Override
    public Post get(String content) {
        return postRepository.get(content);
    }

    @Override
    public List<Post> getAll(Long writerId) {
        return postRepository.getAllByWriterId(writerId);
    }

    @Override
    public Post update(Post post) {
        return postRepository.update(post);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void remove(Post post) {
        postRepository.remove(post.getId());
    }
}
