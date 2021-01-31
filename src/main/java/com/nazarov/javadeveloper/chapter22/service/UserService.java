package com.nazarov.javadeveloper.chapter22.service;

import com.nazarov.javadeveloper.chapter22.entity.dtos.WriterDto;

public interface UserService {

    WriterDto save(WriterDto writerDto);
    WriterDto update(WriterDto writerDto);
    WriterDto get(Long id);
    void remove(Long id);

    WriterDto getByFirstName(String firstName);
    WriterDto getByLastName(String lastName);
}