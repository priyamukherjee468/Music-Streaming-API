package com.example.Music.Streaming.API.Controller;

import com.example.Music.Streaming.API.Model.Dto.SignInInput;
import com.example.Music.Streaming.API.Model.Dto.SignUpOutput;
import com.example.Music.Streaming.API.Model.PlayList;
import com.example.Music.Streaming.API.Model.Role;
import com.example.Music.Streaming.API.Model.Song;
import com.example.Music.Streaming.API.Model.User;
import com.example.Music.Streaming.API.Repository.IUserRepo;
import com.example.Music.Streaming.API.Service.AuthenticationService;
import com.example.Music.Streaming.API.Service.PlayListService;
import com.example.Music.Streaming.API.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    IUserRepo userRepo;
    @Autowired
    PlayListService playListService;
    @PostMapping("user/signup")
    public SignUpOutput signUpUser(@RequestBody User user)
    {

        return userService.signUpUser(user);
    }
    @PostMapping("user/signIn")
    public String sigInUser(@RequestBody @Valid SignInInput signInInput)
    {
        return userService.signInUser(signInInput);
    }

    @DeleteMapping("user/signOut")
    public String sigOutPatient(String email, String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.sigOutPatient(email);
        }
        else {
            return "Sign out not allowed for non authenticated user.";
        }

    }


    @PostMapping("songs/add")
    public String addSong(@RequestBody Song song, @RequestParam String email, @RequestParam String token){
        Role userRole=userRepo.findFirstByUserEmail(email).getUserRole();
        if(userRole.equals(Role.ADMIN)){
            if(authenticationService.authenticate(email,token)){
                boolean status = userService.addSong(song);
                return status ? "song added":"error occurred during added song";
            }
            else
            {
                return "failed because invalid authentication";
            }
        }else{
            return "user can't able to added songs";
        }

    }
    @DeleteMapping("Song/cancel")
    public String  cancelSong(@RequestParam String newemail, @RequestParam String token) {
        if (userRepo.findFirstByUserEmail(newemail).getUserRole().equals(Role.ADMIN)) {
            if (authenticationService.authenticate(newemail, token)) {
                userService.cancelSong(newemail);
                return "canceled successfully";
            } else {
                return "Failed because invalid authentication";
            }
        }
        return "song not canceled.";
    }
    @PutMapping("updateSong/{id}")
    public String updateSong(@PathVariable Long id, @RequestBody Song song, @RequestParam String email, @RequestParam String token) {
        if (userRepo.findFirstByUserEmail(email).getUserRole().equals(Role.ADMIN)) {
            if (authenticationService.authenticate(email, token)) {
                userService.updateSong(token,id,song);
                return "updated successfully";
            } else {
                return "Failed because invalid authentication";
            }
        }
        return "song not possible to update.";
    }

    @PostMapping("PlayList/add")
    public String addPlayList(@RequestBody PlayList playList, @RequestParam String email, @RequestParam String token){
        Role userRole=userRepo.findFirstByUserEmail(email).getUserRole();
        if(userRole.equals(Role.NORMAL)){
            if(authenticationService.authenticate(email,token)){
                boolean status = userService.addPlayList(playList);
                return status ? "PlayList added":"error occurred during added playlist";
            }
            else
            {
                return "failed because invalid authentication";
            }
        }else{
            return "user can't able to added playlist";
        }

    }
    @DeleteMapping("PlayList/cancel")
    public String  cancelPlayList(@RequestParam String email, @RequestParam String token) {
        if (userRepo.findFirstByUserEmail(email).getUserRole().equals(Role.NORMAL)) {
            if (authenticationService.authenticate(email, token)) {
                userService.cancelPlayList(token);
                return "canceled successfully";
            } else {
                return "Failed because invalid authentication";
            }
        }
        return "song not canceled.";
    }
    @PutMapping("updatePlayList/{id}")
    public String updatePlayList(@PathVariable Long id, @RequestBody PlayList playList, @RequestParam String email, @RequestParam String token) {
        if (userRepo.findFirstByUserEmail(email).getUserRole().equals(Role.NORMAL)) {
            if (authenticationService.authenticate(email, token)) {
                userService.updatePlayList(token,id,playList);
                return "updated PlayList successfully";
            } else {
                return "Failed because invalid authentication";
            }
        }
        return "Playlist not possible to update.";
    }
    @GetMapping("/Pagenation")
    public String getPlayList(@RequestParam String email , @RequestParam String token , @RequestParam(defaultValue = "10") int limit,
                                  @RequestParam(defaultValue = "0") int offset) {
        if (userRepo.findFirstByUserEmail(email).getUserRole().equals(Role.NORMAL)) {
            if (authenticationService.authenticate(email, token)) {
                userService.getPlayList(email,token,limit,offset);
                return "get PlayList successfully";
            } else {
                return "Failed because invalid authentication";
            }
        }
        return "Impossible to fetching Playlist details.";
    }

    }

