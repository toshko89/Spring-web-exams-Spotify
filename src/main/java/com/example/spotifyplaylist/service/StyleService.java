package com.example.spotifyplaylist.service;

import com.example.spotifyplaylist.model.Style;
import com.example.spotifyplaylist.model.enums.StyleEnums;
import com.example.spotifyplaylist.repository.StyleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class StyleService {

    private final StyleRepo styleRepo;

    @Autowired
    public StyleService(StyleRepo styleRepo) {
        this.styleRepo = styleRepo;
    }

    public Style getStyleByName(String name){
        return this.styleRepo.findByStyle(StyleEnums.valueOf(name));
    }

    public Style getStyleById(long id){
       return this.styleRepo.findById(id).orElse(null);
    }

    public void initStyleDB() {
        if (this.styleRepo.count() == 0) {
            Arrays.stream(StyleEnums.values())
                    .forEach(value -> {
                        Style style = new Style().setStyle(value);
                        this.styleRepo.save(style);
                    });
        }
    }
}
