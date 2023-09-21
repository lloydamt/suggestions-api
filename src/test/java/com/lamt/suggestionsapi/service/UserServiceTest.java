package com.lamt.suggestionsapi.service;

import com.lamt.suggestionsapi.repository.UserRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    // @Test
    // public void getUserTest() {
    //     User user = buildUser();
    //     when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

    //     assertEquals(userService.getUser("username"), user);
    //     assertEquals(userService.getUser("username").getUsername(), user.getUsername());
    // }

    // @Test
    // public void getNonExistentUserTest() {
    //     RuntimeException exception = assertThrows(RuntimeException.class, () -> {
    //         userService.getUser("username");
    //     });
    //     assertEquals(exception.getMessage(), "user not found");
    // }

    // @Test
    // public void createUserTest() {
    //     User user = buildUser();
    //     when(userRepository.save(user)).thenReturn(user);

    //     assertEquals(userService.createUser(user), user);
    // }

    // // @Test
    // // public void createDuplicateUserTest() {
    // //     User user = new User("username", "password");
    // //     User user2 = new User("username", "password");
    // //     when(userRepository.save(user)).thenReturn(user);
    // //     userService.createUser(user);
    // //     RuntimeException exception = assertThrows(RuntimeException.class, () -> {
    // //         userService.createUser(user);
    // //     });
    // //     assertEquals(exception.getMessage(), "user already exists");
    // // }

    // @Test
    // public void getUserSuggestionsTest() {
    //     User user = buildUser();
    //     Movie movie1 = new Movie("title", "desc", 2022, "genre", 0, 3L);
    //     Movie movie2 = new Movie("title2", "desc", 2022, "genre", 0, 3L);
    //     Set<Movie> movies = new HashSet<>();
    //     movies.add(movie1);
    //     movies.add(movie2);
    //     user.setId(1L);
    //     user.setMovies(movies);
    //     when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    //     assertEquals(userService.getUserSuggestions("username2"), movies);
    // }

    // @Test
    // public void getUserLikesTest() {
    //     User user = buildUser();
    //     Movie movie1 = new Movie("title", "desc", 2022, "genre", 0, 3L);
    //     Movie movie2 = new Movie("title2", "desc", 2022, "genre", 0, 3L);
    //     Set<Movie> movies = new HashSet<>();
    //     movies.add(movie1);
    //     movies.add(movie2);
    //     user.setId(1L);
    //     user.setLikes(movies);
    //     when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    //     assertEquals(userService.getUserLikes(1L), movies);
    // }

    // @Test
    // public void getUserSavedTest() {
    //     User user = buildUser();
    //     Movie movie1 = new Movie("title", "desc", 2022, "genre", 0, 3L);
    //     Movie movie2 = new Movie("title2", "desc", 2022, "genre", 0, 3L);
    //     Set<Movie> movies = new HashSet<>();
    //     movies.add(movie1);
    //     movies.add(movie2);
    //     user.setId(1L);
    //     user.setSaved(movies);
    //     when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    //     assertEquals(userService.getUserSaved(1L), movies);
    // }

    // @Test
    // public void getUserCommentsTest() {
    //     User user = buildUser();
    //     List<Comment> comments = new ArrayList<>();
    //     comments.add(new Comment());
    //     comments.add(new Comment());
    //     user.setId(1L);
    //     user.setComments(comments);
    //     when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    //     assertEquals(userService.getUserComments(1L), comments);
    // }

    // private User buildUser() {
    //     return User.builder()
    //                 .email("username")
    //                 .password("password")
    //                 .id(1L)
    //                 .build();
    // }

}
