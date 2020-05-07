package chapter.android.aweme.ss.com.homework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;
import android.widget.EditText;
import android.view.Window;

public class Chatroom extends AppCompatActivity {

    private TextView tv_content_info;
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_chatroom);

        tv_content_info = findViewById(R.id.tv_content_info);

        Intent intent = getIntent();

        tv_content_info.setText("No." + intent.getStringExtra("num"));
        tv_content_info.setTextSize(25);



    }

}
