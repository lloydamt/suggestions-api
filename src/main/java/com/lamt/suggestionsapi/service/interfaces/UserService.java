package com.lamt.suggestionsapi.service.interfaces;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.entity.User;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserService {
    User getUser(String username);

    User getUser(UUID id);

    User getUserByEmail(String email);

    User createUser(User user);

    void deleteuser(String username);

    Set<Movie> getUserSuggestions(String username);

    Set<Movie> getUserLikes(String username);

    Set<Movie> getUserSaved(String username);

    List<Comment> getUserComments(String username);
}
