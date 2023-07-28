package com.example.Music.Streaming.API.Repository;

import com.example.Music.Streaming.API.Model.AuthenticationToken;
import com.example.Music.Streaming.API.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthTokenRepo extends JpaRepository<AuthenticationToken,Long> {
    AuthenticationToken findFirstByUser(User user);



    AuthenticationToken findFirstByTokenValue(String authTokenValue);


}
