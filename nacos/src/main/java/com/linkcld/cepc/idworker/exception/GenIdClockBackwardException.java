package com.linkcld.cepc.idworker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * rest exception for rest api
 * this will add a special errorHandler
 */
@Getter
public class GenIdClockBackwardException extends ResponseStatusException {

    private static final long serialVersionUID = 1L;

    /**
     * backward 毫秒数
     */
    private long backTimes;

    public GenIdClockBackwardException(Long backTimes) {
        super(HttpStatus.SERVICE_UNAVAILABLE, String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",backTimes));
        this.backTimes = backTimes;
    }
}
