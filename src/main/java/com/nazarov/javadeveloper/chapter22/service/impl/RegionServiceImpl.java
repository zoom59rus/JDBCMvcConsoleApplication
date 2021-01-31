package com.nazarov.javadeveloper.chapter22.service.impl;

import com.nazarov.javadeveloper.chapter22.entity.Region;
import com.nazarov.javadeveloper.chapter22.repository.RegionRepository;
import com.nazarov.javadeveloper.chapter22.repository.impl.RegionRepositoryImpl;
import com.nazarov.javadeveloper.chapter22.service.RegionService;

public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    public RegionServiceImpl() {
        this.regionRepository =  new RegionRepositoryImpl();
    }

    @Override
    public Region get(Long id) {
        return regionRepository.get(id);
    }

    @Override
    public Region get(String name) {
        return regionRepository.get(name);
    }



    @Override
    public Region update(Region region) {
        return regionRepository.update(region);
    }

    @Override
    public Region save(Region region) {
        return regionRepository.save(region);
    }

    @Override
    public void remove(Region region) {
        regionRepository.remove(region.getId());
    }
}
