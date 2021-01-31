package com.nazarov.javadeveloper.chapter22.service;

import com.nazarov.javadeveloper.chapter22.entity.Writer;

public interface WriterService {
    Writer get(Long id);
    Writer getByFirstName(String firstName);
    Writer getByLastName(String lastName);
    Writer getByRegion(Long regionId);
    Writer update(Writer writer);
    Writer save(Writer writer);
    void remove(Writer writer);
}
