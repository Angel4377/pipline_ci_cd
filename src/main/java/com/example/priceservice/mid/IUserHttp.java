package com.example.priceservice.mid;

import com.example.priceservice.dto.UserDTO;

public interface IUserHttp {

    UserDTO getUserById(Long userId);
}
