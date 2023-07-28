package com.example.Music.Streaming.API.Repository;

import com.example.Music.Streaming.API.Model.PlayList;
import com.example.Music.Streaming.API.Model.Song;
import com.example.Music.Streaming.API.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlaylistRepo extends JpaRepository<PlayList,Long> {
    PlayList findFirstByUser(User user);
}
