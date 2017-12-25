package com.example.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */
public class TabLayout extends HorizontalScrollView {

    private List<String> tabTitleList;

    private ViewPager viewPager;

    private LinearLayout containerView;

    private int left, top, right, bottom;

    private int defaultIndex = 0;

    private Paint mPaint = new Paint();

    private int padding = 20;

    private int textSize = 14;

    private int indexHeight = 5;

    private int selectColor = 0xff000000;

    private int defaultColor = 0xff999999;

    private int height, width;

    public TabLayout(Context context) {
        super(context);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTabTitles(List<String> tabTitleList) {
        this.tabTitleList = tabTitleList;
        addItemViews();
    }

    public void setViewPager(final ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scrollToPosition(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                if (containerView.getChildCount() > position && containerView.getChildAt(position) instanceof TextView) {
                    for (int i = 0; i < containerView.getChildCount(); i++) {
                        if (i == position) {
                            ((TextView) containerView.getChildAt(i)).setTextColor(selectColor);
                        } else {
                            ((TextView) containerView.getChildAt(i)).setTextColor(defaultColor);
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void scrollToPosition(int position, float positionOffset) {
        View positionView = containerView.getChildAt(position);
        left = (int) (positionView.getX() + positionView.getMeasuredWidth() * positionOffset) + padding;
        if (position + 1 < containerView.getChildCount()) {
            View tagView = containerView.getChildAt(position + 1);
            right = (int) (left + positionView.getMeasuredWidth() + (tagView.getMeasuredWidth() -
                    positionView.getMeasuredWidth()) * positionOffset) - 2 * padding;
        } else {
            right = left + positionView.getMeasuredWidth() - 2 * padding;
        }
        TabLayout.this.smoothScrollTo(left - padding - (width - (right - left)) / 2, 0);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
    }

    private void addItemViews() {
        this.removeAllViews();
        this.setHorizontalScrollBarEnabled(false);
        this.setOverScrollMode(View.OVER_SCROLL_NEVER);
        containerView = new LinearLayout(getContext());
        containerView.setGravity(Gravity.CENTER);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(containerView, lp);
        for (int i = 0; i < tabTitleList.size(); i++) {
            TextView item = new TextView(getContext());
            item.setPadding(padding, 0, padding, 0);
            item.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            TextPaint paint = item.getPaint();
            paint.setFakeBoldText(true);
            if (i == 0) {
                item.setTextColor(selectColor);
            } else {
                item.setTextColor(defaultColor);
            }
            item.setGravity(Gravity.CENTER);
            final int index = i;
            item.setText(tabTitleList.get(i));
            containerView.addView(item, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(index, false);
                    setCurrentIndex(index);
                }
            });
        }

        mPaint.setColor(selectColor);
        mPaint.setAntiAlias(true);
        setCurrentIndex(defaultIndex);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (top == 0) {
            top = height * 3 / 4;
            bottom = top + indexHeight;
        }
        canvas.drawRect(new Rect(left, top, right, bottom), mPaint);
        canvas.save();
    }

    public void setCurrentIndex(final int index) {
        if (containerView == null || index >= containerView.getChildCount()) {
            return;
        }
        this.post(new Runnable() {
            @Override
            public void run() {
                scrollToPosition(index, 0);
            }
        });
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setIndexHeight(int indexHeight) {
        this.indexHeight = indexHeight;
    }

    public void setSelectColor(int selectColor) {
        this.selectColor = selectColor;
    }

    public void setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
    }
}