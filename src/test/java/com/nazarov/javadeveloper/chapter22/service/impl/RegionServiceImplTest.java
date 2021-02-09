package com.nazarov.javadeveloper.chapter22.service.impl;

import com.nazarov.javadeveloper.chapter22.entity.Region;
import com.nazarov.javadeveloper.chapter22.repository.RegionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RegionServiceImplTest {

    @Mock
    private RegionRepository regionRepository;

    @Test
    void getById() {
        Mockito.when(regionRepository.get(1L)).thenReturn(new Region(1L, "Test region"));
        Region region = regionRepository.get(1L);

        assertEquals(1L, region.getId());
        assertEquals("Test region", region.getName());
        Mockito.verify(regionRepository).get(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(regionRepository);
    }

    @Test
    void getByName() {
        Mockito.when(regionRepository.get("test")).thenReturn(new Region(2L, "test"));
        Region region = regionRepository.get("test");

        assertEquals(2L, region.getId());
        assertEquals("test", region.getName());
        Mockito.verify(regionRepository).get(Mockito.anyString());
        Mockito.verifyNoMoreInteractions(regionRepository);
    }

    @Test
    void update() {
        List<Region> regions = new ArrayList<>();
        regions.add(new Region(1L, "One"));
        regions.add(new Region(2L, "Two"));
        regions.add(new Region(3L, "Three"));
        regions.add(new Region(4L, "Fore"));

        Mockito.when(regionRepository.update(Mockito.any(Region.class))).thenAnswer(i -> {
            Region region = (Region) i.getArguments()[0];
            if(regions.contains(region)){
                Region find = regions.stream()
                        .filter(r -> r.getId().equals(region.getId()))
                        .findFirst().get();
                regions.remove(find);
                regions.add(region);
            }
            return region;
        });

        Region containsRegion = new Region(2L, "Two");
        Region testUpdateRegion = regionRepository.update(new Region(2L, "Ten"));


        assertEquals(containsRegion.getId(), testUpdateRegion.getId());
        assertNotEquals(containsRegion.getName(), testUpdateRegion.getName());
        Mockito.verify(regionRepository, Mockito.times(1)).update(testUpdateRegion);
    }

    @Test
    void save() {
        Mockito.when(regionRepository.save(Mockito.any(Region.class))).thenReturn(new Region(33L, "Saved"));
        Region region = regionRepository.save(new Region(null, "Saved"));

        assertEquals(33L, region.getId());
        assertEquals("Saved", region.getName());
        Mockito.verify(regionRepository, Mockito.times(1)).save(Mockito.any(Region.class));

    }

    @Test
    void remove() {
        regionRepository.remove(1L);
        Mockito.verify(regionRepository, Mockito.times(1)).remove(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(regionRepository);
    }
}