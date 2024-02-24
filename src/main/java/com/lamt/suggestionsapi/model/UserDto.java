package com.lamt.suggestionsapi.model;

import com.lamt.suggestionsapi.model.base.BaseMovieDto;
import com.lamt.suggestionsapi.model.base.BaseUserDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BaseUserDto {
    private Set<BaseMovieDto> movies;
    private Set<BaseMovieDto> likes;
    private Set<BaseMovieDto> saved;
    private List<CommentDto> comments;
    private LocalDateTime joined;
}
