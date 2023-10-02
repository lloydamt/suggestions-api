package com.lamt.suggestionsapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "content", nullable = false)
    @NonNull
    @NotBlank(message = "Comment cannot be blank.")
    private String content;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;

    @CreationTimestamp
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
