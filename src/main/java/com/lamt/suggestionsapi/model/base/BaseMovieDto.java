package com.lamt.suggestionsapi.model.base;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseMovieDto {
    private UUID id;
    private String title;
    private String description;
    private Integer year;
    private BaseUserDto user;
    private String genre;
    private Integer likes;
    private Integer saves;
}
