package com.eventshub.modules.user.core.port;

import com.eventshub.modules.user.core.model.User;

public interface AuthPort {

    User authenticate(String email, String password);
}
