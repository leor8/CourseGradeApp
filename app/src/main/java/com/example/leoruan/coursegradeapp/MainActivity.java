package com.example.leoruan.coursegradeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener, SeekBar.OnSeekBarChangeListener
{

    // Text listeners and number holders
    private Double assignment;
    private EditText assign_text;

    private Double part;
    private EditText part_text;

    private Double project;
    private EditText project_text;

    private Double quiz;
    private EditText quiz_text;

    private EditText exam_text;

    private TextView final_display;

    // Seek bar holders
    private Double exam = 80.0;
    private SeekBar exam_seek;

    // Reset button
    private Button reset;

    // Final grade
    private Double final_grade;

    // State Saving keys
    private static final String ASSIGNEMENT = "ASSIGNMENT";
    private static final String PARTICIPATION = "PARTICIPATION";
    private static final String PROJECT = "PROJECT";
    private static final String QUIZ = "QUIZ";
    private static final String EXAM = "EXAM";
    private static final String FINAL = "FINAL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assign_text = findViewById(R.id.assignment_input);
        assign_text.addTextChangedListener(this);

        part_text = findViewById(R.id.part_input);
        part_text.addTextChangedListener(this);

        project_text = findViewById(R.id.project_input);
        project_text.addTextChangedListener(this);

        quiz_text = findViewById(R.id.quiz_input);
        quiz_text.addTextChangedListener(this);

        exam_text = findViewById(R.id.exam_input);

        exam_seek = findViewById(R.id.exam_seek_bar);
        exam_seek.setOnSeekBarChangeListener(this);

        final_display = findViewById(R.id.final_input);

        reset = findViewById(R.id.reset);
        reset.setOnClickListener(this);
    }

    private void display_final() {
        if (assignment != null && part != null && project != null && quiz != null && exam != null){
            final_grade = (assignment * 15 / 100) + (part * 15 / 100) + (project * 20 / 100) + (quiz * 20 / 100)
                    + (exam * 30 / 100);
            if(final_grade > 100 || final_grade < 0) {
                Toast.makeText(getApplicationContext(), "You have entered illegal grade(s)", Toast.LENGTH_LONG).show();
            } else {
                final_display.setText(String.format("%.02f", final_grade));
            }
        }
    }

    @Override
    public void onClick(View v){
        assign_text.setText("");
        assignment = null;

        part_text.setText("");
        part = null;

        project_text.setText("");
        project = null;

        quiz_text.setText("");
        quiz = null;

        exam_text.setText("80");
        exam = 80.0;

        exam_seek.setProgress(80);

        final_display.setText("0");
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        exam = (double)seekBar.getProgress();
        exam_text.setText(String.format("%.00f", exam));
        display_final();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count){
        if(assign_text.isFocused()){
            try {
                assignment = Double.parseDouble(s.toString());
                if(assignment > 100 || assignment < 0){
                    Toast.makeText(getApplicationContext(), "The assignment grade you entered is out of range", Toast.LENGTH_SHORT).show();
                    assignment = 0.0;
                } else {
                    display_final();
                }
            } catch (NumberFormatException e) {
                assignment = 0.0;
            }
        }

        if(part_text.isFocused()){
            try {
                part = Double.parseDouble(s.toString());
                if(part > 100 || part < 0){
                    Toast.makeText(getApplicationContext(), "The participation grade you entered is out of range", Toast.LENGTH_SHORT).show();
                    part = 0.0;
                } else {
                    display_final();
                }
            } catch (NumberFormatException e) {
                part = 0.0;
            }
        }

        if(project_text.isFocused()){
            try {
                project = Double.parseDouble(s.toString());
                if(project > 100 || project < 0){
                    Toast.makeText(getApplicationContext(), "The project grade you entered is out of range", Toast.LENGTH_SHORT).show();
                    project = 0.0;
                } else {
                    display_final();
                }
            } catch (NumberFormatException e) {
                project = 0.0;
            }
        }

        if(quiz_text.isFocused()){
            try {
                quiz = Double.parseDouble(s.toString());
                if(quiz > 100 || quiz < 0){
                    Toast.makeText(getApplicationContext(), "The quiz grade you entered is out of range", Toast.LENGTH_SHORT).show();
                    quiz = 0.0;
                } else {
                    display_final();
                }
            } catch (NumberFormatException e) {
                quiz = 0.0;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s){
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after){
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putDouble(ASSIGNEMENT, assignment);
        outState.putDouble(PARTICIPATION, part);
        outState.putDouble(PROJECT, project);
        outState.putDouble(QUIZ, quiz);
        outState.putDouble(EXAM, exam);
        outState.putDouble(FINAL, final_grade);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        assignment = savedInstanceState.getDouble(ASSIGNEMENT);
        part = savedInstanceState.getDouble(PARTICIPATION);
        project = savedInstanceState.getDouble(PROJECT);
        quiz = savedInstanceState.getDouble(QUIZ);
        exam = savedInstanceState.getDouble(EXAM);
        final_grade = savedInstanceState.getDouble(FINAL);

        display_final();
    }


}
