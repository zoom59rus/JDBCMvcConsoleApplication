package com.nazarov.javadeveloper.chapter22.entity.dtos;

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
    private List<String> posts;
    private String region;

    public static WriterDto fromWriter(Writer writer, List<String> posts, String region){
        return new WriterDto(
                writer.getId(),
                writer.getFirstName(),
                writer.getLastName(),
                posts,
                region
        );
    }
}