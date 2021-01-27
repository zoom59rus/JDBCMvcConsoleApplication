package com.nazarov.javadeveloper.chapter22.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long id;
    private Long writersId;
    private String content;
    private Date created;
    private Date updated;

    public Post(Long writersId, String content) {
        this.id = null;
        this.writersId = writersId;
        this.content = content;
        this.created = null;
        this.updated = null;
    }
}