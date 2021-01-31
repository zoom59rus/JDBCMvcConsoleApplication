package com.nazarov.javadeveloper.chapter22.controllers;

import com.nazarov.javadeveloper.chapter22.entity.Region;
import com.nazarov.javadeveloper.chapter22.service.RegionService;
import com.nazarov.javadeveloper.chapter22.service.impl.RegionServiceImpl;

public class RegionController {
    private final RegionService regionService;

    public RegionController() {
        this.regionService = new RegionServiceImpl();
    }

    public Region get(Long id) {
        return regionService.get(id);
    }

    public Region get(String name){
        return regionService.get(name);
    }

    public Region update(Region region) {
        return regionService.update(region);
    }

    public Region save(Region region) {
        return regionService.save(region);
    }

    public void remove(Region region) {
        regionService.remove(region);
    }
}
