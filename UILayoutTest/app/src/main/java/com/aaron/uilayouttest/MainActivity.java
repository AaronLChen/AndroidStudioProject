package com.aaron.uilayouttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.view.View.OnClickListener;
import android.widget.Toast;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {

//    private Button send;
//    private EditText input_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        send = (Button) findViewById(R.id.send);
//        send.setOnClickListener(this);
//        input_message = (EditText) findViewById(R.id.input_message);

//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String inputText = input_message.getText().toString();
//                Toast.makeText(MainActivity.this, inputText, Toast.LENGTH_LONG).show();
//            }
//        });
    }
//
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.send:
//                String inputText = input_message.getText().toString();
//                Toast.makeText(MainActivity.this, inputText, Toast.LENGTH_LONG).show();
//        }
//    }


}
