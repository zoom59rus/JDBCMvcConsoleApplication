package com.nazarov.javadeveloper.chapter22.repository;

import com.nazarov.javadeveloper.chapter22.entity.Region;

public interface RegionRepository extends GenericRepository<Region, Long> {
    Region get(String name);
}
