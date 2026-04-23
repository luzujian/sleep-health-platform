package com.sleephealth.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sleephealth.auth.dto.LoginRequest;
import com.sleephealth.auth.dto.LoginResponse;
import com.sleephealth.auth.dto.RegisterRequest;
import com.sleephealth.common.exception.BusinessException;
import com.sleephealth.common.Code;
import com.sleephealth.common.utils.JwtUtils;
import com.sleephealth.common.utils.CustomPasswordEncoder;
import com.sleephealth.user.entity.User;
import com.sleephealth.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final CustomPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getUsername, request.getUsername())
        );

        if (user == null) {
            throw new BusinessException(Code.AUTH_FAILED, "用户名或密码错误");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(Code.AUTH_FAILED, "用户名或密码错误");
        }

        String token = jwtUtils.generateToken(user.getId(), user.getUsername(), user.getRole());

        return LoginResponse.builder()
                .token(token)
                .expiresIn(jwtUtils.getExpiration() / 1000)
                .user(LoginResponse.UserInfo.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .role(user.getRole())
                        .nickname(user.getNickname())
                        .build())
                .build();
    }

    @Override
    public void register(RegisterRequest request) {
        User existUser = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getUsername, request.getUsername())
        );

        if (existUser != null) {
            throw new BusinessException(Code.USERNAME_EXISTS, "用户名已存在");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname(request.getNickname());
        user.setPhone(request.getPhone());
        user.setRole("user");

        userMapper.insert(user);
    }
}
