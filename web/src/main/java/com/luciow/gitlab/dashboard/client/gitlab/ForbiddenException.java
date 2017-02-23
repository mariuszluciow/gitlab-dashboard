package com.luciow.gitlab.dashboard.client.gitlab;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(Throwable cause) {
        super(cause);
    }
}
