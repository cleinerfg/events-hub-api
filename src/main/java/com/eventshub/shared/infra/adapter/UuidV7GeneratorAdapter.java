package com.eventshub.shared.infra.adapter;

import com.eventshub.shared.core.port.UuidGeneratorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class UuidV7GeneratorAdapter implements UuidGeneratorPort {

    private final Supplier<UUID> uuidV7Generator;

    @Override
    public UUID generate() {
        return uuidV7Generator.get();
    }
}
