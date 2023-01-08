package com.example.spotifyplaylist.init;

import com.example.spotifyplaylist.service.SongService;
import com.example.spotifyplaylist.service.StyleService;
import com.example.spotifyplaylist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInit implements CommandLineRunner {

    private final StyleService styleService;
    private final SongService songService;
    private final UserService userService;

    @Autowired
    public DBInit(StyleService styleService, SongService songService, UserService userService) {
        this.styleService = styleService;
        this.songService = songService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.styleService.initStyleDB();
        this.songService.initSongDB();
        this.userService.initUserDB();
    }
}
