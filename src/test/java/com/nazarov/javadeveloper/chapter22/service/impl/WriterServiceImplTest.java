package com.nazarov.javadeveloper.chapter22.service.impl;

import com.nazarov.javadeveloper.chapter22.entity.Writer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WriterServiceImplTest {

    @Spy
    @InjectMocks
    private WriterServiceImpl writerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void get_by_id() {
        Writer find = writerService.get(2L);

        assertNotNull(find);
        assertEquals(2L, find.getId());
        Mockito.verify(writerService, Mockito.times(1)).get(Mockito.anyLong());
    }

    @Test
    void get_by_first_name() {
        Writer find = writerService.getByFirstName("Anton");

        assertNotNull(find);
        assertEquals("Anton", find.getFirstName());
        Mockito.verify(writerService, Mockito.times(1)).getByFirstName(Mockito.anyString());
    }

    @Test
    void get_by_last_name() {
        Writer find = writerService.getByLastName("Nazarov");

        assertNotNull(find);
        assertEquals("Nazarov", find.getLastName());
        Mockito.verify(writerService, Mockito.times(1)).getByLastName(Mockito.anyString());
    }

    @Test
    void get_by_region() {
        Writer find = writerService.getByRegion(2L);

        assertNotNull(find);
        assertEquals(2L, find.getRegions_id());
        Mockito.verify(writerService, Mockito.times(1)).getByRegion(Mockito.anyLong());
    }

    @Test
    void update_some_writer() {
        Writer updatableWriter = writerService.get(5L);
        Writer updatedWriter = new Writer();
        updatedWriter.setId(updatableWriter.getId());
        updatedWriter.setRegions_id(2L);
        updatedWriter.setFirstName("Vasily");
        updatedWriter.setLastName("Smirnov");
        updatedWriter = writerService.update(updatedWriter);

        assertNotNull(updatedWriter);
        assertEquals(5L, updatedWriter.getId());
        assertEquals(2L, updatedWriter.getRegions_id());
        assertEquals("Vasily", updatedWriter.getFirstName());
        assertEquals("Smirnov", updatedWriter.getLastName());
        Mockito.verify(writerService, Mockito.times(1)).update(Mockito.any(Writer.class));

        updatedWriter = writerService.update(updatableWriter);
        assertNotNull(updatedWriter);
        assertEquals(5L, updatedWriter.getId());
        assertEquals(5L, updatedWriter.getRegions_id());
        assertEquals("Test", updatedWriter.getFirstName());
        assertEquals("root", updatedWriter.getLastName());
        Mockito.verify(writerService, Mockito.times(2)).update(Mockito.any(Writer.class));
    }

    @Test
    void save_some_writer() {
        Writer writer = new Writer(null, 9L, "Gulia", "Briks");
        writer = writerService.save(writer);

        assertNotNull(writer);
        assertNotNull(writer.getId());
        assertEquals(9L, writer.getRegions_id());
        assertEquals("Gulia", writer.getFirstName());
        assertEquals("Briks", writer.getLastName());
        Mockito.verify(writerService, Mockito.times(1)).save(Mockito.any(Writer.class));
    }

    @Test
    void remove() {
        Writer writer = new Writer(12L, 9L, "Gulia", "Briks");
        writerService.remove(writer);
        Writer find = writerService.get(12L);

        assertNull(find);
        Mockito.verify(writerService, Mockito.times(1)).remove(Mockito.any(Writer.class));
    }
}