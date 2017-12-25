package com.example.administrator.tablayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.library.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {
    ViewPager test_viewpager;
    TabLayout test_tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test_viewpager = findViewById(R.id.test_viewpager);
        test_tab = findViewById(R.id.test_tab);
        List<View> views = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setText("" + i);
            textView.setTextSize(50);
            views.add(textView);

            Random random = new Random();
            String title = "";
            for (int j = 0; j < random.nextInt(20) + 2; j++) {
                title += "" + i;
            }
            titles.add(title);
        }
        test_tab.setTabTitles(titles);
        test_tab.setViewPager(test_viewpager);
        TestViewAdapter adapter = new TestViewAdapter(views);
        test_viewpager.setAdapter(adapter);
    }

    public class TestViewAdapter extends PagerAdapter {
        private List<View> mViewList;

        public TestViewAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }
    }

}

