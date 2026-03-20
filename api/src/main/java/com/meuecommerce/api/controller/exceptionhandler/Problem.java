package com.meuecommerce.api.controller.exceptionhandler;

import java.time.LocalDateTime;

public class Problem {

    private Integer status;
    private LocalDateTime timestamp;
    private String title;

    public Problem(Integer status, LocalDateTime timestamp, String title) {
        this.status = status;
        this.timestamp = timestamp;
        this.title = title;
    }

    public Integer getStatus() { 
        return status;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getTitle() {
        return title;
    }
}
