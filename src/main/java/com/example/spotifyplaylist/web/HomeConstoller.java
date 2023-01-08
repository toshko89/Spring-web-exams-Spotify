package com.example.spotifyplaylist.web;

import com.example.spotifyplaylist.model.Song;
import com.example.spotifyplaylist.model.User;
import com.example.spotifyplaylist.service.SongService;
import com.example.spotifyplaylist.service.UserService;
import com.example.spotifyplaylist.session.LoggedUserSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

@Controller
public class HomeConstoller {

    private final LoggedUserSession loggedUserSession;
    private final SongService songService;
    private final UserService userService;

    public HomeConstoller(LoggedUserSession loggedUserSession, SongService songService, UserService userService) {
        this.loggedUserSession = loggedUserSession;
        this.songService = songService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        if (!loggedUserSession.isLoggeinIn()) {
            return "redirect:/login";
        }
        List<Song> allPopSongs = this.songService.findAllPopSongs();
        List<Song> allRockSongs = this.songService.findAllRockSongs();
        List<Song> allJazzSongs = this.songService.findAllJazzSongs();

        model.addAttribute("allPopSongs", allPopSongs);
        model.addAttribute("allRockSongs", allRockSongs);
        model.addAttribute("allJazzSongs", allJazzSongs);

        User user = this.userService.findById(loggedUserSession.getId());
        Set<Song> playlist = user.getPlaylist();
        long totalTime = playlist.stream()
                .map(Song::getDuration).mapToLong(Long::longValue).sum();
        model.addAttribute("totalTime", totalTime);
        model.addAttribute("userPlaylist", playlist);

        return "home";
    }

    @GetMapping("/add-to-playlist/{id}")
    public String addSongToPlaylist(@PathVariable("id") long id, Model model) {
        if (!loggedUserSession.isLoggeinIn()) {
            return "redirect:/login";
        }

        this.songService.addSongToUserPlaylist(id, this.loggedUserSession.getId());

        return "redirect:/home";
    }

    @GetMapping("/remove-song-from-playlist/{id}")
    public String removeSong(@PathVariable("id") long songId){
        if(!loggedUserSession.isLoggeinIn()){
            return "redirect:/login";
        }

        this.songService.removeSongById(songId,loggedUserSession.getId());

        return "redirect:/home";
    }

    @GetMapping("/remove-all-songs")
    public String removeAll(){
        if(!loggedUserSession.isLoggeinIn()){
            return "redirect:/login";
        }

        this.songService.clearPlaylist(loggedUserSession.getId());
        return "redirect:/home";
    }
}
