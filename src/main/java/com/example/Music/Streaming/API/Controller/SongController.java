package com.example.Music.Streaming.API.Controller;

import com.example.Music.Streaming.API.Model.PlayList;
import com.example.Music.Streaming.API.Model.Role;
import com.example.Music.Streaming.API.Model.Song;
import com.example.Music.Streaming.API.Model.User;
import com.example.Music.Streaming.API.Repository.ISongRepo;
import com.example.Music.Streaming.API.Repository.IUserRepo;
import com.example.Music.Streaming.API.Service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SongController {
    @Autowired
    SongService songService;

    @GetMapping("songs")
    public List<Song> getAllSongs() {
        return songService.getAllsongs();
    }

}
