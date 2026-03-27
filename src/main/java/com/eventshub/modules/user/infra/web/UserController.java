package com.eventshub.modules.user.infra.web;

import com.eventshub.modules.user.core.model.User;
import com.eventshub.modules.user.core.usecase.CreateUserUseCase;
import com.eventshub.modules.user.core.usecase.LoginUseCase;
import com.eventshub.modules.user.infra.web.dto.CreateUserRequest;
import com.eventshub.modules.user.infra.web.dto.LoginRequest;
import com.eventshub.modules.user.infra.web.dto.LoginResponse;
import com.eventshub.modules.user.infra.web.dto.UserResponse;
import com.eventshub.modules.user.infra.web.mapper.LoginWebMapper;
import com.eventshub.modules.user.infra.web.mapper.UserWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/users")
public class UserController {

    private final CreateUserUseCase createUseCase;
    private final LoginUseCase loginUseCase;

    private final UserWebMapper userMapper;
    private final LoginWebMapper loginMapper;

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> register(
            @RequestBody @Valid CreateUserRequest request) {
        User user = createUseCase.execute(userMapper.toCreateInput(request));
        UserResponse response = userMapper.toResponse(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request
    ) {
        String token = loginUseCase.execute(loginMapper.toInput(request));
        return ResponseEntity.ok(loginMapper.toResponse(token));
    }
}
