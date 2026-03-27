package com.eventshub.modules.user.core.application.port;

import com.eventshub.modules.user.core.domain.model.User;

public interface UserPort {

    User create(User user);

    boolean emailExists(String email);
}
