package android.com.category.model;

import java.util.List;

public class CategoryParentBean {
    String code;
    String msg;
    List<CategoryItemBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<CategoryItemBean> getData() {
        return data;
    }

    public void setData(List<CategoryItemBean> data) {
        this.data = data;
    }
}
