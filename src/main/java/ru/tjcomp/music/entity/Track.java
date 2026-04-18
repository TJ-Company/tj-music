package ru.tjcomp.music.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tracks")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "created_at")
    private LocalDate createdAt;
}
