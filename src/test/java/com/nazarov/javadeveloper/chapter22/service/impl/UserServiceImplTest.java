package com.nazarov.javadeveloper.chapter22.service.impl;

import com.nazarov.javadeveloper.chapter22.entity.Post;
import com.nazarov.javadeveloper.chapter22.entity.Region;
import com.nazarov.javadeveloper.chapter22.entity.dtos.WriterDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Spy
    @InjectMocks
    private UserServiceImpl userService;

    private WriterDto testWriterDto;
    private List<Post> posts;
    private Region region;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        posts = new ArrayList<>();
        posts.add(new Post(null, null, "Test content 1", new Date(), null));
        posts.add(new Post(null, null, "Test content 2", new Date(), null));
        posts.add(new Post(null, null, "Test content 3", new Date(), null));
        region = new Region(null, "Test region");
        testWriterDto = new WriterDto(null, "Test", "Dto", posts, region);
    }

    @Test
    void save_writer_as_writerDto() {
        WriterDto save = userService.save(testWriterDto);

        assertNotNull(save);
        assertEquals("Test region", save.getRegion().getName());
        assertEquals(posts.size(), save.getPosts().size());
        Mockito.verify(userService, Mockito.times(1)).save(Mockito.any(WriterDto.class));
    }

    @Test
    void update_some_writerDto() {
        WriterDto updatableWriterDto = userService.get(20L);
        String oldFirstName = updatableWriterDto.getFirstName();
        String newFirstName = "Goodtest";
        updatableWriterDto.setFirstName(newFirstName);
        updatableWriterDto = userService.update(updatableWriterDto);

        assertNotNull(updatableWriterDto);
        assertEquals(20L, updatableWriterDto.getId());
        assertEquals("Goodtest", updatableWriterDto.getFirstName());
        Mockito.verify(userService, Mockito.times(1)).update(Mockito.any(WriterDto.class));

        updatableWriterDto.setFirstName(oldFirstName);
        updatableWriterDto = userService.update(updatableWriterDto);

        assertEquals(20L, updatableWriterDto.getId());
        assertEquals(oldFirstName, updatableWriterDto.getFirstName());
        Mockito.verify(userService, Mockito.times(2)).update(Mockito.any(WriterDto.class));
    }

    @Test
    void get_some_writerDto_by_id() {
        WriterDto find = userService.get(2L);

        assertNotNull(find);
        assertEquals(2L, find.getId());
        Mockito.verify(userService, Mockito.times(1)).get(Mockito.anyLong());
    }

    @Test
    void get_by_first_name() {
        WriterDto find = userService.getByFirstName("Test");

        assertNotNull(find);
        assertEquals("Test", find.getFirstName());
        Mockito.verify(userService, Mockito.times(1)).getByFirstName(Mockito.anyString());
    }

    @Test
    void get_by_last_name() {
        WriterDto find = userService.getByLastName("Dto");

        assertNotNull(find);
        assertEquals("Dto", find.getLastName());
        Mockito.verify(userService, Mockito.times(1)).getByLastName(Mockito.anyString());
    }

    @Test
    void remove_some_writerDto() {
        userService.remove(19L);
        WriterDto find = userService.get(19L);

        assertNull(find);
        Mockito.verify(userService, Mockito.times(1)).remove(Mockito.anyLong());
    }
}