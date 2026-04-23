package com.sleephealth.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sleephealth.user.entity.User;

public interface UserService {
    Page<User> getUserPage(String role, int page, int size);
    User getUserById(Long id);
}
