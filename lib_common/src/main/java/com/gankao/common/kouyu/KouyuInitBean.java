package com.gankao.common.kouyu;

import java.io.Serializable;

/**
 * Created by heqianqian on 2019/7/1.
 */

public class KouyuInitBean implements Serializable {

    public Params params;
    public String onSuccess;
    public String onError;

    public class Params implements Serializable {
        public String serverAPI;
        public String testServerAPI;
        public boolean enableVolume;
        public int  serverTimeout;
        public boolean openVad;
        public long frontVadTime;
        public long backVadTime;

        public String getServerAPI() {
            return serverAPI;
        }

        public void setServerAPI(String serverAPI) {
            this.serverAPI = serverAPI;
        }

        public String getTestServerAPI() {
            return testServerAPI;
        }

        public void setTestServerAPI(String testServerAPI) {
            this.testServerAPI = testServerAPI;
        }

        public boolean isEnableVolume() {
            return enableVolume;
        }

        public void setEnableVolume(boolean enableVolume) {
            this.enableVolume = enableVolume;
        }

        public int getServerTimeout() {
            return serverTimeout;
        }

        public void setServerTimeout(int serverTimeout) {
            this.serverTimeout = serverTimeout;
        }

        public boolean isOpenVad() {
            return openVad;
        }

        public void setOpenVad(boolean openVad) {
            this.openVad = openVad;
        }

        public long getFrontVadTime() {
            return frontVadTime;
        }

        public void setFrontVadTime(long frontVadTime) {
            this.frontVadTime = frontVadTime;
        }

        public long getBackVadTime() {
            return backVadTime;
        }

        public void setBackVadTime(long backVadTime) {
            this.backVadTime = backVadTime;
        }
    }
}
