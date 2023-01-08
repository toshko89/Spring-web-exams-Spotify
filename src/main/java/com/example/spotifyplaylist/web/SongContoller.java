package com.example.spotifyplaylist.web;

import com.example.spotifyplaylist.model.dto.SongDTO;
import com.example.spotifyplaylist.service.SongService;
import com.example.spotifyplaylist.session.LoggedUserSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SongContoller {

    private final SongService songService;
    private final LoggedUserSession loggedUserSession;

    public SongContoller(SongService songService, LoggedUserSession loggedUserSession) {
        this.songService = songService;
        this.loggedUserSession = loggedUserSession;
    }

    @ModelAttribute
    public SongDTO initSongDTO(){
        return new SongDTO();
    }

    @GetMapping("add-song")
    public String addSong() {
        if (!loggedUserSession.isLoggeinIn()) {
            return "redirect:/login";
        }
        return "song-add";
    }

    @PostMapping("add-song")
    public String addSong(@Valid SongDTO songDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (!loggedUserSession.isLoggeinIn()) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()){
            redirectAttributes
                    .addFlashAttribute("songDTO", songDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.songDTO", bindingResult);
            return "redirect:/add-song";
        }

        this.songService.addSong(songDTO);
        return "redirect:/home";
    }
}
