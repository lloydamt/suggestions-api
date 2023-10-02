package com.lamt.suggestionsapi.model;

import com.lamt.suggestionsapi.model.base.BaseMovieDto;
import com.lamt.suggestionsapi.model.base.BaseUserDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto extends BaseMovieDto {
    private String genre;
    private Integer likes;
    private Integer saves;
    private List<CommentDto> comments;
    private Set<BaseUserDto> likedBy;
    private Set<BaseUserDto> savedBy;
    private LocalDateTime created;
}
