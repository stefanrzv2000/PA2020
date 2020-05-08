package com.company.rest;


import com.company.game.Player;
import net.minidev.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.ServerRequest;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class Services {

    final String uri = "https://192.168.100.6:8642/";

    static {
        try {
            SSLUtil.turnOffSslChecking();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public RestPlayer getById(int id){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RestPlayer> response = restTemplate.exchange(
                uri + "/players/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                }
        );
        System.out.println(response);
        return response.getBody();
    }

    public Boolean existsGameId(String gid){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Boolean> response = restTemplate.exchange(
                uri + "/games/exists_id/" + gid, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    public Integer existsPlayer(String name){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Integer> response = restTemplate.exchange(
                uri + "/players/exists/" + name,HttpMethod.GET,null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    public Integer postPlayer(String username){
        int id = existsPlayer(username);
        if(id > 0) return id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RestPlayer> response = restTemplate.exchange(
                uri + "/players/add/" + username,HttpMethod.POST,null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody().getId();
    }

    public void postGame(RestGame game){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var gameJSON = game.toJSON();

        HttpEntity<String> entity = new HttpEntity<>(gameJSON.toJSONString(),headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri + "/games",HttpMethod.POST,entity,
                new ParameterizedTypeReference<>() {}
        );
        if(response.getStatusCode().equals(HttpStatus.NOT_FOUND)){
            System.err.println(response.getBody());
        }
        else{
            System.out.println(response.getBody());
        }
    }
}
