package com.example.Music.Streaming.API.Service;

import com.example.Music.Streaming.API.Model.Song;
import com.example.Music.Streaming.API.Model.User;
import com.example.Music.Streaming.API.Repository.ISongRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {
    @Autowired
    ISongRepo songRepo;
    public void saveSong(Song song) {
songRepo.save(song);
    }

    public Song getSong(User user) {
        return songRepo.findFirstByUser(user);
    }

    public void cancelsong(Song song) {
        songRepo.delete(song);
    }



    public List<Song> getAllsongs() {
        return songRepo.findAll();
    }
}
