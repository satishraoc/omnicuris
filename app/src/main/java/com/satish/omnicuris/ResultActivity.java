package com.satish.omnicuris;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView tvCount = (TextView) findViewById(R.id.tvCorrectAnswer);
        tvCount.setText(getIntent().getStringExtra("CorrectAns"));

    }

}
