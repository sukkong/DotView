package com.tubb.dotviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tubb.dotview.DotView;

/**
 * Created by bingbing.tu
 * 2015/7/2.
 */
public class DotViewTestActivity extends AppCompatActivity{
    private DotView dotView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dotview);

        dotView = (DotView)findViewById(R.id.dot);
    }

    public void viewClick(View view){
        switch (view.getId()){
            case R.id.btnL:
                dotView.setDrawableLeft(R.mipmap.star2);
                break;
            case R.id.btnR:
                dotView.setDrawableRight(R.mipmap.dot);
                break;
        }
    }
}
