package com.example.Music.Streaming.API.Service;

import com.example.Music.Streaming.API.Model.PlayList;
import com.example.Music.Streaming.API.Model.Song;
import com.example.Music.Streaming.API.Model.User;
import com.example.Music.Streaming.API.Repository.IPlaylistRepo;
import com.example.Music.Streaming.API.Repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PlayListService {
    @Autowired
    IPlaylistRepo playlistRepo;
    @Autowired
    IUserRepo userRepo;

    public void save(PlayList playList) {
        playlistRepo.save(playList);
    }



    public void cancelPlayList(PlayList playList) {
        playlistRepo.delete(playList);
    }

    public PlayList getPlayList(User user) {
        return playlistRepo.findFirstByUser(user);
    }

    public List<PlayList> getAllPlayList() {
        return playlistRepo.findAll();
    }



}
