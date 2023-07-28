package com.example.Music.Streaming.API.Controller;

import com.example.Music.Streaming.API.Model.PlayList;
import com.example.Music.Streaming.API.Model.Role;
import com.example.Music.Streaming.API.Model.Song;
import com.example.Music.Streaming.API.Repository.IPlaylistRepo;
import com.example.Music.Streaming.API.Repository.IUserRepo;
import com.example.Music.Streaming.API.Service.AuthenticationService;
import com.example.Music.Streaming.API.Service.PlayListService;
import com.example.Music.Streaming.API.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayListController {

    @Autowired
    PlayListService playListService;
    @GetMapping("playList")
    public List<PlayList> getAllPlayList() {
        return playListService.getAllPlayList();
    }


}
