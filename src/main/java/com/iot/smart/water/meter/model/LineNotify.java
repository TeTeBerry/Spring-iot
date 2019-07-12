package com.iot.smart.water.meter.model;

import com.iot.smart.water.meter.util.ConnectionUtil;
import sun.rmi.transport.Connection;

import java.io.IOException;
public class LineNotify {
    private String userToken;
    private LineParameter lineParameter;

    public LineNotify(String userToken) {
        this.userToken = userToken;
    }

    public LineNotify(String userToken, LineParameter lineParameter) {
        this.userToken = userToken;
        this.lineParameter = lineParameter;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public boolean notifyMe(String message) throws IOException{
        return notifyMe(message, 0, 0);
    }

    public boolean notifyMe(String message,int stickerPackageId,int stickerId) throws IOException{
        if(this.lineParameter == null)
            this.lineParameter = new LineParameter(message, stickerPackageId, stickerId);
        int resStatus =  ConnectionUtil.sendData(this.lineParameter,this.userToken);
        return resStatus==200;
    }
}
