package com.example.spotifyplaylist.model.dto;

import com.example.spotifyplaylist.model.Style;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class SongDTO {
    @Size(min = 3, max = 20)
    @NotNull
    private String performer;

    @Size(min = 3, max = 20)
    @NotNull
    private String title;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @NotNull
    @Positive
    private long duration;

    @NotNull
    private String style;

    public SongDTO() {
    }

    public String getPerformer() {
        return performer;
    }

    public SongDTO setPerformer(String performer) {
        this.performer = performer;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SongDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public SongDTO setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public long getDuration() {
        return duration;
    }

    public SongDTO setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public String getStyle() {
        return style;
    }

    public SongDTO setStyle(String style) {
        this.style = style;
        return this;
    }
}
