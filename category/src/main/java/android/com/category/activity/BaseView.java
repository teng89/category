package android.com.category.activity;

import android.com.category.model.CategoryItemBean;

import java.util.ArrayList;

/*
 * Created by thh on 2019/10/25.
 */
public interface BaseView {
    void getData(ArrayList<CategoryItemBean> list);
    void getDataFail(String msg);
}
