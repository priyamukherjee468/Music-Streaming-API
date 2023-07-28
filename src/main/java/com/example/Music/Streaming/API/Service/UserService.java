package com.example.Music.Streaming.API.Service;

import com.example.Music.Streaming.API.Model.AuthenticationToken;
import com.example.Music.Streaming.API.Model.Dto.SignInInput;
import com.example.Music.Streaming.API.Model.Dto.SignUpOutput;
import com.example.Music.Streaming.API.Model.PlayList;
import com.example.Music.Streaming.API.Model.Song;
import com.example.Music.Streaming.API.Model.User;
import com.example.Music.Streaming.API.Repository.IAuthTokenRepo;
import com.example.Music.Streaming.API.Repository.IPlaylistRepo;
import com.example.Music.Streaming.API.Repository.ISongRepo;
import com.example.Music.Streaming.API.Repository.IUserRepo;
import com.example.Music.Streaming.API.Service.EmailUtility.EmailHandler;
import com.example.Music.Streaming.API.Service.HashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    IUserRepo userRepo;
    @Autowired
    IAuthTokenRepo authTokenRepo;
    @Autowired
    SongService songService;
    @Autowired
    ISongRepo songRepo;
    @Autowired
    PlayListService playListService;
    @Autowired
    IPlaylistRepo playlistRepo;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    IAuthTokenRepo authenticationRepo;

    public SignUpOutput signUpUser(User user) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();

        if (newEmail == null) {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }


        User existingUser = userRepo.findFirstByUserEmail(newEmail);

        if (existingUser != null) {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getPassword());


            user.setPassword(encryptedPassword);
            userRepo.save(user);

            return new SignUpOutput(signUpStatus, "User registered successfully!!!");
        } catch (Exception e) {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }
    }

    public String signInUser(SignInInput signInInput) {
        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if (signInEmail == null) {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }


        User existingUser = userRepo.findFirstByUserEmail(signInEmail);

        if (existingUser == null) {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if (existingUser.getPassword().equals(encryptedPassword)) {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken = new AuthenticationToken(existingUser);
                authTokenRepo.save(authToken);

                EmailHandler.sendEmail(signInEmail, "email testing", authToken.getTokenValue());
                return "Token sent to your email";
            } else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        } catch (Exception e) {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }

    }

    public String sigOutPatient(String email) {
        User user = userRepo.findFirstByUserEmail(email);
        authTokenRepo.delete(authTokenRepo.findFirstByUser(user));
        return "User Signed out successfully";
    }

    public boolean addSong(Song song) {
        Long userId = song.getUser().getUserId();
        ;
        boolean isUserValid = userRepo.existsById(userId);


        Long songId = song.getSongId();
        boolean isSongValid = songRepo.existsById(songId);

        if (isUserValid && isSongValid) {
            songService.saveSong(song);
            return true;
        } else {
            return false;
        }

    }

    public void cancelSong(String email) {
        User user = userRepo.findFirstByUserEmail(email);

        Song song = songService.getSong(user);

        songService.cancelsong(song);
    }
    public void updateSong(String token, Long id, Song song) {
        Long userId = song.getUser().getUserId();
        boolean isUserValid = userRepo.existsById(userId);

        Long songId = song.getSongId();
        boolean isSongValid = songRepo.existsById(songId);
        if (isUserValid && isSongValid) {
            Optional<Song> optionalSong = songRepo.findById(id);
            if (optionalSong.isPresent()) {
                Song originalSong = optionalSong.get();
                if (song.getSongTitle() != null) {
                    originalSong.setSongTitle(song.getSongTitle());
                }
                if(song.getArtist()!=null){
                    originalSong.setArtist(song.getArtist());
                }
            }
        }
    }
    public boolean addPlayList(PlayList playList) {
        Long userId = playList.getUser().getUserId();
        ;
        boolean isUserValid = userRepo.existsById(userId);


        Long playListId = playList.getPlaylistId();
        boolean isPlayListValid = playlistRepo.existsById(playListId);

        if (isUserValid && isPlayListValid) {
            playListService.save(playList);
            return true;
        } else {
            return false;
        }
    }


    public void cancelPlayList(String token) {
        User user = userRepo.findFirstByUserEmail(token);

        PlayList playList = playListService.getPlayList(user);

        playListService.cancelPlayList(playList);
    }


    public void updatePlayList(String token, Long id, PlayList playList) {
        Long userId = playList.getUser().getUserId();
        boolean isUserValid = userRepo.existsById(userId);

        Long playListId = playList.getPlaylistId();
        boolean isPlayListValid = playlistRepo.existsById(playListId);
        if (isUserValid && isPlayListValid) {
            Optional<PlayList> optionalPlayList=playlistRepo.findById(id);
            if (optionalPlayList.isPresent()) {
                PlayList originalPlayList = optionalPlayList.get();
                if (playList.getPlayListName()!=null) {
                    originalPlayList.setPlayListName(playList.getPlayListName());
                }

            }
        }
    }

    public void getPlayList(String email, String token, int limit, int offset) {
        AuthenticationToken token1 = authenticationService.authenticationRepo.findFirstByTokenValue(token);
        User user = token1.getUser();
        PlayList playList = user.getPlayList();
        List<Song> songList = playList.getSongs().subList(offset, Math.min(offset + limit, playList.getSongs().size()));

    }
}
