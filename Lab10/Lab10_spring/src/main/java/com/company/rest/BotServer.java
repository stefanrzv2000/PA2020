package com.company.rest;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class BotServer {

    final String uri = "https://192.168.100.6:8822/gomoku/";

    static {
        try {
            SSLUtil.turnOffSslChecking();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public BotServer(){
        System.out.println("created");
    }

    public Integer createGame(int size){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Integer> response = restTemplate.exchange(
                uri + "/new/" + size, HttpMethod.POST,null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    public RestPiece sendPiece(int id, RestPiece piece){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RestPiece> response = restTemplate.exchange(
                uri + "/" + id + "/move/" + piece.getX() + "/" + piece.getY(),
                HttpMethod.POST,null,
                new ParameterizedTypeReference<>() {}
        );

        return response.getBody();
    }

}
