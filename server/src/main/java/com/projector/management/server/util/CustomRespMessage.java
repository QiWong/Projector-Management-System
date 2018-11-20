package com.projector.management.server.util;

import com.projector.management.server.model.Duration;

import java.util.List;

public class CustomRespMessage {
    public static String availableTimesMessage(List<Duration> durationList) {
        StringBuilder sb = new StringBuilder();
        sb.append("Available times on same day and next days are: [");
        for (Duration duration: durationList) {
            sb.append("{\n");
            sb.append(duration.toString());
            sb.append("\n}\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
