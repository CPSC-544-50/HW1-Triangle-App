package com.example.triangleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button checkNumberButton;
    Button EndButton;
    EditText numberInputEditText;
    TextView resultsTextView;
    String outputString = "";
    Float[] floatLengths= new Float[3];

    final static String COMPARE_STRING = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finds views in the UI and makes them accessible in code
        resultsTextView = findViewById(R.id.resultsTextView);
        numberInputEditText = findViewById(R.id.stringUserInput);
        setupButton();
    }

    private void setupButton() {
        checkNumberButton = findViewById(R.id.checkNumberButton);
        EndButton = findViewById(R.id.EndButton);
        EndButton.setText("Stop");
//        EndButton.setVisibility(View.GONE);
        checkNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayText();
            }
        });
        EndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
            }
        });
    }
    private void stop(){
        if(EndButton.getText()=="Stop") {
            resultsTextView.setText("Program Stopped");
            EndButton.setText("Resume");
            checkNumberButton.setVisibility(View.GONE);
            numberInputEditText.setVisibility(View.GONE);
        }
        else{
            resultsTextView.setText("Program Resumed");
            EndButton.setText("Stop");
            checkNumberButton.setVisibility(View.VISIBLE);
            numberInputEditText.setVisibility(View.VISIBLE);
        }
    }
    // Changes resultsTextView based on the user's input
    private String displayText() {
        outputString = "Good input:\n";
        String[] sideLengths = numberInputEditText.getText().toString().split(",");
        if (validateInputs(sideLengths)) {
            outputString+= "\n" + classifyTriangle(floatLengths);
        }
        resultsTextView.setText(outputString);
        return outputString;
    }

    private boolean validateInputs(String[] sideLengths) {
        switch(sideLengths.length){

            case 1:
                try {
                    Float.parseFloat(sideLengths[0]);
                }
                catch (NumberFormatException | NullPointerException nfe) {
                    outputString = "You need to use commas as delimiters";
                    return false;
                }
                outputString = "You need two more inputs";
                return false;
            case 2:
                try {
                    Float.parseFloat(sideLengths[0]);
                    Float.parseFloat(sideLengths[1]);
                }
                catch (NumberFormatException | NullPointerException nfe) {
                    outputString = "Not a Comma Separated List of 3 Numbers";
                    return false;
                }
                outputString = "You need one more input";
                return false;
            case 3:
                int count=0;
                for (String side : sideLengths) {
                    try {
                        floatLengths[count] = Float.parseFloat(side);
                        outputString += floatLengths[count].toString() ;
                        if(count++ < sideLengths.length-1)
                            outputString += " | ";
                    }
                    catch (NumberFormatException | NullPointerException nfe) {
                        outputString = "Not a Comma Separated List of 3 Numbers";
                        return false;
                    }
                }

                if(confirmSanity(floatLengths)) {
                    return true;
                } else {
                    outputString+= "\n" + "All Triangle side lengths MUST be greater than zero";
                    return false;
                }

            default:
                outputString = "You have more than 3 inputs";
                return false;
        }
    }

    private boolean confirmTriangularity(Float sides[]){
        return sides[0]<sides[1]+sides[2] && sides[1]<sides[0]+sides[2] && sides[2]<sides[0]+sides[1];
    }

    private boolean confirmSanity(Float sides[]){
        return sides[0]>0 && sides[1]>0 && sides[2]>0;
    }

    private String classifyTriangle(Float sides[]) {
        if(!confirmTriangularity(sides))
            return "-- This is not a valid triangle. One side is longer (or equal) that the sum of the other two sides --";
        if(Float.compare(sides[0],sides[1]) == 0) {
            if (Float.compare(sides[1],sides[2]) == 0)
                return "--Equilateral--";
            else
                return "--Isosceles--";
        } else if(Float.compare(sides[1],sides[2]) == 0) {
            return "--Isosceles--";
        }
        else {
            return "--Scalene--";
        }
    }
}
