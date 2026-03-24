package com.example.priceservice.mid;


import com.example.priceservice.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
    @RequiredArgsConstructor
    public class UserHttp implements IUserHttp {

    private final RestTemplate restTemplate;

    private final String USER_SERVICE_URL = "http://localhost:8082/users";

    @Override
    public UserDTO getUserById(Long userId) {
        try {
            return restTemplate.getForObject(USER_SERVICE_URL + "/" + userId, UserDTO.class);
        } catch (Exception e) {
            return null;
        }

    }
}
