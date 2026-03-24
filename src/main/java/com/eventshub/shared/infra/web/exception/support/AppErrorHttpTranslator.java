package com.eventshub.shared.infra.web.exception.support;

import com.eventshub.shared.core.exception.AppError;
import com.eventshub.shared.core.exception.ErrorScope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AppErrorHttpTranslator {

    private final Map<ErrorScope, Map<AppError, HttpStatus>> schemaMapping;

    public AppErrorHttpTranslator(List<HttpErrorSchema> schemas) {
        schemaMapping = schemas.stream()
                .collect(Collectors.toMap(
                        HttpErrorSchema::scope,
                        HttpErrorSchema::definitions,
                        (existingMap, newMap) -> {
                            var combined = new HashMap<>(existingMap);
                            combined.putAll(newMap);
                            return combined;
                        }
                ));
    }

    public HttpStatus lookup(AppError appError) {
        var scopeMap = schemaMapping.get(appError.getScope());
        if (scopeMap == null) return null;

        return scopeMap.get(appError);
    }

}
