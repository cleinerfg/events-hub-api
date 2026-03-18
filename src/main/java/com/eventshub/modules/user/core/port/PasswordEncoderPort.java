package com.eventshub.modules.user.core.port;

public interface PasswordEncoderPort {

    String encode(String rawPassword);
}
