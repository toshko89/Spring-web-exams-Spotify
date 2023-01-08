package com.example.spotifyplaylist.repository;

import com.example.spotifyplaylist.model.Style;
import com.example.spotifyplaylist.model.enums.StyleEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StyleRepo extends JpaRepository<Style,Long> {
    Style findByStyle(StyleEnums styleEnums);
}
