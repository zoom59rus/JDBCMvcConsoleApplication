package com.nazarov.javadeveloper.chapter22.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Writer {
    private Long id;
    private Long regions_id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    private Region region;
}