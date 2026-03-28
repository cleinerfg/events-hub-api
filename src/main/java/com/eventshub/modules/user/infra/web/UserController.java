package com.eventshub.modules.user.infra.web;

import com.eventshub.modules.user.core.application.usecase.CreateUserUseCase;
import com.eventshub.modules.user.core.domain.model.User;
import com.eventshub.modules.user.infra.web.dto.CreateUserRequest;
import com.eventshub.modules.user.infra.web.dto.UserResponse;
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
    private final UserWebMapper mapper;

    @PostMapping
    public ResponseEntity<UserResponse> create(
            @RequestBody @Valid CreateUserRequest request) {
        User user = createUseCase.execute(mapper.toCreateCommand(request));
        UserResponse response = mapper.toResponse(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
