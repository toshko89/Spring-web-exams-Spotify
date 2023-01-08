package com.example.spotifyplaylist.model;

import com.example.spotifyplaylist.model.enums.StyleEnums;
import jakarta.persistence.*;

@Entity
@Table(name = "styles")
public class Style {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false,unique = true)
    private StyleEnums style;

    private String description;

    public Style() {
    }

    public long getId() {
        return id;
    }

    public Style setId(long id) {
        this.id = id;
        return this;
    }

    public StyleEnums getStyle() {
        return style;
    }

    public Style setStyle(StyleEnums style) {
        this.style = style;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Style setDescription(String description) {
        this.description = description;
        return this;
    }
}
