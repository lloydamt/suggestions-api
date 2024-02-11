package com.lamt.suggestionsapi.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.entity.User;
import com.lamt.suggestionsapi.model.MovieDto;
import com.lamt.suggestionsapi.model.UserDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class MovieMapperTest {
    final MovieMapper mapper = Mappers.getMapper(MovieMapper.class);

    @Test
    void testMapEntityToDto() {
        final var user = User.builder().username("user").build();
        final var entity = Movie.builder()
                .id(UUID.randomUUID())
                .title("movie")
                .year(2012)
                .description("new movie")
                .user(user)
                .saves(0)
                .likes(0)
                .likedBy(Set.of(user))
                .savedBy(Set.of())
                .created(LocalDateTime.now())
                .comments(List.of())
                .build();

        final var dto = mapper.mapEntityToDto(entity);

        assertEntityEqualsDto(entity, dto);
    }

    @Test
    void testMapDtoToEntity() {
        final var user = UserDto.builder().username("user").build();
        final var dto = MovieDto.builder()
                .id(UUID.randomUUID())
                .title("movie")
                .year(2012)
                .description("new movie")
                .user(user)
                .saves(0)
                .likes(0)
                .likedBy(Set.of(user))
                .savedBy(Set.of())
                .created(LocalDateTime.now())
                .comments(List.of())
                .build();

        final var entity = mapper.mapDtoToEntity(dto);

        assertEntityEqualsDto(entity, dto);
    }

    private static void assertEntityEqualsDto(Movie entity, MovieDto dto) {
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getYear(), dto.getYear());
        assertEquals(entity.getUser().getUsername(), dto.getUser().getUsername());
        assertEquals(entity.getDescription(), dto.getDescription());
        assertEquals(entity.getLikes(), dto.getLikes());
        assertEquals(entity.getSaves(), dto.getSaves());
        assertEquals(entity.getCreated(), dto.getCreated());
        assertEquals(entity.getLikedBy().size(), dto.getLikedBy().size());
        assertEquals(entity.getSavedBy().size(), dto.getSavedBy().size());
        assertEquals(entity.getComments().size(), dto.getComments().size());
    }
}
