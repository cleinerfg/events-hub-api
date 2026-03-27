package com.eventshub.modules.user.core.application.port;

public interface PasswordEncoderPort {

    String encode(String rawPassword);
}
