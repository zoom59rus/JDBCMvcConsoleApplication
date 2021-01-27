package com.nazarov.javadeveloper.chapter22.repository.impl;

import com.nazarov.javadeveloper.chapter22.ObjectFactory;
import com.nazarov.javadeveloper.chapter22.entity.Post;
import com.nazarov.javadeveloper.chapter22.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class PostRepositoryImpl implements PostRepository {
    private final Logger log = LoggerFactory.getLogger("PostRepositoryImpl");
    private final Connection conn;

    public PostRepositoryImpl() {
        this.conn = ObjectFactory.getInstance().getConnection();
    }

    @Override
    public Post save(Post entity) {
        String sqlQuery = String.format("INSERT posts VALUES(null, %s, null, null)", entity.getContent());
        return null;
    }

    @Override
    public Post get(Long primaryKey) {
        return null;
    }

    @Override
    public Post update(Post entity) {
        return null;
    }

    @Override
    public void remove(Long primaryKey) {

    }
}