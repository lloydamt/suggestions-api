package com.lamt.suggestionsapi.mapper;

import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.model.MovieDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDto mapEntityToDto(Movie entity);

    Movie mapDtoToEntity(MovieDto dto);
}
