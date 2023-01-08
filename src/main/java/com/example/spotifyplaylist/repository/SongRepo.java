package com.example.spotifyplaylist.repository;

import com.example.spotifyplaylist.model.Song;
import com.example.spotifyplaylist.model.Style;
import com.example.spotifyplaylist.model.enums.StyleEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepo extends JpaRepository<Song,Long> {
    List<Song> findByStyle(Style style);
}
