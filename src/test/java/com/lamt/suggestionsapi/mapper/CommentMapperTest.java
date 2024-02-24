package com.lamt.suggestionsapi.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.entity.User;
import com.lamt.suggestionsapi.model.CommentDto;
import com.lamt.suggestionsapi.model.base.BaseMovieDto;
import com.lamt.suggestionsapi.model.base.BaseUserDto;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class CommentMapperTest {
    final CommentMapper mapper = Mappers.getMapper(CommentMapper.class);

    @Test
    void testMapEntityToDto() {
        final var entity = Comment.builder()
                .id(UUID.randomUUID())
                .content("comment")
                .user(User.builder().username("user").build())
                .movie(Movie.builder().title("movie").build())
                .timestamp(LocalDateTime.now())
                .build();

        final var dto = mapper.mapToDto(entity);

        assertEntityEqualsDto(entity, dto);
    }

    @Test
    void testMapDtoToEntity() {
        final var dto = CommentDto.builder()
                .id(UUID.randomUUID())
                .content("comment")
                .user(BaseUserDto.builder().username("user").build())
                .movie(BaseMovieDto.builder().title("movie").build())
                .timestamp(LocalDateTime.now())
                .build();

        final var entity = mapper.mapToEntity(dto);

        assertEntityEqualsDto(entity, dto);
    }

    private static void assertEntityEqualsDto(Comment entity, CommentDto dto) {
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getContent(), dto.getContent());
        assertEquals(entity.getUser().getUsername(), dto.getUser().getUsername());
        assertEquals(entity.getMovie().getTitle(), dto.getMovie().getTitle());
        assertEquals(entity.getTimestamp(), dto.getTimestamp());
    }
}
