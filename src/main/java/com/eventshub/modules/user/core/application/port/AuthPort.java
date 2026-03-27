package com.eventshub.modules.user.core.application.port;

import com.eventshub.modules.user.core.domain.model.User;

public interface AuthPort {

    User authenticate(String email, String password);
}
