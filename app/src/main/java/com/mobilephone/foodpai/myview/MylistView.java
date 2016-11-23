package com.mobilephone.foodpai.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by qf on 2016/11/2.
 */
public class MylistView extends ListView {
    public MylistView(Context context) {
        this(context, null);
    }

    public MylistView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MylistView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
