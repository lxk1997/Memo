package com.clxk1997.memo.ui;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.clxk1997.memo.R;
import com.clxk1997.memo.adapter.MemoTabAdapter;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.UnderlinePageIndicator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton ib_menu;
    private ImageButton ib_search;
    private Spinner spinner;

    private TabPageIndicator tab;
    private UnderlinePageIndicator underline;
    private ViewPager viewPager;
    private MemoTabAdapter tabAdapter;
    private ArrayAdapter spAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initEvent();
    }

    public void initView() {
        ib_menu = findViewById(R.id.ib_menu);
        ib_search = findViewById(R.id.ib_search);
        spinner = findViewById(R.id.spinner);

        tab = findViewById(R.id.tab);
        underline = findViewById(R.id.underline);
        viewPager = findViewById(R.id.vp);
        tabAdapter = new MemoTabAdapter(getSupportFragmentManager());

    }

    public void initEvent() {
        viewPager.setAdapter(tabAdapter);
        tab.setViewPager(viewPager, 0);
        underline.setViewPager(viewPager);
        underline.setFades(false);
        tab.setOnPageChangeListener(underline);

        ib_search.setOnClickListener(this);
        ib_menu.setOnClickListener(this);
        String[] spmenu = {"新建","全选"};
        spAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spmenu);
        //设置下拉列表的风格
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spAdapter);
    }

    @Override
    public void onClick(View v) {

    }
}
