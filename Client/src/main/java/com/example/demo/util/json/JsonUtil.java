package com.example.demo.util.json;

/**
 * Created by Administrator on 2017-06-13.
 */
public class JsonUtil {
    private Object data; //请求参数
    private Info info; //公共参数
    private ExtLimit extlimit; //分页数据
    private Object other; //其他参数

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public ExtLimit getExtlimit() {
        return extlimit;
    }

    public void setExtlimit(ExtLimit extlimit) {
        this.extlimit = extlimit;
    }

    public Object getOther() {
        return other;
    }

    public void setOther(Object other) {
        this.other = other;
    }
}
