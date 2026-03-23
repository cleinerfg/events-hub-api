package com.eventshub.shared.core.security;

public interface TokenPort {

    String generate(String subject);
}
