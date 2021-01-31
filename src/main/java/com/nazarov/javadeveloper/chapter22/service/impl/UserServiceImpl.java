package com.nazarov.javadeveloper.chapter22.service.impl;

import com.nazarov.javadeveloper.chapter22.entity.Post;
import com.nazarov.javadeveloper.chapter22.entity.Region;
import com.nazarov.javadeveloper.chapter22.entity.Writer;
import com.nazarov.javadeveloper.chapter22.entity.dtos.WriterDto;
import com.nazarov.javadeveloper.chapter22.repository.PostRepository;
import com.nazarov.javadeveloper.chapter22.repository.RegionRepository;
import com.nazarov.javadeveloper.chapter22.repository.WriterRepository;
import com.nazarov.javadeveloper.chapter22.repository.impl.PostRepositoryImpl;
import com.nazarov.javadeveloper.chapter22.repository.impl.RegionRepositoryImpl;
import com.nazarov.javadeveloper.chapter22.repository.impl.WriterRepositoryImpl;
import com.nazarov.javadeveloper.chapter22.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private final RegionRepository regionRepository;
    private final PostRepository postRepository;
    private final WriterRepository writerRepository;

    public UserServiceImpl() {
        this.regionRepository = new RegionRepositoryImpl();
        this.postRepository = new PostRepositoryImpl();
        this.writerRepository = new WriterRepositoryImpl();
    }

    @Override
    public WriterDto save(WriterDto writerDto) {
        Writer writer = new Writer();
        Region region = new Region(writerDto.getRegion());
        region = regionRepository.save(region);

        writer.setRegions_id(region.getId());
        writer.setFirstName(writerDto.getFirstName());
        writer.setLastName(writerDto.getLastName());
        writerRepository.save(writer);

        writerDto.getPosts().stream()
                .map(p -> new Post(writer.getId(), p))
                .map(postRepository::save)
                .collect(Collectors.toList());

        return writerDto;
    }

    @Override
    public WriterDto update(WriterDto writerDto) {
        return null;
    }

    @Override
    public WriterDto get(Long id) {
        Writer writer = writerRepository.get(id);
        if (writer == null) {
            return null;
        }
        Region region = regionRepository.get(writer.getRegions_id());
        List<String> posts = postRepository.getAllByWriterId(writer.getId()).stream()
                .map(Post::getContent)
                .collect(Collectors.toList());

        return WriterDto.fromWriter(writer, posts, region.getName());
    }

    public WriterDto getByFirstName(String firstName) {
        Writer writer = writerRepository.getByFirstName(firstName);
        if (writer == null) {
            return null;
        } else {
            Region region = regionRepository.get(writer.getRegions_id());
            List<String> posts = postRepository.getAllByWriterId(writer.getId()).stream()
                    .map(Post::getContent)
                    .collect(Collectors.toList());

            return WriterDto.fromWriter(writer, posts, region.getName());
        }
    }

    public WriterDto getByLastName(String lastName) {
        Writer writer = writerRepository.getByLastName(lastName);
        if (writer == null) {
            return null;
        } else {
            Region region = regionRepository.get(writer.getRegions_id());
            List<String> posts = postRepository.getAllByWriterId(writer.getId()).stream()
                    .map(Post::getContent)
                    .collect(Collectors.toList());

            return WriterDto.fromWriter(writer, posts, region.getName());
        }
    }

    @Override
    public void remove(Long id) {
        writerRepository.remove(id);
    }
}
