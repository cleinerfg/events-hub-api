package com.eventshub.modules.user.core.port;

import com.eventshub.modules.user.core.model.User;

public interface TokenPort {

    String generate(User user);
}
