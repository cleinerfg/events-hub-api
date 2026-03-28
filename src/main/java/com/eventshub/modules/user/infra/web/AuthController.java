package com.eventshub.modules.user.infra.web;

import com.eventshub.modules.user.core.application.usecase.LoginUseCase;
import com.eventshub.modules.user.infra.web.dto.LoginRequest;
import com.eventshub.modules.user.infra.web.dto.LoginResponse;
import com.eventshub.modules.user.infra.web.mapper.LoginWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final LoginWebMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request
    ) {
        String token = loginUseCase.execute(mapper.toCommand(request));
        return ResponseEntity.ok(mapper.toResponse(token));
    }
}
