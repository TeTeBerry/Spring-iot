package com.iot.smart.water.meter.util.line;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LineNotify {

    private static final String USERTOKEN = "pCtUdqqKEqGzZEVJDRUWJIs3ZZgCdo3joYcH9FeTLQ2";

    public boolean notifyMe(String message) {
        return notifyMe(message, 0, 0);
    }

    public boolean notifyMe(String message, int stickerPackageId, int stickerId) {
        try {
            int resStatus = ConnectionUtil.sendData(new LineParameter(message, stickerPackageId, stickerId), USERTOKEN);
            return resStatus == 200;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
