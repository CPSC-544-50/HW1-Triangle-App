package com.example.triangleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button checkNumberButton;
    Button EndButton;
    EditText numberInputEditText;
    TextView resultsTextView;

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
        EndButton.setVisibility(View.GONE); //comment out to get stop functionality
        // This handles what happens when the button is clicked
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
        String[] sideLengths = numberInputEditText.getText().toString().split(",");
        String outputString = "Good input:\n";
        if ( sideLengths.length!= 3)
            outputString = "Not a Comma Seperated List of 3 Numbers";
        else {
            int count=0;
            Float[] floatLengths= new Float[3];
            for (String side : sideLengths) {
                try {
                    floatLengths[count] = Float.parseFloat(side);
                    outputString += floatLengths[count].toString() ;
                    if(count++ < sideLengths.length-1)
                            outputString += " | ";
                }
                catch (NumberFormatException | NullPointerException nfe) {
                    outputString = "Not a Comma Seperated List of 3 Numbers";
                    break;
                }
            }
//            if(floatLengths[2]!=null)
//                if(confirmSanity(floatLengths))
//                    outputString+= "\n" + classifyTriangle(floatLengths);
//                else
//                    outputString+= "\n" + "All Triangle side lengths MUST be greater than zero";
        }

        resultsTextView.setText(outputString);
        return outputString;

    }
    private boolean confirmTriangularity(Float sides[]){
        return sides[0]<sides[1]+sides[2] && sides[1]<sides[0]+sides[2] && sides[2]<sides[0]+sides[1];
    }
    private boolean confirmSanity(Float sides[]){
        return sides[0]>0 && sides[1]>0 && sides[2]>0;
    }
    private String classifyTriangle(Float sides[]) {
        if(!confirmTriangularity(sides))
            return "-- This is not a valid triangle. One side is longer that the sum of the other two sides --";
        if(sides[0]==sides[1])
            if(sides[1]==sides[2])
                return "--Equalateral--";
            else
                return "--Equalateral--";
        else if(sides[1]==sides[2])
            return "--Iscoceles--";
        else
            return "--Scalene--";
    }
}
