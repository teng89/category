package android.com.category.activity;

import android.com.category.R;
import android.com.category.adapter.CategoryLeftRecyclerAdapter;
import android.com.category.model.CategoryItemBean;
import android.com.category.model.CategorySecondItemBean;
import android.com.category.util.MyLog;
import android.com.category.view.CategoryFlowView;
import android.com.category.view.CouponScrollView;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


/*
 * Created by thh on 2019/10/25.
 */
public class MainActivity extends AppCompatActivity implements BaseView,
        CategoryLeftRecyclerAdapter.CategoryLeftTitleClickListener{

    private static final String TAG = "MainActivity";
    private CompositeDisposable compositeDisposable;
    private RecyclerView titleRc;
    private CategoryLeftRecyclerAdapter categoryLeftRecyclerAdapter;
    private LinearLayout right_container;
    private List<CategoryItemBean> dataList;
    private CouponScrollView right_scroll;
    private int position;
    private MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLog.d(TAG,"onCreate");
        initStatusBar();
        setContentView(R.layout.main_layout);
        initUi();
        initPresenter();
    }

    private void initStatusBar() {
        try {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.WHITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDarkStatusIcon(true);
    }

    private void setDarkStatusIcon(boolean isDark) {
        try {
            View decorView = getWindow().getDecorView();
            int flag = decorView.getSystemUiVisibility();
            if (isDark) {
                flag |= 8192; //View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                flag &= ~8192;//View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            decorView.setSystemUiVisibility(flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUi() {
        MyLog.d(TAG,"initUi");
        titleRc = (RecyclerView) findViewById(R.id.left_rc);
        right_container = (LinearLayout) findViewById(R.id.right_container);
        right_scroll = (CouponScrollView) findViewById(R.id.right_page);
        right_scroll.setOverScrollListener(new CouponScrollView.OverScrollListener() {
            @Override
            public void overScrolled(boolean top, boolean bottom) {
                if(dataList == null) {
                    return;
                }
                if(top && position > 0) {
                    leftTitleClick(position-1);
                } else if(bottom && position < (dataList.size()-1)) {
                    leftTitleClick(position+1);
                } else if((top && position == 0) || (bottom && position==(dataList.size()-1))) {
                    right_scroll.init();
                }
            }
        });
    }

    private void initPresenter() {
        MyLog.d(TAG,"initpresenter");
        compositeDisposable = new CompositeDisposable();
        presenter = new MainPresenter(getApplicationContext(),this,compositeDisposable);
        presenter.getCateData();
    }

    private void initRightContent(List<CategorySecondItemBean> list) {
        for (int i = 0; i < list.size(); i++) {
            CategoryFlowView view = new CategoryFlowView(this);
            view.setData(list.get(i));
            right_container.addView(view);
        }
        right_scroll.scrollTo(0,0);
        right_scroll.init();
    }

    @Override
    public void leftTitleClick(int position) {
        MyLog.d(TAG, "ItemClick position:" + position);
        this.position = position;
        categoryLeftRecyclerAdapter.setCurrentPosition(position);
        categoryLeftRecyclerAdapter.notifyDataSetChanged();
        right_container.removeAllViews();
        initRightContent(dataList.get(position).getChildren());
    }

    @Override
    public void getData(ArrayList<CategoryItemBean> list) {
        if(list==null || (list!=null && list.size()==0)) {
            return;
        }
        dataList = list;
        List<String> leftTitleList = new ArrayList<>();
        for (CategoryItemBean categoryItemBean : dataList) {
            leftTitleList.add(categoryItemBean.getName());
        }
        right_container.removeAllViews();
        categoryLeftRecyclerAdapter = new CategoryLeftRecyclerAdapter(this, leftTitleList);
        categoryLeftRecyclerAdapter.setCategoryLeftTitleClickListener(this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                LinearLayout.VERTICAL);
        titleRc.setLayoutManager(layoutManager);
        titleRc.setAdapter(categoryLeftRecyclerAdapter);
        categoryLeftRecyclerAdapter.notifyDataSetChanged();

        initRightContent(dataList.get(0).getChildren());
    }

    @Override
    public void getDataFail(String msg) {
        Toast.makeText(this,"没有获取到有效数据",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        presenter = null;
        super.onDestroy();
    }
}
