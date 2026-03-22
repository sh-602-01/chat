package com.smhrd.chat.service;

import com.smhrd.chat.domain.User;
import com.smhrd.chat.dto.CreateUserRequest;
import com.smhrd.chat.dto.LoginRequest;
import com.smhrd.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository repository;

    //회원가입
    public Long signup(CreateUserRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        return repository.save(user).getId(); //값 추가 후 id 값만 리턴
    }

    // 로그인
    public User login(LoginRequest request){
        User user = repository.findByUsername(request.getUsername())
                .orElseThrow(()-> new IllegalArgumentException("해당 사용자 없음"));
        if(!user.getPassword().equals(request.getPassword())){
            throw new IllegalArgumentException("비밀번호 오류");
        }
        return user;
    }
}
