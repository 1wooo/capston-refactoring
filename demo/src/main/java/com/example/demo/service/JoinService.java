package com.example.demo.service;

import com.example.demo.DTO.UserRegistrationDto;
import com.example.demo.Entity.Member;

public interface JoinService {

    public boolean join(UserRegistrationDto userRegistrationDto);

}
