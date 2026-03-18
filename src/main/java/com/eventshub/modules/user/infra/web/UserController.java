package com.eventshub.modules.user.infra.web;

import com.eventshub.modules.user.core.model.User;
import com.eventshub.modules.user.core.model.input.CreateUserInput;
import com.eventshub.modules.user.core.usecase.CreateUserUseCase;
import com.eventshub.modules.user.infra.web.dto.UserResponse;
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
    private final UserDtoMapper dtoMapper;

    @PostMapping
    public ResponseEntity<UserResponse> register(@RequestBody CreateUserInput input) {
        User user = createUseCase.execute(input);
        UserResponse response = dtoMapper.toResponse(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
