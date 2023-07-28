package com.example.Music.Streaming.API.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PlayList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistId;
    @NotBlank(message = "name can't be blank")
    private String playListName;
    @OneToMany
    @JoinColumn(name = "fk_song")
    private List<Song> songs;
    @OneToOne
    @JoinColumn(name = "fk_user")
    private  User user;


}
