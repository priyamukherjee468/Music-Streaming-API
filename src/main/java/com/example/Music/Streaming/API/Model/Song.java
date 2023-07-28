package com.example.Music.Streaming.API.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;
    @NotBlank(message ="title can't be blank")
    private String songTitle;
    @NotBlank(message ="artist can't be blank")
    private String artist;
@OneToOne
@JoinColumn(name = "fk_user")
    private User user;


}
