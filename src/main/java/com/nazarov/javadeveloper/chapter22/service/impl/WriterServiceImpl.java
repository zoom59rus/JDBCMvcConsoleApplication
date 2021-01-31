package com.nazarov.javadeveloper.chapter22.service.impl;

import com.nazarov.javadeveloper.chapter22.entity.Writer;
import com.nazarov.javadeveloper.chapter22.repository.WriterRepository;
import com.nazarov.javadeveloper.chapter22.repository.impl.WriterRepositoryImpl;
import com.nazarov.javadeveloper.chapter22.service.WriterService;

public class WriterServiceImpl implements WriterService {
    private final WriterRepository writerRepository;

    public WriterServiceImpl() {
        this.writerRepository = new WriterRepositoryImpl();
    }

    @Override
    public Writer get(Long id) {
        return writerRepository.get(id);
    }

    @Override
    public Writer getByFirstName(String firstName) {
        return writerRepository.getByFirstName(firstName);
    }

    @Override
    public Writer getByLastName(String lastName) {
        return writerRepository.getByLastName(lastName);
    }

    @Override
    public Writer getByRegion(Long regionId) {
        return writerRepository.getByRegion(regionId);
    }

    @Override
    public Writer update(Writer writer) {
        return writerRepository.update(writer);
    }

    @Override
    public Writer save(Writer writer) {
        return writerRepository.save(writer);
    }

    @Override
    public void remove(Writer writer) {
        writerRepository.remove(writer.getId());
    }
}
