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
        Writer writer = WriterDto.fromWriterDto(writerDto);
        Region region = writerDto.getRegion();
        List<Post> posts = writerDto.getPosts();

        region = regionRepository.save(region);

        writer.setRegions_id(region.getId());
        writer.setFirstName(writerDto.getFirstName());
        writer.setLastName(writerDto.getLastName());
        writer = writerRepository.save(writer);
        Long writerId = writer.getId();

        posts = posts.stream()
                .peek(p -> p.setWritersId(writerId))
                .map(postRepository::save)
                .collect(Collectors.toList());

        writerDto.setId(writer.getId());
        return writerDto;
    }

    @Override
    public WriterDto update(WriterDto writerDto) {
        Writer writer = writerRepository.get(writerDto.getId());
        Region region = regionRepository.get(writerDto.getRegion().getName());
        if(region == null){
            region = writerDto.getRegion();
            regionRepository.save(region);
        } else regionRepository.update(region);
        writer.setFirstName(writerDto.getFirstName());
        writer.setLastName(writerDto.getLastName());
        writer.setRegions_id(region.getId());
        writerRepository.update(writer);
        List<Post> posts = writerDto.getPosts().stream().peek(p -> postRepository.update(p)).collect(Collectors.toList());

        return writerDto;
    }

    @Override
    public WriterDto get(Long id) {
        Writer writer = writerRepository.get(id);
        if (writer == null) {
            return null;
        }
        Region region = regionRepository.get(writer.getRegions_id());
        List<Post> posts = postRepository.getAllByWriterId(writer.getId());

        return WriterDto.fromWriter(writer);
    }

    public WriterDto getByFirstName(String firstName) {
        Writer writer = writerRepository.getByFirstName(firstName);
        if (writer == null) {
            return null;
        } else {
            Region region = regionRepository.get(writer.getRegions_id());
            List<Post> posts = postRepository.getAllByWriterId(writer.getId());

            return WriterDto.fromWriter(writer);
        }
    }

    public WriterDto getByLastName(String lastName) {
        Writer writer = writerRepository.getByLastName(lastName);
        if (writer == null) {
            return null;
        } else {
            Region region = regionRepository.get(writer.getRegions_id());
            List<Post> posts = postRepository.getAllByWriterId(writer.getId());

            return WriterDto.fromWriter(writer);
        }
    }

    @Override
    public void remove(Long id) {
        Writer writer = writerRepository.get(id);
        List<Post> posts = postRepository.getAllByWriterId(writer.getId());
        posts = posts.stream()
                .peek(p -> postRepository.remove(p.getId()))
                .collect(Collectors.toList());

        writerRepository.remove(id);
    }
}
