package com.nazarov.javadeveloper.chapter22.service.impl;

import com.nazarov.javadeveloper.chapter22.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Spy
    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void get_by_id() {
        Post post = postService.get(2L);

        assertNotNull(post);
        assertEquals(2L, post.getId());
        Mockito.verify(postService, Mockito.times(1)).get(Mockito.anyLong());
    }

    @Test
    void get_by_content() {
        Post post = postService.get("Post #55");

        assertNotNull(post);
        assertEquals("Post #55", post.getContent());
        Mockito.verify(postService, Mockito.times(1)).get(Mockito.anyString());

        post = postService.get("fffff");

        assertNull(post);
        Mockito.verify(postService, Mockito.times(2)).get(Mockito.anyString());
    }

    @Test
    void get_all_posts_from_table_posts() {
        List<Post> posts = postService.getAll(2L);
        long count = posts.stream().filter(p -> p.getWritersId().equals(2L)).count();

        assertNotNull(posts);
        assertEquals(posts.size(), count);
        Mockito.verify(postService, Mockito.times(1)).getAll(Mockito.anyLong());
    }

    @Test
    void update_some_post() {
        Post updatablePost = postService.get("Post #55");
        String oldContent = updatablePost.getContent();
        String newContent = "Post #5";
        updatablePost.setContent(newContent);
        Post updatedPost = postService.update(updatablePost);

        assertNotNull(updatablePost);
        assertEquals(updatablePost.getId(), updatedPost.getId());
        assertEquals(updatablePost.getWritersId(), updatedPost.getWritersId());
        assertNotNull(updatedPost.getUpdated());
        assertEquals(newContent, updatedPost.getContent());
        Mockito.verify(postService, Mockito.times(1)).update(Mockito.any(Post.class));

        updatablePost.setContent(oldContent);
        postService.update(updatablePost);
        Mockito.verify(postService, Mockito.times(2)).update(Mockito.any(Post.class));
    }

    @Test
    void save_some_post() {
        Post savePost = new Post(null, 5L, "Post #77", new Date(), null);
        savePost = postService.save(savePost);

        assertNotNull(savePost.getId());
        assertEquals(5L, savePost.getWritersId());
        assertEquals("Post #77", savePost.getContent());
        assertNull(savePost.getUpdated());
        assertNotNull(savePost.getCreated());
        Mockito.verify(postService, Mockito.times(1)).save(Mockito.any(Post.class));
    }

    @Test
    void remove_some_post() {
        Post savePost = new Post(18L, 5L, "Post #77", new Date(), null);
        postService.remove(savePost);
        Post findPost = postService.get(18L);

        assertNull(findPost);
        Mockito.verify(postService, Mockito.times(1)).remove(Mockito.any(Post.class));
    }
}