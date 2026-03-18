package com.eventshub.modules.user.core.port;

import com.eventshub.modules.user.core.model.User;

public interface UserPort {

    User create(User user);

    boolean emailExists(String email);
}
