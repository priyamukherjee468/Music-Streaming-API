package com.example.Music.Streaming.API.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotBlank(message = "email cannot be blank")
    private String userEmail;
    @NotBlank(message = "password cannot be blank")
    private String Password;
    @Enumerated(EnumType.STRING)
    private Role userRole;


    public PlayList getPlayList() {
        return null;
    }
}
