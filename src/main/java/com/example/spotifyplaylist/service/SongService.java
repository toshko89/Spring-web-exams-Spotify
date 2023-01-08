package com.example.spotifyplaylist.service;

import com.example.spotifyplaylist.model.Song;
import com.example.spotifyplaylist.model.User;
import com.example.spotifyplaylist.model.dto.SongDTO;
import com.example.spotifyplaylist.repository.SongRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SongService {

    private final SongRepo songRepo;
    private final StyleService styleService;
    private final UserService userService;

    @Autowired
    public SongService(SongRepo songRepo, StyleService styleService, UserService userService) {
        this.songRepo = songRepo;
        this.styleService = styleService;
        this.userService = userService;
    }

    public void initSongDB() {
        if (this.songRepo.count() == 0) {
            Song song = new Song()
                    .setDuration(5)
                    .setPerformer("Madona")
                    .setTitle("Like a Virgin")
                    .setReleaseDate(LocalDate.now())
                    .setStyle(styleService.getStyleById(2));
            this.songRepo.save(song);

            Song song2 = new Song()
                    .setDuration(3)
                    .setPerformer("Madona")
                    .setTitle("Material Girl")
                    .setReleaseDate(LocalDate.now())
                    .setStyle(styleService.getStyleById(1));
            this.songRepo.save(song2);

            Song song3 = new Song()
                    .setDuration(4)
                    .setPerformer("Madona")
                    .setTitle("Vogue")
                    .setReleaseDate(LocalDate.now())
                    .setStyle(styleService.getStyleById(1));
            this.songRepo.save(song3);
        }
    }

    public void addSong(SongDTO songDTO) {
        Song song = new Song()
                .setStyle(this.styleService.getStyleByName(songDTO.getStyle()))
                .setReleaseDate(songDTO.getReleaseDate())
                .setPerformer(songDTO.getPerformer())
                .setDuration(5)
                .setTitle(songDTO.getTitle());
        this.songRepo.save(song);
    }

    public List<Song> findAllPopSongs() {
        return this.songRepo.findByStyle(this.styleService.getStyleByName("POP"));
    }

    public List<Song> findAllJazzSongs() {
        return this.songRepo.findByStyle(this.styleService.getStyleByName("JAZZ"));
    }

    public List<Song> findAllRockSongs() {
        return this.songRepo.findByStyle(this.styleService.getStyleByName("ROCK"));
    }

    public void addSongToUserPlaylist(long songId, long userId) {
        Song song = this.songRepo.findById(songId).orElse(null);
        User user = this.userService.findById(userId);
        if (song != null && user != null) {
            user.getPlaylist().add(song);
            this.userService.save(user);
        }
    }

    public void clearPlaylist(long userId) {
        User user = this.userService.findById(userId);
        user.getPlaylist().clear();
        this.userService.save(user);
    }


    public void removeSongById(long songId, long userId) {
        User user = this.userService.findById(userId);
        Song song = this.songRepo.findById(songId).orElse(null);

        if (song != null) {
            user.getPlaylist().remove(song);
            this.userService.save(user);
        }
    }
}
