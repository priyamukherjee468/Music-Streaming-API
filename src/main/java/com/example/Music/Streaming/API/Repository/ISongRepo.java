package com.example.Music.Streaming.API.Repository;

import com.example.Music.Streaming.API.Model.Song;
import com.example.Music.Streaming.API.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISongRepo extends JpaRepository<Song,Long> {
    Song findFirstByUser(User user);
}
