package com.example.spotifyplaylist.service;

import com.example.spotifyplaylist.model.User;
import com.example.spotifyplaylist.model.dto.UserLoginDTO;
import com.example.spotifyplaylist.model.dto.UserRegisterDTO;
import com.example.spotifyplaylist.repository.UserRepo;
import com.example.spotifyplaylist.session.LoggedUserSession;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final LoggedUserSession loggedUserSession;
    private final HttpSession session;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, LoggedUserSession loggedUserSession, HttpSession session) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.loggedUserSession = loggedUserSession;
        this.session = session;
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    public void initUserDB() {
        if (this.userRepo.count() == 0) {
            User user = new User()
                    .setEmail("goshko@abv.bg")
                    .setUsername("Gosho123")
                    .setPassword(passwordEncoder.encode("123123"));

            this.userRepo.save(user);

            User user2 = new User()
                    .setEmail("toshko@abv.bg")
                    .setUsername("Toshko123")
                    .setPassword(passwordEncoder.encode("123123"));

            this.userRepo.save(user2);
        }
    }

    public void registerUser(UserRegisterDTO userRegisterDTO) {
        User user = new User()
                .setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .setUsername(userRegisterDTO.getUsername())
                .setEmail(userRegisterDTO.getEmail());

        this.userRepo.save(user);
    }

    public boolean checkUserData(UserLoginDTO userLoginDTO) {
        User user = this.userRepo.findByUsername(userLoginDTO.getUsername()).orElse(null);

        if (user == null) {
            return false;
        }

        return this.passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword());
    }

    public void login(UserLoginDTO userLoginDTO) {
        User user = this.userRepo.findByUsername(userLoginDTO.getUsername()).orElse(null);

        if (user != null) {
            loggedUserSession.setId(user.getId());
            loggedUserSession.setUsername(user.getUsername());
            loggedUserSession.setEmail(user.getEmail());
        }
    }

    public void logout() {
        this.loggedUserSession.setEmail(null);
        this.loggedUserSession.setId(0);
        this.loggedUserSession.setUsername(null);
        this.session.invalidate();
    }

    public User findById(long userId) {
        return this.userRepo.findById(userId).orElse(null);
    }

    public void save(User user) {
        this.userRepo.save(user);
    }
}
