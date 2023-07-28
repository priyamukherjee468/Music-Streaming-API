package com.example.Music.Streaming.API.Service;

import com.example.Music.Streaming.API.Model.AuthenticationToken;
import com.example.Music.Streaming.API.Repository.IAuthTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    IAuthTokenRepo authenticationRepo;
    public boolean authenticate(String email, String authTokenValue)
    {
        AuthenticationToken authToken = authenticationRepo.findFirstByTokenValue(authTokenValue);

        if(authToken == null)
        {
            return false;
        }

        String tokenConnectedEmail = authToken.getUser().getUserEmail();

        return tokenConnectedEmail.equals(email);
    }
    }

