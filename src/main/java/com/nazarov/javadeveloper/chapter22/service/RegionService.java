package com.nazarov.javadeveloper.chapter22.service;

import com.nazarov.javadeveloper.chapter22.entity.Region;

public interface RegionService {

    Region get(Long id);
    Region get(String name);
    Region update(Region region);
    Region save(Region region);
    void remove(Region region);
}