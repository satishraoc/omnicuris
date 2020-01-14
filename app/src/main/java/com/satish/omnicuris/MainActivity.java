package com.satish.omnicuris;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.satish.omnicuris.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivityLogs";
    private Button btnNextQue;
    private RadioButton rb1, rb2, rb3, rb4;
    private ArrayList<Question> questionLists;
    private static int quesNum = 0;
    private TextView tvQuestion;
    private RadioGroup rgAns;
    private int correctAns = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inti();
        loadQuestions();
        showQuestion(quesNum);
    }

    private void inti() {
        tvQuestion = findViewById(R.id.tvQuestion);
        btnNextQue = findViewById(R.id.btnNextQue);
        rgAns = findViewById(R.id.radioGroup1);
        rb1 = findViewById(R.id.radioButton1);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb4 = findViewById(R.id.radioButton4);

        btnNextQue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckAnswer()) {
                    Log.d(TAG, "size : "+quesNum +" "+questionLists.size());

                    if (questionLists != null && quesNum < questionLists.size()-1) {
                        quesNum = quesNum + 1;
                        showQuestion(quesNum);
                    } else {
                        Log.d(TAG, "correct answer "+correctAns);
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        intent.putExtra("CorrectAns", String.valueOf(correctAns));
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private boolean CheckAnswer() {
        int checkedRadioButtonId = rgAns.getCheckedRadioButtonId();
        if (checkedRadioButtonId == -1) {
            Toast.makeText(this, "Select answer", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            int selected = questionLists.get(quesNum).getCorrectIndex();
            switch (selected) {
                case 0:
                    if (checkedRadioButtonId == R.id.radioButton1) {
                        correctAns = correctAns + 1;

                    }
                    break;
                case 1:
                    if (checkedRadioButtonId == R.id.radioButton2) {
                        correctAns = correctAns + 1;
                    }
                    break;
                case 2:
                    if (checkedRadioButtonId == R.id.radioButton3) {
                        correctAns = correctAns + 1;
                    }
                    break;
                case 3:
                    if (checkedRadioButtonId == R.id.radioButton4) {
                        correctAns = correctAns + 1;
                    }
                    break;
            }
            return true;

        }
    }

    private void showQuestion(int quesNum) {
        clearButton();
        Question question = questionLists.get(quesNum);
        tvQuestion.setText(question.getQuestion());
        Log.d(TAG, "show answer: " + question.getAnswers().toString());
        rb1.setText(question.getAnswers().get(0));
        rb2.setText(question.getAnswers().get(1));
        rb3.setText(question.getAnswers().get(2));
        rb4.setText(question.getAnswers().get(3));
    }

    private void clearButton() {
        rb1.setChecked(false);
        rb2.setChecked(false);
        rb3.setChecked(false);
        rb4.setChecked(false);
    }

    private void loadQuestions() {
        questionLists = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(Objects.requireNonNull(loadJSONFromAsset(MainActivity.this)));
            JSONArray m_jArry = obj.getJSONArray("questions");
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d(TAG, jo_inside.getString("question"));
                String question = jo_inside.getString("question");
                String correctIndex = jo_inside.getString("correctIndex");

                m_li = new HashMap<String, String>();
                m_li.put("Question", question);
                m_li.put("correctIndex", correctIndex);
                Question models = new Gson().fromJson(jo_inside.toString(), Question.class);

                Log.d(TAG, "loadQuestions: " + models.toString());
                questionLists.add(models);

            }
            Log.d(TAG, "loadQuestions: " + questionLists.size());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static String loadJSONFromAsset(Activity activity) {
        String json = null;
        try {
            InputStream is = activity.getAssets().open("question.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}
