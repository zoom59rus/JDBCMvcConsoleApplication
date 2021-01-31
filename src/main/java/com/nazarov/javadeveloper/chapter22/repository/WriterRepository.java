package com.nazarov.javadeveloper.chapter22.repository;


import com.nazarov.javadeveloper.chapter22.entity.Writer;

public interface WriterRepository extends GenericRepository<Writer, Long> {

    Writer getByFirstName(String firstName);
    Writer getByLastName(String lastName);
    Writer getByRegion(Long regionId);
}