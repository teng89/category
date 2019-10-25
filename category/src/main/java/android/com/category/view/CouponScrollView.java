package android.com.category.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;


/*
 * Created by thh on 2019/10/16.
 */
public class CouponScrollView extends ScrollView {

    private OverScrollListener overScrollListener;

    private int count = 0;

    private boolean top,bottom,overScrolled;

    public CouponScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOverScrollListener(OverScrollListener listener) {
        this.overScrollListener = listener;
    }

    public void init() {
        count = 0;
        top = false;
        bottom = false;
        overScrolled = false;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if(overScrollListener == null) {
            return;
        }
        if(clampedY) {
            count++;
        } else {
            count=0;
        }
        if(count == 6) {
            overScrolled = true;
            if(scrollY == 0) {
                //滑动到了顶部
                top = true;
                bottom = false;
            } else {
                //滑动到了底部
                top = false;
                bottom = true;
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (overScrollListener != null && overScrolled) {
                    overScrolled = false;
                    overScrollListener.overScrolled(top,bottom);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    public interface OverScrollListener {
        void overScrolled(boolean top, boolean bottom);
    }
}
