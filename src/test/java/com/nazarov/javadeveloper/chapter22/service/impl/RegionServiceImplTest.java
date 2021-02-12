package com.nazarov.javadeveloper.chapter22.service.impl;

import com.nazarov.javadeveloper.chapter22.entity.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RegionServiceImplTest {

    @Spy
    @InjectMocks
    private RegionServiceImpl regionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void get_by_id() {
        Region region = regionService.get(2L);

        assertNotNull(region);
        assertEquals(2L, region.getId());
        assertNotNull(region.getName());
        Mockito.verify(regionService, Mockito.times(1)).get(Mockito.anyLong());
    }

    @Test
    void get_by_name() {
        Region region = regionService.get("Permsky kray");

        assertNotNull(region);
        assertNotNull(region.getId());
        assertEquals("Permsky kray", region.getName());
        Mockito.verify(regionService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    void update_some_region() {
        Region updatableRegion = regionService.get(4L);
        String oldName = updatableRegion.getName();
        String newName = "Alaska";
        updatableRegion.setName(newName);
        updatableRegion = regionService.update(updatableRegion);

        assertNotNull(updatableRegion);
        assertEquals(4L, updatableRegion.getId());
        assertEquals("Alaska", updatableRegion.getName());
        Mockito.verify(regionService, Mockito.times(1)).update(Mockito.any(Region.class));

        updatableRegion.setName(oldName);
        regionService.update(updatableRegion);
        Mockito.verify(regionService, Mockito.times(2)).update(Mockito.any(Region.class));

    }

    @Test
    void save_some_region() {
        Region saveRegion = new Region(null, "ViceCity");
        saveRegion = regionService.save(saveRegion);

        assertNotNull(saveRegion.getId());
        assertEquals("ViceCity", saveRegion.getName());
        Mockito.verify(regionService, Mockito.times(1)).save(Mockito.any(Region.class));
    }

    @Test
    void remove_some_region() {
        Region removeRegion = new Region(11L, "ViceCity");
        regionService.remove(removeRegion);
        Region find = regionService.get(11L);

        assertNull(find);
        Mockito.verify(regionService, Mockito.times(1)).remove(Mockito.any(Region.class));
    }
}