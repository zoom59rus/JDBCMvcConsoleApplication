package com.nazarov.javadeveloper.chapter22.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Writer {
    private Long id;
    private Long regions_id;
    private String firstName;
    private String lastName;
}