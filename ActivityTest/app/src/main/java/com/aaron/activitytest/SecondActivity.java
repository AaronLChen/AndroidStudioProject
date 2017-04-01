package com.aaron.activitytest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by chenl on 2/27/2017.
 */

public class SecondActivity extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d("SecondActivity", "Task id is " + getTaskId());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.second_layout);

//        Intent intent = getIntent();
//        String data = intent.getStringExtra("extra_data");
//        Log.d("SecondActivity", data);

        Button button2 = (Button) findViewById(R.id.button_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
//                intent.putExtra("data_return", "Hello FirstActivity!");
//                setResult(RESULT_OK, intent);
//                finish();
                startActivity(intent);
            }
        });

        }

//        public void onBackPressed() {
//        Intent intent = new Intent();
//        intent.putExtra("data_return", "Hello FirstActivity!!");
//        setResult(RESULT_OK, intent);
//        finish();
//    }
}
