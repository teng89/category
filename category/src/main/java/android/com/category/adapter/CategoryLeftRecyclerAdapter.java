package android.com.category.adapter;

import android.com.category.R;
import android.com.category.util.MyLog;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/*
 * Created by thh on 2019/8/12.
 */
public class CategoryLeftRecyclerAdapter extends RecyclerView.Adapter<CategoryLeftRecyclerAdapter.ViewHolder>
        implements View.OnClickListener {

    private static final String TAG = "CategoryLeftRecycleAdapter";
    private List<String> list;
    private Context cn;
    private CategoryLeftTitleClickListener listener;
    private int currentPosition = 0;
    private Resources res;
    private LayoutInflater layoutInflater;

    public CategoryLeftRecyclerAdapter(Context context, List<String> list) {
        this.list = list;
        this.cn = context;
        res = cn.getResources();
        layoutInflater = LayoutInflater.from(cn);
    }

    public void setCategoryLeftTitleClickListener(CategoryLeftTitleClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setCurrentPosition(int position) {
        this.currentPosition = position;
    }

    @Override
    public void onClick(View view) {
        if(view == null) {
            return;
        }
        int position = (int) view.getTag(R.id.categoryViewHolder);
        MyLog.d(TAG, "onclick " + position);
        currentPosition = position;
        if (listener != null) {
            listener.leftTitleClick(position);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        String subTitle = list.get(i);
        viewHolder.text.setText(subTitle);
        viewHolder.text.setTag(R.id.categoryViewHolder, i);
        if (currentPosition == i) {
            viewHolder.text.setBackgroundColor(res.getColor(R.color.category_main_bg));
            viewHolder.text.setTextColor(res.getColor(R.color.category_main_title_select));
            viewHolder.line.setVisibility(View.VISIBLE);
        } else {
            viewHolder.text.setBackgroundColor(res.getColor(R.color.white_color));
            viewHolder.text.setTextColor(res.getColor(R.color.category_main_title_unselect));
            viewHolder.line.setVisibility(View.GONE);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.category_title_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private View line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.category_item_text);
            line = (View) itemView.findViewById(R.id.cate_lf_line);
            text.setOnClickListener(CategoryLeftRecyclerAdapter.this);
        }
    }

    public interface CategoryLeftTitleClickListener {
        void leftTitleClick(int position);
    }

}
