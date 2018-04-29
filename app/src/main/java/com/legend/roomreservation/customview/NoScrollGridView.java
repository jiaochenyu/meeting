package com.legend.roomreservation.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by JCY on 2017/6/15.
 * 说明：
 */

public class NoScrollGridView extends GridView {
    public NoScrollGridView(Context context) {
        super(context);
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        // TODO Auto-generated method stub
//        if(ev.getAction() == MotionEvent.ACTION_MOVE){
//            return true;//禁止Gridview进行滑动
//        }
//        return super.dispatchTouchEvent(ev);
//    }
}
