package android.com.category.adapter;

import android.com.category.R;
import android.com.category.model.CategoryThirdItemBean;
import android.com.category.util.MyLog;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/*
 * Created by thh on 2019/8/12.
 */
public class CategoryRecycleAdapter extends RecyclerView.Adapter<CategoryRecycleAdapter.ViewHolder>
        implements View.OnClickListener {

    private static final String TAG = "CategoryRecycleAdapter";
    private List<CategoryThirdItemBean> list;
    private Context cn;
    private int type;
    private Resources res;
    private LayoutInflater layoutInflater;

    public CategoryRecycleAdapter(Context context, List<CategoryThirdItemBean> list) {
        this.list = list;
        this.cn = context;
        this.type = type;
        res = cn.getResources();
        layoutInflater = LayoutInflater.from(cn);
    }


    public void setData(List<CategoryThirdItemBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    @Override
    public void onClick(View view) {
        if(view == null) {
            return;
        }
        Object tag = view.getTag(R.id.categoryViewHolder);
        if(tag == null) {
            return;
        }
        int position = (int) tag;
        MyLog.d(TAG, "onclick " + position);
        String content = list.get(position).getSname() != null ?
                list.get(position).getSname() : list.get(position).getName();
        //do something after item click
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.text.setText(list.get(i).getName());
        viewHolder.text.setTag(R.id.categoryViewHolder, i);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        Glide.with(cn)
                .load(list.get(i).getLogo())
                .apply(options)
                .into(viewHolder.img);
        viewHolder.img.setTag(R.id.categoryViewHolder, i);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.category_flow_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView text;
        private View line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.category_item_img);
            text = (TextView) itemView.findViewById(R.id.category_item_text);
            img.setOnClickListener(CategoryRecycleAdapter.this);
            text.setOnClickListener(CategoryRecycleAdapter.this);
        }

    }
}
