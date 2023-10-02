package com.lamt.suggestionsapi.web;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.entity.User;
import com.lamt.suggestionsapi.service.interfaces.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<String> getUser(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUser(username).getUsername(), HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        return new ResponseEntity<>(userService.getUserByEmail(authentication.getName()), HttpStatus.OK);
    }

    @GetMapping("/{username}/suggestions")
    public ResponseEntity<Set<Movie>> getUserSuggestions(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserSuggestions(username), HttpStatus.OK);
    }

    @GetMapping("/{username}/likes")
    public ResponseEntity<Set<Movie>> getUserLikes(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserLikes(username), HttpStatus.OK);
    }

    @GetMapping("/{username}/saves")
    public ResponseEntity<Set<Movie>> getUserSaves(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserSaved(username), HttpStatus.OK);
    }

    @GetMapping("/{username}/comments")
    public ResponseEntity<List<Comment>> getUserComments(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserComments(username), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user).getUsername(), HttpStatus.CREATED);
    }
}
