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
    String input = "";
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
        input = numberInputEditText.getText().toString();
        inputHistory.add(input);
        inputPointer=inputHistory.size();
        String[] sideLengths = input.split(",");

        if (validateInputs(sideLengths)) {
            outputString+= classifyTriangle(floatLengths) + "\n";
            resultsTextView.setTextColor(getResources().getColor(R.color.colorLightGray));
        } else {
            resultsTextView.setTextColor(Color.RED);
        }
        resultsTextView.setText(outputString);
        outputString="";
    }

    private boolean validateInputs(String[] sideLengths) {
        boolean valid = true;
        switch(sideLengths.length){
            case 0:
                outputString = formattedInputValues(true) + formattedInputValues(false);
                outputString += "Please enter a comma-separated list of three numbers. Use the format xx,xx,xx\n\n";
                valid = false;
                break;
            case 1:
                outputString = formattedInputValues(true) + formattedInputValues(false);
                outputString += checkForValidEntry(sideLengths[0], 0);

                outputString += "You need two more values\n\n";
                outputString += "You need to use commas to separate the three values\n\n";
                valid = false;
                break;
            case 2:
                outputString = formattedInputValues(true) + formattedInputValues(false);
                outputString += checkForValidEntry(sideLengths[0], 0);
                outputString += checkForValidEntry(sideLengths[1], 1);

                outputString += "You need one more value\n\n";
                outputString += "Not a comma-separated list of three numbers. Please use the format xx,xx,xx\n\n";
                valid = false;
                break;
            case 3:
                outputString = formattedInputValues(true) + formattedInputValues(false);
                String errorMessage = "";

                errorMessage += checkForValidEntry(sideLengths[0], 0);
                errorMessage += checkForValidEntry(sideLengths[1], 1);
                errorMessage += checkForValidEntry(sideLengths[2], 2);

                if(errorMessage.length() > 0) {
                    outputString += errorMessage + "Not a comma-separated list of three numbers. Please use the format xx,xx,xx\n\n";
                    return false;
                }

                setFloatLengths(sideLengths);
                if(!confirmTriangularity(floatLengths)) {
                    outputString += "This is not a valid triangle. One side is longer (or equal) that the sum of the other two sides\n\n";
                    valid = false;
                }
                break;
            default:
                outputString = formattedInputValues(true) + formattedInputValues(false);
                outputString += "You have more than three values entered\n\n";
                valid = false;
                break;
        }
        return valid;
    }

    private String formattedInputValues(boolean showInput) {
        if (showInput) {
            return "Input: Values for TriangleApp?: " + input + "\n";
        } else {
            // show output
            return "Output: [" + input + "] = ";
        }
    }

    private void setFloatLengths(String[] sideLengths) {
        for(int i=0; i<sideLengths.length; i++) {
            floatLengths[i] = Float.parseFloat(sideLengths[i]);
        }
    }

    private String checkForValidEntry(String entry, int index) {
        String errorMessage = "";
        Float number;

        String entryIndex = "";
        switch (index) {
            case 0:
                entryIndex = "The first value ";
                break;
            case 1:
                entryIndex = "The second value ";
                break;
            case 2:
                entryIndex = "The third value ";
                break;
        }

        //check if the value is a number
        try {
            number = Float.parseFloat(entry);
        }
        catch (NumberFormatException | NullPointerException nfe) {
            errorMessage += entryIndex + "needs to be a number\n\n";
            return errorMessage;
        }

        if (number < 1 && number >= 0) {
            errorMessage += entryIndex + "needs to be greater than or equal to 1\n\n";
        } else if (number < 0) {
            errorMessage += entryIndex + "needs to be a positive number\n\n";
        } else if (number > 100) {
            errorMessage += entryIndex + "needs to be less than or equal to 100\n\n";
        }
        return errorMessage;
    }

    private boolean confirmTriangularity(Float sides[]){
        return sides[0]<sides[1]+sides[2] && sides[1]<sides[0]+sides[2] && sides[2]<sides[0]+sides[1];
    }

    private String classifyTriangle(Float sides[]) {
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
