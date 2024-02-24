package com.lamt.suggestionsapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lamt.suggestionsapi.entity.Comment;
import com.lamt.suggestionsapi.entity.Movie;
import com.lamt.suggestionsapi.entity.User;
import com.lamt.suggestionsapi.mapper.CommentMapper;
import com.lamt.suggestionsapi.model.CommentDto;
import com.lamt.suggestionsapi.model.MovieDto;
import com.lamt.suggestionsapi.model.UserDto;
import com.lamt.suggestionsapi.repository.CommentRepository;
import com.lamt.suggestionsapi.service.interfaces.MovieService;
import com.lamt.suggestionsapi.service.interfaces.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
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

    final CommentMapper mapper = Mappers.getMapper(CommentMapper.class);

    @BeforeEach
    void setUp() {
        commentService = new CommentServiceImpl(commentRepository, mapper, movieService, userService);
    }

    @Test
    public void getCommentTest() {
        Comment comment = buildComment();
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        final var result = commentService.getComment(UUID.randomUUID());

        assertEquals(comment.getContent(), result.getContent());
    }

    @Test
    public void getAllCommentsTest() {
        final var comments = List.of(buildComment());

        when(commentRepository.findAll()).thenReturn(comments);

        final var result = commentService.getAllComments();

        assertEquals(1, result.size());
        assertEquals("comment", result.get(0).getContent());
    }

    @Test
    public void addCommentTest() {
        final var id = UUID.randomUUID();

        final var comment = CommentDto.builder().content("comment").build();
        when(movieService.getMovie(any()))
                .thenReturn(MovieDto.builder().title("movie").build());
        when(userService.getUser(any(UUID.class)))
                .thenReturn(UserDto.builder().username("user").build());
        when(commentRepository.save(any())).thenReturn(buildComment());

        var savedComment = commentService.addComment(
                CommentDto.builder().content("comment").build(), id, id);
        assertEquals(comment.getContent(), savedComment.getContent());
    }

    @Test
    public void findCommentsByUser() {
        final var id = UUID.randomUUID();

        when(commentRepository.findAllByUserIdAndMovieId(any(), any())).thenReturn(List.of(buildComment()));

        var savedComments = commentService.findUserCommentForMovie(id, id);

        assertEquals(1, savedComments.size());
        assertEquals("comment", savedComments.get(0).getContent());
    }

    @Test
    public void deleteCommentTest() {
        final var id = UUID.randomUUID();
        final var comment = Comment.builder()
                .content("comment")
                .user(User.builder().username("user").id(id).build())
                .build();

        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));
        commentService.deleteComment(UUID.randomUUID(), id);

        verify(commentRepository).deleteById(any());
    }

    private Comment buildComment() {
        return Comment.builder()
                .content("comment")
                .user(User.builder().username("user").build())
                .movie(Movie.builder().title("movie").build())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
