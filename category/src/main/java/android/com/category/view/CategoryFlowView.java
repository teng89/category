package android.com.category.view;

import android.com.category.R;
import android.com.category.adapter.CategoryRecycleAdapter;
import android.com.category.model.CategorySecondItemBean;
import android.com.category.model.CategoryThirdItemBean;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


/*
 * Created by thh on 2019/8/27.
 */
public class CategoryFlowView extends LinearLayout {

    private static final String TAG = "CategoryFlowView";
    private Context context;
    private RecyclerView boxLayout;
    private TextView title;
    private CategorySecondItemBean data;

    public CategoryFlowView(Context context) {
        this(context, null);
    }

    public CategoryFlowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CategoryFlowView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CategoryFlowView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context cn) {
        context = cn;
        LayoutInflater inflater = LayoutInflater.from(cn);
        View rootView = inflater.inflate(R.layout.category_right_item, this);
        title = (TextView) rootView.findViewById(R.id.right_item_title);
        boxLayout = (RecyclerView) rootView.findViewById(R.id.right_box);
    }

    public void setData(CategorySecondItemBean bean) {
        this.data = bean;
        title.setText(bean.getName());
        List<CategoryThirdItemBean> children = bean.getChildren();
        if (children != null && children.size() != 0) {
            CategoryRecycleAdapter adapter = new CategoryRecycleAdapter(context, children);
            GridLayoutManager layoutManager = new GridLayoutManager(context, 3) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };

            boxLayout.setLayoutManager(layoutManager);
            boxLayout.setAdapter(adapter);
        } else {
            this.setVisibility(View.GONE);
        }
    }

}
