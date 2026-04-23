package com.sleephealth.auth.service;

import com.sleephealth.auth.dto.LoginRequest;
import com.sleephealth.auth.dto.LoginResponse;
import com.sleephealth.auth.dto.RegisterRequest;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    void register(RegisterRequest request);
}
