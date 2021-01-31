package com.nazarov.javadeveloper.chapter22.controllers;

import com.nazarov.javadeveloper.chapter22.entity.Writer;
import com.nazarov.javadeveloper.chapter22.service.WriterService;
import com.nazarov.javadeveloper.chapter22.service.impl.WriterServiceImpl;

public class WriterController {
    private final WriterService writerService;

    public WriterController() {
        this.writerService = new WriterServiceImpl();
    }

    public Writer save(Writer writer){
        return writerService.save(writer);
    }

    public Writer get(Long id){
        return writerService.get(id);
    }

    public Writer getByFirstName(String firstName){
        return writerService.getByFirstName(firstName);
    }

    public Writer getByLastName(String lastName){
        return writerService.getByLastName(lastName);
    }

    public Writer getByRegion(Long regionId){
        return writerService.getByRegion(regionId);
    }

    public Writer update(Writer writer){
        return writerService.update(writer);
    }

    public void remove(Writer writer){
        writerService.remove(writer);
    }
}
