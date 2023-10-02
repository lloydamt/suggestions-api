package com.lamt.suggestionsapi.model.base;

import com.lamt.suggestionsapi.model.UserDto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseMovieDto {
    private UUID id;
    private String title;
    private String description;
    private Integer year;
    private UserDto user;
}
