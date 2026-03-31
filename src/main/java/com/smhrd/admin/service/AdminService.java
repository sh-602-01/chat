package com.smhrd.admin.service;

import com.smhrd.chat.dto.CreateUserRequest;
import com.smhrd.chat.repository.UserRepository;
import com.smhrd.common.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final UserRepository userRepository;

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public void saveUser(CreateUserRequest req){
        User user = new User();
        // 폼에서 입력한 값들을 하나씩 세팅함
        user.setUserId(req.getUserId());
        user.setPassword(req.getPassword());
        user.setUserName(req.getUserName());
        user.setWorkStartTime(req.getWorkStartTime());
        user.setWorkEndTime(req.getWorkEndTime());

        // 실제 DB에 INSERT 실행
        userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
