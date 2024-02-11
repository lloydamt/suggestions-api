package com.lamt.suggestionsapi.model;

import com.lamt.suggestionsapi.model.base.BaseMovieDto;
import com.lamt.suggestionsapi.model.base.BaseUserDto;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private UUID id;
    private String content;
    private BaseUserDto user;
    private BaseMovieDto movie;
    private LocalDateTime timestamp;
}
