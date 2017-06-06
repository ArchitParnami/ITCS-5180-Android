package com.example.archit.inclass3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public final static CharSequence[] department = {"SIS", "CS", "BIO", "Others"};
    public static int ids = 0;
    public static final String STUDENT_KEY = "STUDENT";
    private RadioGroup departmentGroup;
    private EditText nameText;
    private EditText emailText;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);

        departmentGroup = new RadioGroup(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        departmentGroup.setLayoutParams(params);


        for(CharSequence str : department) {

            RadioButton depButton = new RadioButton(this);
            depButton.setLayoutParams(params);
            depButton.setText(str);
            depButton.setId(ids);
            depButton.setTextSize(18);
            ids++;
            departmentGroup.addView(depButton);
        }
        departmentGroup.check(0);
        layout.addView(departmentGroup, 3);

        nameText = (EditText) findViewById(R.id.nameEditText);
        emailText = (EditText) findViewById(R.id.emailEditText);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setProgress(10);

    }

    public void onSubmit(View v) {



        String name = nameText.getText().toString();
        String email = emailText.getText().toString();

        if(name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Name and Email can not be emmpty", Toast.LENGTH_SHORT).show();
            return;
        }

        CharSequence dep = department[departmentGroup.getCheckedRadioButtonId()];

        int mood = seekBar.getProgress();

        Student student = new Student(name, email, dep, mood);

        //Log.d("Student", student.toString());

        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(STUDENT_KEY, student);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        nameText.setText("");
        emailText.setText("");
        departmentGroup.check(0);
        seekBar.setProgress(10);

    }
}
