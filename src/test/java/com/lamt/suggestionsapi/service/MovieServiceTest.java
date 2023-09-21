package com.lamt.suggestionsapi.service;

import com.lamt.suggestionsapi.repository.MovieRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {

    @Mock
    MovieRepository movieRepository;

    @Mock
    UserService userService;

    @InjectMocks
    MovieServiceImpl movieService;

    // @Test
    // public void getMovieTest() {
    //     Movie movie = new Movie();
    //     movie.setId(1L);
    //     movie.setTitle("Movie");
    //     when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

    //     assertEquals(movie, movieService.getMovie(1L));
    // }
    // @Test
    // public void suggestMovieTest() {
    //     User user = new User("username", "password");
    //     user.setId(1L);
    //     Movie movie = new Movie();
    //     movie.setId(1L);
    //     movie.setTitle("Movie");

    //     when(userService.getUser(1L)).thenReturn(user);
    //     when(movieRepository.save(movie)).thenReturn(movie);

    //     assertEquals(movie, movieService.suggestMovie(movie, 1L));
    // }
    // @Test
    // public void editMovieTitleTest() {
    //     String title = "new title";
    //     Movie movie = new Movie();
    //     movie.setId(1L);
    //     movie.setTitle("Movie");
    //     Movie newMovie = new Movie();
    //     User user1 = new User("name", "pass");
    //     user1.setId(1L);
    //     user1.setMovies(new HashSet<>());
    //     user1.getMovies().add(movie);
    //     when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
    //     when(movieRepository.save(movie)).thenReturn(movie);
    //     when(userService.getUser(1L)).thenReturn(user1);
    //     Movie oldMovie = movieRepository.save(movie);
    //     newMovie.setTitle(title);

    //     var editMovie = movieService.editMovie(1L, 1L,  newMovie);

    //     assertEquals("new title", editMovie.getTitle());
    //     assertEquals(oldMovie.getDescription(), editMovie.getDescription());
    //     assertEquals(oldMovie.getYear(), editMovie.getYear());
    // }
    // @Test
    // public void editMovieUserTest() {
    //     Movie movie = new Movie();
    //     User user1 = new User("name", "pass");
    //     User user2 = new User("username", "password");
    //     movie.setId(1L);
    //     movie.setTitle("Movie");
    //     movie.setUser(user1);
    //     Movie newMovie = new Movie();
    //     when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
    //     when(movieRepository.save(movie)).thenReturn(movie);
    //     newMovie.setUser(user2);

    //     var editMovie = movieService.editMovie(1L, 1L, newMovie);
    //     assertEquals(user1, editMovie.getUser());
    // }
    // @Test
    // public void getMovieLikesTest() {
    //     Movie movie = new Movie();
    //     movie.setId(1L);
    //     movie.setLikes(0);
    //     when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

    //     Integer likes = movieService.getMovieLikes(1L);

    //     assertEquals(Integer.valueOf(0), likes);
    // }
    // @Test
    // public void getSavedMoviesTest() {
    //     User user1 = new User("name", "pass");
    //     Set<User> savedBy = new HashSet<>();
    //     savedBy.add(user1);
    //     Movie movie1 = new Movie();
    //     movie1.setId(1L);
    //     movie1.setTitle("Movie 1");
    //     movie1.setSavedBy(savedBy);
    //     Movie movie2 = new Movie();
    //     movie2.setId(2L);
    //     movie2.setTitle("Movie 2");
    //     movie2.setSavedBy(new HashSet<>());
    //     Set<Movie> saved = new HashSet<>();
    //     saved.add(movie1);
    //     Set<Movie> fullList = new HashSet<>();
    //     fullList.add(movie2);
    //     fullList.add(movie1);

    //     when(movieRepository.findAll()).thenReturn(fullList);

    //     assertEquals(saved, movieService.getSavedMovies());
    // }
    // @Test
    // public void getLikedMoviesTest() {
    //     User user1 = new User("name", "pass");
    //     Set<User> likedBy = new HashSet<>();
    //     likedBy.add(user1);
    //     Movie movie1 = new Movie();
    //     movie1.setId(1L);
    //     movie1.setTitle("Movie 1");
    //     movie1.setLikedBy(new HashSet<>());
    //     Movie movie2 = new Movie();
    //     movie2.setId(2L);
    //     movie2.setTitle("Movie 2");
    //     movie2.setLikedBy(likedBy);
    //     Set<Movie> liked = new HashSet<>();
    //     liked.add(movie2);
    //     Set<Movie> fullList = new HashSet<>();
    //     fullList.add(movie2);
    //     fullList.add(movie1);

    //     when(movieRepository.findAll()).thenReturn(fullList);

    //     assertEquals(liked, movieService.getLikedMovies());
    // }
    // @Test
    // public void getMovieByUserTest() {
    //     User user1 = new User("name", "pass");
    //     user1.setId(1L);
    //     Movie movie1 = new Movie();
    //     movie1.setId(1L);
    //     movie1.setTitle("Movie 1");
    //     Movie movie2 = new Movie();
    //     movie2.setId(2L);
    //     movie2.setTitle("Movie 2");
    //     Set<Movie> fullList = new HashSet<>();
    //     fullList.add(movie2);
    //     fullList.add(movie1);
    //     user1.setMovies(fullList);
    //     when(movieRepository.findByUserId(1L)).thenReturn(fullList);

    //     assertEquals(fullList, movieService.getMoviesByUser(1L));
    // }
    // @Test
    // public void getAllMoviesTest() {
    //     Movie movie1 = new Movie();
    //     movie1.setId(1L);
    //     movie1.setTitle("Movie 1");
    //     Movie movie2 = new Movie();
    //     movie2.setId(2L);
    //     movie2.setTitle("Movie 2");
    //     Set<Movie> fullList = new HashSet<>();
    //     fullList.add(movie2);
    //     fullList.add(movie1);
    //     when(movieRepository.findAll()).thenReturn(fullList);

    //     assertEquals(fullList, movieService.getAllMovies());
    // }

    // @Test
    // public void likeMovieTest() {
    //     User user1 = new User("name", "pass");
    //     user1.setId(1L);
    //     Movie movie = new Movie();
    //     movie.setId(1L);
    //     movie.setTitle("Movie 1");
    //     movie.setLikes(0);
    //     movie.setDescription("This is a movie");
    //     movie.setLikedBy(new HashSet<>());
    //     when(userService.getUser(1L)).thenReturn(user1);
    //     when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
    //     when(movieRepository.save(movie)).thenReturn(movie);

    //     assertTrue(movieService.likeMovie(1L, 1L).getLikedBy().contains(user1));
    //     assertEquals(Integer.valueOf(1), movieService.getMovie(1L).getLikes());
    // }
    // @Test
    // public void saveMovieTest() {
    //     User user1 = new User("name", "pass");
    //     user1.setId(1L);
    //     Movie movie = new Movie();
    //     movie.setId(1L);
    //     movie.setTitle("Movie 1");
    //     movie.setDescription("This is a movie");
    //     movie.setSaves(0);
    //     movie.setSavedBy(new HashSet<>());
    //     when(userService.getUser(1L)).thenReturn(user1);
    //     when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
    //     when(movieRepository.save(movie)).thenReturn(movie);

    //     assertTrue(movieService.saveMovie(1L, 1L).getSavedBy().contains(user1));
    //     assertEquals(Integer.valueOf(1), movieService.getMovie(1L).getSaves());
    // }

    // @Test
    // public void getMovieCommentsTest() {
    //     Comment comment = new Comment(1L, "new comment", new User(), new Movie(), LocalDateTime.now());
    //     Comment comment2 = new Comment(2L, "another comment", new User(), new Movie(), LocalDateTime.now());
    //     List<Comment> comments = Arrays.asList(comment, comment2);
    //     Movie movie = new Movie();
    //     movie.setId(1L);
    //     movie.setTitle("a movie");
    //     movie.setComments(comments);

    //     when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

    //     assertEquals(comments, movieService.getMovieComments(1L));
    // }

    // @Test
    // public void unlikeMovieTest() {
    //     User user1 = new User("name", "pass");
    //     user1.setId(1L);
    //     Set<User> likedBy = new HashSet<>();
    //     likedBy.add(user1);
    //     Movie movie = new Movie();
    //     movie.setId(1L);
    //     movie.setTitle("Movie 1");
    //     movie.setDescription("This is a movie");
    //     movie.setLikedBy(likedBy);
    //     when(userService.getUser(1L)).thenReturn(user1);
    //     when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
    //     when(movieRepository.save(movie)).thenReturn(movie);

    //     movieService.unlikeMovie(1L, 1L);

    //     assertFalse(movieService.getMovie(1L).getLikedBy().contains(user1));
    // }

    // @Test
    // public void unsaveMovieTest() {
    //     User user1 = new User("name", "pass");
    //     user1.setId(1L);
    //     Set<User> savedBy = new HashSet<>();
    //     savedBy.add(user1);
    //     Movie movie = new Movie();
    //     movie.setId(1L);
    //     movie.setTitle("Movie 1");
    //     movie.setDescription("This is a movie");
    //     movie.setSavedBy(savedBy);
    //     when(userService.getUser(1L)).thenReturn(user1);
    //     when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
    //     when(movieRepository.save(movie)).thenReturn(movie);

    //     movieService.unsaveMovie(1L, 1L);

    //     assertFalse(movieService.getMovie(1L).getSavedBy().contains(user1));
    // }

    // @Test
    // public void testDeleteMovie() {
    //     User user1 = new User("name", "pass");
    //     user1.setId(1L);
    //     Movie movie = new Movie();
    //     movie.setId(1L);
    //     movie.setTitle("Movie 1");
    //     movie.setDescription("This is a movie");
    //     movie.setUser(user1);
    //     Set<Movie> movies = new HashSet<>();
    //     Movie movie2 = new Movie();
    //     movie2.setId(2L);
    //     movies.add(movie);
    //     movies.add(new Movie());
    //     user1.setMovies(movies);
    //     Set<Movie> updatedMovies = new HashSet<>();
    //     updatedMovies.add(movie2);
    //     when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
    //     when(userService.getUser(1L)).thenReturn(user1);
    //     when(movieRepository.findAll()).thenReturn(movies);
    //     when(movieRepository.deleteByMovieIdAndUserId(1L, 1L)).thenReturn(movie);

    //     assertEquals(movie, movieService.deleteMovie(1L, 1L));
    // }

}
