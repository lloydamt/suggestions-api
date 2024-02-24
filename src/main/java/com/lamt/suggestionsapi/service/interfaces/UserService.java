package com.lamt.suggestionsapi.service.interfaces;

import com.lamt.suggestionsapi.entity.User;
import com.lamt.suggestionsapi.model.CommentDto;
import com.lamt.suggestionsapi.model.UserDto;
import com.lamt.suggestionsapi.model.base.BaseMovieDto;
import com.lamt.suggestionsapi.model.base.BaseUserDto;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserService {
    UserDto getUser(String username);

    UserDto getUser(UUID id);

    UserDto getUserByEmail(String email);

    BaseUserDto createUser(User user);

    void deleteuser(String username);

    Set<BaseMovieDto> getUserSuggestions(String username);

    Set<BaseMovieDto> getUserLikes(String username);

    Set<BaseMovieDto> getUserSaved(String username);

    List<CommentDto> getUserComments(String username);
}
