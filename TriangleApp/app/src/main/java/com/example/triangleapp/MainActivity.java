package com.example.triangleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import java.util.*;

import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//import com.google.android.material.floatingactionbutton;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout mainLayout;
    Button checkNumberButton;
    Button EndButton;
    com.google.android.material.floatingactionbutton.FloatingActionButton UpButton;
    com.google.android.material.floatingactionbutton.FloatingActionButton DownButton;
    EditText numberInputEditText;
    TextView resultsTextView;
    String outputString = "";
    Float[] floatLengths= new Float[3];

    private List<String> inputHistory = new ArrayList<String>();
    private int inputPointer=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finds views in the UI and makes them accessible in code
        numberInputEditText = findViewById(R.id.stringUserInput);
        resultsTextView = findViewById(R.id.resultsTextView);
        UpButton = findViewById(R.id.UpButton);
        DownButton = findViewById(R.id.DownButton);
        setupButton();

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorBlue)));
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#dbddeb\">" + getString(R.string.app_name) + "</font>")));

        // Use this to hide the keyboard
        mainLayout = findViewById(R.id.main_layout);
    }

    private void setupButton() {
        checkNumberButton = findViewById(R.id.checkNumberButton);
        EndButton = findViewById(R.id.EndButton);
        EndButton.setText("Stop");
//        EndButton.setVisibility(View.GONE);
        checkNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                displayText();
            }
        });
        EndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
            }
        });
        UpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputPointer>0)
                    numberInputEditText.setText(inputHistory.get(--inputPointer));
            }

        });
        DownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(inputPointer<inputHistory.size()-1)
                    numberInputEditText.setText(inputHistory.get(++inputPointer));
            }

        });

    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
    }

    private void stop(){
        hideKeyboard();

        if(EndButton.getText()=="Stop") {
            resultsTextView.setText("Program Stopped");
            EndButton.setText("Resume");
            checkNumberButton.setVisibility(View.GONE);
            numberInputEditText.setVisibility(View.GONE);
            UpButton.hide();
            DownButton.hide();
            inputPointer=0;
            inputHistory = new ArrayList<String>();
            numberInputEditText.setText("");
        }
        else{
            resultsTextView.setText("Program Resumed");
            EndButton.setText("Stop");
            UpButton.show();
            DownButton.show();
            checkNumberButton.setVisibility(View.VISIBLE);
            numberInputEditText.setVisibility(View.VISIBLE);
        }
    }

    // Changes resultsTextView based on the user's input
    private void displayText() {
        String input= numberInputEditText.getText().toString();
        inputHistory.add(input);
        inputPointer=inputHistory.size();
        String[] sideLengths = input.split(",");

        if (validateInputs(sideLengths)) {
            outputString+= classifyTriangle(floatLengths) + "\n";
        }
        resultsTextView.setText(outputString);
        outputString="";
    }

    private boolean validateInputs(String[] sideLengths) {
        boolean valid = true;
        switch(sideLengths.length){
            case 1:
                try {
                    Float.parseFloat(sideLengths[0]);
                }
                catch (NumberFormatException | NullPointerException nfe) {
                    outputString += "You need to use commas to separate the three values\n\n";
                }
                outputString += "You need two more values\n\n";
                valid = false;
                break;
            case 2:
                try {
                    Float.parseFloat(sideLengths[0]);
                    Float.parseFloat(sideLengths[1]);
                }
                catch (NumberFormatException | NullPointerException nfe) {
                    outputString += "Not a comma-separated list of three numbers. Please use the format xx,xx,xx\n\n";
                }
                outputString += "You need one more value\n\n";
                valid = false;
                break;
            case 3:
                int count=0;
                outputString+="[";
                for (String side : sideLengths) {
                    try {
                        floatLengths[count] = Float.parseFloat(side);
                        outputString += floatLengths[count].toString() ;
                        if(count++ < sideLengths.length-1)
                            outputString += ", ";
                    }
                    catch (NumberFormatException | NullPointerException nfe) {
                        outputString = "Not a comma-separated list of three numbers. Please use the format xx,xx,xx\n\n";
                        valid = false;
                        break;
                    }
                }
                if (valid) {
                    outputString += "] = ";
                }

                if(valid && !confirmSanity(floatLengths)) {
                    outputString = "All Triangle side lengths MUST be greater than zero\n\n";
                    valid = false;
                }
                break;
            default:
                outputString += "You have more than three values entered\n\n";
                valid = false;
                break;
        }
        return valid;
    }

    private boolean confirmTriangularity(Float sides[]){
        return sides[0]<sides[1]+sides[2] && sides[1]<sides[0]+sides[2] && sides[2]<sides[0]+sides[1];
    }

    private boolean confirmSanity(Float sides[]){
        return sides[0]>0 && sides[1]>0 && sides[2]>0;
    }

    private String classifyTriangle(Float sides[]) {
        if(!confirmTriangularity(sides)) {
            outputString = "";
            return "This is not a valid triangle. One side is longer (or equal) that the sum of the other two sides.\n\n";
        }

        if(Float.compare(sides[0],sides[1]) == 0) {
            if (Float.compare(sides[1],sides[2]) == 0)
                return "Equilateral";
            else
                return " Isosceles";
        } else if(Float.compare(sides[1],sides[2]) == 0) {
            return "Isosceles";
        }
        else {
            return "Scalene";
        }
    }
}
