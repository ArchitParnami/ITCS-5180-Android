package com.example.archit.inclass3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class UpdateActivity extends AppCompatActivity {

    private RadioGroup departmentGroup;
    private EditText nameView;
    private EditText emailView;
    private SeekBar seekBar;
    private TextView groupLabelView;
    private TextView moodLabelView;
    private EditActivity.REQ_CODE code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        LinearLayout layout = (LinearLayout) findViewById(R.id.updateLayout);

        departmentGroup = new RadioGroup(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        departmentGroup.setLayoutParams(params);


        for(CharSequence str : MainActivity.department) {

            RadioButton depButton = new RadioButton(this);
            depButton.setLayoutParams(params);
            depButton.setText(str);
            depButton.setId(MainActivity.ids);
            depButton.setTextSize(18);
            departmentGroup.addView(depButton);
            MainActivity.ids++;

        }
        departmentGroup.check(0);
        layout.addView(departmentGroup, 3);

        Student student = EditActivity.student;
        setData(student);

        String label = getIntent().getExtras().getString(EditActivity.LABEL_KEY);

        switch (label) {
            case EditActivity.labelName:
                code = EditActivity.REQ_CODE.Name;
                nameView.setVisibility(View.VISIBLE); break;
            case EditActivity.labelEmail:
                code = EditActivity.REQ_CODE.Email;
                emailView.setVisibility(View.VISIBLE); break;
            case EditActivity.labelDepartment:
                code = EditActivity.REQ_CODE.Department;
                groupLabelView.setVisibility(View.VISIBLE);
                departmentGroup.setVisibility(View.VISIBLE);break;
            case EditActivity.labelMood:
                code = EditActivity.REQ_CODE.Mood;
                moodLabelView.setVisibility(View.VISIBLE);
                seekBar.setVisibility(View.VISIBLE); break;
        }

    }

    private void setData(Student student) {

        nameView = (EditText) findViewById(R.id.updateNameEditText);
        nameView.setText(student.getName());
        nameView.setVisibility(View.INVISIBLE);

        emailView = (EditText) findViewById(R.id.updateEmailEditText);
        emailView.setText(student.getEmail());
        emailView.setVisibility(View.INVISIBLE);

        moodLabelView = (TextView) findViewById(R.id.updateSeekBarLabel);
        moodLabelView.setVisibility(View.INVISIBLE);

        seekBar = (SeekBar) findViewById(R.id.updateSeekBar);
        seekBar.setProgress(student.getMood());
        seekBar.setVisibility(View.INVISIBLE);

        groupLabelView = (TextView) findViewById(R.id.updateDepartmentView);
        groupLabelView.setVisibility(View.INVISIBLE);

        for(int i = 0; i < departmentGroup.getChildCount(); i++) {
            RadioButton button = (RadioButton) departmentGroup.getChildAt(i);
            String dept = button.getText().toString();
            if (dept.equalsIgnoreCase(student.getDepartment().toString())) {
                button.setChecked(true);
                break;
            }
        }

        departmentGroup.setVisibility(View.INVISIBLE);

    }


    public void onSave(View v) {

        switch (code) {

            case Name:
                String name = nameView.getText().toString();
                if(name.isEmpty()) {
                    Toast.makeText(this, "Name can not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                EditActivity.student.setName(name);
                break;

            case Email:
                String email = emailView.getText().toString();
                if(email.isEmpty()) {
                    Toast.makeText(this, "Email can not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                EditActivity.student.setEmail(email);
                break;

            case Department:
                int checkedId =  departmentGroup.getCheckedRadioButtonId();
                String dept = ((RadioButton)findViewById(checkedId)).getText().toString();
                EditActivity.student.setDepartment(dept);
                break;

            case Mood:
                EditActivity.student.setMood(seekBar.getProgress());
                break;
        }

        finish();
    }

}
