package com.nazarov.javadeveloper.chapter22;

import com.nazarov.javadeveloper.chapter22.entity.Post;
import com.nazarov.javadeveloper.chapter22.repository.impl.PostRepositoryImpl;

import java.util.Date;

public class Application {
    public static void main(String[] args) {
        PostRepositoryImpl postRepository = new PostRepositoryImpl();
        Post post = new Post(null, 2L, "New content", new Date(), new Date());

        Post post1 = postRepository.get(25L);
        post1.setContent("New content");
        postRepository.update(post1);
        System.out.println(postRepository.get(25L));
        Post iff = postRepository.get(25L);
        System.out.println();

//        Post persist = postRepository.save(post);
//        System.out.println(persist);
//        System.out.println(postRepository.get(6L));
    }
}