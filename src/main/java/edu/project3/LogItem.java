package edu.project3;

import java.time.OffsetDateTime;

public record LogItem(
    String remoteAddr,
    String remoteUser,
    OffsetDateTime timeLocal,
    RequestItem request,
    Integer status,
    Long bodyBytesSent,
    String httpReferer,
    String httpUserAgent
) {
}

