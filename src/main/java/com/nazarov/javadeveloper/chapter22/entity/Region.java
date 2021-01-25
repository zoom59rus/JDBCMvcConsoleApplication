package com.nazarov.javadeveloper.chapter22.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Region {

    private Long id;
    private String name;

    public Region() {
    }
}