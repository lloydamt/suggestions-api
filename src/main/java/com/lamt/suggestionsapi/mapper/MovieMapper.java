package com.lamt.suggestionsapi.mapper;

import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.model.MovieDto;
import com.lamt.suggestionsapi.model.base.BaseMovieDto;
import java.util.Set;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDto mapEntityToDto(Movie entity);

    Movie mapDtoToEntity(MovieDto dto);

    Set<BaseMovieDto> mapSetToDto(Set<Movie> entities);

    Set<MovieDto> mapFullSetToDto(Set<Movie> entities);
}
