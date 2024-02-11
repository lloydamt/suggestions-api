package com.lamt.suggestionsapi.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.entity.User;
import com.lamt.suggestionsapi.model.UserDto;
import com.lamt.suggestionsapi.model.base.BaseMovieDto;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class UserMapperTest {
    public final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testMapEntitytoDto() {
        final var id = UUID.randomUUID();
        final var movie = Movie.builder().title("title").likes(1).saves(1).build();
        final var entity = User.builder()
                .email("user@email.com")
                .username("user")
                .password("pass")
                .id(id)
                .movies(Set.of(movie))
                .likes(Set.of(movie))
                .saved(Set.of(movie))
                .build();

        final var dto = mapper.mapEntityToDto(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getUsername(), dto.getUsername());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getMovies().size(), dto.getMovies().size());
        assertEquals(entity.getLikes().size(), dto.getLikes().size());
    }

    @Test
    void testMapDtoToEntity() {
        final var id = UUID.randomUUID();
        final var movieDto = BaseMovieDto.builder().title("movie").build();
        final var dto = UserDto.builder()
                .id(id)
                .username("user")
                .email("user@mail.com")
                .movies(Set.of(movieDto))
                .likes(Set.of(movieDto))
                .saved(Set.of(movieDto))
                .build();

        final var entity = mapper.mapDtoToEntity(dto);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getUsername(), dto.getUsername());
        assertEquals(entity.getEmail(), dto.getEmail());
        assertEquals(entity.getMovies().size(), dto.getMovies().size());
        assertEquals(entity.getLikes().size(), dto.getLikes().size());
    }
}
