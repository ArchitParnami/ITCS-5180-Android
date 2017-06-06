package com.example.archit.inclass3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class EditActivity extends AppCompatActivity {

    public static final String labelName = "Name";
    public static final String labelEmail = "Email";
    public static final String labelDepartment = "Department";
    public static final String labelMood = "Mood";
    public static final String LABEL_KEY = "label";

    public static Student student;

    public enum REQ_CODE {
        Name, Email, Department, Mood
    }

    private InfoView nameView;
    private InfoView emailView;
    private InfoView deptView;
    private InfoView moodView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        student = (Student)getIntent().getExtras().getSerializable(MainActivity.STUDENT_KEY);
        String mood = student.getMood() + " %  Positive";

        nameView = new InfoView(this, labelName, student.getName());
        emailView = new InfoView(this, labelEmail, student.getEmail());
        deptView  = new InfoView(this, labelDepartment, student.getDepartment().toString());
        moodView = new InfoView(this, labelMood, mood);

        LinearLayout editLayout = (LinearLayout) findViewById(R.id.editLayout);
        editLayout.addView(nameView);
        editLayout.addView(emailView);
        editLayout.addView(deptView);
        editLayout.addView(moodView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        REQ_CODE code = REQ_CODE.values()[requestCode];

        switch (code) {
            case Name:
                nameView.updateValue(student.getName());
                break;

            case Email:
                emailView.updateValue(student.getEmail());
                break;

            case Department:
                deptView.updateValue(student.getDepartment().toString());
                break;

            case Mood:
                moodView.updateValue(student.getMood() + " % Positive");
                break;

        }

    }
}
