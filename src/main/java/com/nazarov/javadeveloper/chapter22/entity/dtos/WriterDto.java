package com.nazarov.javadeveloper.chapter22.entity.dtos;

import com.nazarov.javadeveloper.chapter22.entity.Post;
import com.nazarov.javadeveloper.chapter22.entity.Region;
import com.nazarov.javadeveloper.chapter22.entity.Writer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WriterDto {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;
    private Region region;

    public static WriterDto fromWriter(Writer writer){
        return new WriterDto(
                writer.getId(),
                writer.getFirstName(),
                writer.getLastName(),
                writer.getPosts(),
                writer.getRegion()
        );
    }

    public static Writer fromWriterDto(WriterDto writerDto){
        return new Writer(
                writerDto.getId(),
                null,
                writerDto.getFirstName(),
                writerDto.getLastName(),
                writerDto.getPosts(),
                writerDto.getRegion()
        );
    }
}