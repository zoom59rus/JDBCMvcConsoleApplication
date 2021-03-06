package com.nazarov.javadeveloper.chapter22.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Region {

    private Long id;
    private String name;

    public Region(String name) {
        this.id = null;
        this.name = name;
    }
}