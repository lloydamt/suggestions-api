package com.lamt.suggestionsapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.entity.User;
import com.lamt.suggestionsapi.repository.CommentRepository;
import com.lamt.suggestionsapi.service.interfaces.MovieService;
import com.lamt.suggestionsapi.service.interfaces.UserService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    MovieService movieService;

    @Mock
    UserService userService;

    @InjectMocks
    CommentServiceImpl commentService;

    @Test
    public void getCommentTest() {
        Comment comment = buildComment("new comment");
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        assertEquals(comment, commentService.getComment(UUID.randomUUID()));
    }

    @Test
    public void getAllCommentsTest() {
        Comment comment = buildComment("new comment");
        Comment comment2 = buildComment("another comment");
        List<Comment> comments = Arrays.asList(comment, comment2);
        when(commentRepository.findAll()).thenReturn(comments);

        assertEquals(comments, commentService.getAllComments());
    }

    @Test
    public void addCommentTest() {
        var id = UUID.randomUUID();
        Movie movie = buildMovie().toBuilder().id(id).build();
        User user = buildUser().toBuilder().id(id).build();

        Comment comment = buildComment("new comment");
        when(movieService.getMovie(id)).thenReturn(movie);
        when(userService.getUser(id)).thenReturn(user);
        when(commentRepository.save(comment)).thenReturn(comment);

        var savedComment = commentService.addComment(comment, id, id);
        assertEquals(comment, savedComment);
        assertEquals("title1", savedComment.getMovie().getTitle());
    }

    @Test
    public void findCommentsByUser() {
        var id = UUID.randomUUID();
        Movie movie = buildMovie().toBuilder().id(id).build();

        Comment comment = buildComment("new comment");
        Comment comment2 = buildComment("another comment");
        var comments = Stream.of(comment, comment2).toList();
        movie.setComments(comments);

        when(commentRepository.findAllByUserIdAndMovieId(any(), any())).thenReturn(comments);

        var savedComments = commentService.findUserCommentForMovie(id, id);

        assertEquals(comments, savedComments);
    }

    @Test
    public void deleteCommentTest() {
        var id = UUID.randomUUID();
        var user = buildUser().toBuilder().id(id).build();
        var comment = buildComment("new comment").toBuilder().user(user).build();
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));
        commentService.deleteComment(UUID.randomUUID(), id);

        verify(commentRepository).deleteById(any());
    }

    private Comment buildComment(String content) {
        return Comment.builder()
                .content(content)
                .user(buildUser())
                .movie(buildMovie())
                .timestamp(LocalDateTime.now())
                .build();
    }

    private User buildUser() {
        return User.builder()
                .email("user@gmail.com")
                .username("username")
                .password("password")
                .likes(new HashSet<Movie>())
                .saved(new HashSet<Movie>())
                .build();
    }

    private Movie buildMovie() {
        return Movie.builder()
                .title("title1")
                .description("desc")
                .year(2022)
                .likes(0)
                .saves(0)
                .build();
    }
}
