package android.com.category.activity;

import android.com.category.model.CategoryItemBean;
import android.com.category.model.CategoryParentBean;
import android.com.category.util.MyLog;
import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/*
 * Created by thh on 2019/10/25.
 */
public class MainPresenter {

    private static final String TAG = "MainPresenter";

    private BaseView baseView;

    private Context cn;

    private CompositeDisposable compositeDisposable;

    public MainPresenter(Context context,BaseView view,CompositeDisposable com) {
        this.baseView = view;
        this.cn = context;
        this.compositeDisposable = com;
    }

    public void getCateData() {
        MyLog.d(TAG,"getCateData");
        Observable<ArrayList<CategoryItemBean>> observable = Observable.create(new ObservableOnSubscribe<ArrayList<CategoryItemBean>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<CategoryItemBean>> emitter) throws Exception {
                String asset = getAssetData();
                MyLog.d(TAG,"asset:" + asset);
                if(!TextUtils.isEmpty(asset)) {
                    CategoryParentBean parentBean = JSON.parseObject(asset,CategoryParentBean.class);
                    if(parentBean.getData()!=null) {
                        emitter.onNext((ArrayList<CategoryItemBean>) parentBean.getData());
                    } else {
                        emitter.onError(new Throwable("not get data list"));
                    }
                } else {
                    emitter.onError(new Throwable("get null"));
                }

            }
        });
        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<CategoryItemBean>>() {
                    @Override
                    public void accept(ArrayList<CategoryItemBean> list) throws Exception {
                        MyLog.d(TAG,"receiver data:");
                        baseView.getData(list);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        MyLog.d(TAG, "exception accept " + throwable.getLocalizedMessage());
                        baseView.getDataFail(throwable.getMessage());
                    }
                });
        if (compositeDisposable != null) {
            compositeDisposable.add(disposable);
        }
    }

    public String getAssetData() {
        InputStream inputStream=null;
        try {
            inputStream = cn.getAssets().open("category");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            String s = "";
            while ((s = reader.readLine()) != null) {
                sb.append(s);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(inputStream!=null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
