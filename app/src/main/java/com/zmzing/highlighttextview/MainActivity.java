package com.zmzing.highlighttextview;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HighlightTextView tv_testContent1 = (HighlightTextView) findViewById(R.id.main_tv_testContent1);
        HighlightTextView tv_testContent2 = (HighlightTextView) findViewById(R.id.main_tv_testContent2);
        HighlightTextView tv_testContent3 = (HighlightTextView) findViewById(R.id.main_tv_testContent3);
        HighlightTextView tv_testContent4 = (HighlightTextView) findViewById(R.id.main_tv_testContent4);
        HighlightTextView tv_testContent5 = (HighlightTextView) findViewById(R.id.main_tv_testContent5);

        tv_testContent1.setText(getString(R.string.test_content_1));
        tv_testContent2.setText(getString(R.string.test_content_2));
        tv_testContent3.setText(getString(R.string.test_content_3));
        tv_testContent4.setText(getString(R.string.test_content_4));
        tv_testContent5.setText(getString(R.string.test_content_5));
    }

}
