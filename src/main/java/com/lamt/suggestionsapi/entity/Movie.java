package com.lamt.suggestionsapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@Builder
@NoArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", nullable = false, unique = true)
    @NonNull
    @NotBlank(message = "Movie title cannot be empty")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "year_of_creation")
    private Integer year;

    @Column(name = "genre")
    private String genre;

    @Column(name = "likes", nullable = false, columnDefinition = "int default 0")
    @NonNull
    private Integer likes;

    @Column(name = "saves", nullable = false, columnDefinition = "int default 0")
    @NonNull
    private Integer saves;

    @Column(name = "user_rating")
    private Long userRating;

    @JsonIgnore
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_like",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"))
    private Set<User> likedBy = new HashSet<>();

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_save",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"))
    private Set<User> savedBy = new HashSet<>();

    @CreationTimestamp
    @Column(name = "timestamp")
    private LocalDateTime created;
}
