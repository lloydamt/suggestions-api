package com.lamt.suggestionsapi.mapper;

import com.lamt.suggestionsapi.entity.User;
import com.lamt.suggestionsapi.model.UserDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto mapEntityToDto(User entity);

    User mapDtoToEntity(UserDto dto);
}
