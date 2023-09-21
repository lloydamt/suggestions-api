package com.lamt.suggestionsapi.service;

import com.lamt.suggestionsapi.repository.CommentRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    MovieService movieService;

    @Mock
    UserService userService;

    @InjectMocks
    CommentServiceImpl commentService;

    // @Test
    // public void getCommentTest() {
    //     Comment comment = new Comment(1L, "new comment", new User(), new Movie(), LocalDateTime.now());
    //     when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

    //     assertEquals(comment, commentService.getComment(1L));
    // }

    // @Test
    // public void getAllCommentsTest() {
    //     Comment comment = new Comment(1L, "new comment", new User(), new Movie(), LocalDateTime.now());
    //     Comment comment2 = new Comment(2L, "another comment", new User(), new Movie(), LocalDateTime.now());
    //     List<Comment> comments = Arrays.asList(comment, comment2);
    //     when(commentRepository.findAll()).thenReturn(comments);

    //     assertEquals(comments, commentService.getAllComments());
    // }

    // @Test
    // public void addCommentTest() {
    //     Movie movie = new Movie();
    //     movie.setId(1L);
    //     movie.setTitle("movie");

    //     User user = new User("user", "password");
    //     user.setId(1L);

    //     Comment comment = new Comment(1L, "new comment", new User(), movie, LocalDateTime.now());
    //     when(movieService.getMovie(1L)).thenReturn(movie);
    //     when(commentRepository.save(comment)).thenReturn(comment);
    //     when(userService.getUser(1L)).thenReturn(user);

    //     assertEquals(comment, commentService.addComment(comment, 1L, 1L));
    //     assertEquals("movie", commentService.addComment(comment, 1L, 1L).getMovie().getTitle());
    // }

    // @Test
    // public void findCommentsByUser() {
    //     User user = new User("username", "password");
    //     user.setId(1L);
    //     Comment comment = new Comment(1L, "new comment", user, new Movie(), LocalDateTime.now());
    //     Comment comment2 = new Comment(2L, "another comment", user, new Movie(), LocalDateTime.now());
    //     List<Comment> comments = Arrays.asList(comment, comment2);
    //     Movie movie = new Movie();
    //     movie.setId(1L);
    //     movie.setComments(comments);

    //     when(commentRepository.findByUserIdAndMovieId(1L, 1L)).thenReturn(comments);

    //     assertEquals(comments, commentService.findUserCommentForMovie(1L, 1L));

    // }
}
