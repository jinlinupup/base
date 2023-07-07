package com.gankao.common.kouyu;

import java.io.Serializable;

/**
 * Created by heqianqian on 2019/7/1.
 */

public class KouyuStartBean implements Serializable {

    public int userId;
    public Request request;
    public String onUpdateVolume;
    public String onBackVadTimeOut;
    public String onFrontVadTimeOut;
    public String onError;
    public String onResult;

    public class Request implements Serializable {
        public String refText;
        public int rank;
        public String coreType;
    }
}
