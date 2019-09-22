package com.example.triangleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button checkNumberButton;
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

        // This handles what happens when the button is clicked
        checkNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayText();
            }
        });
    }

    // Changes resultsTextView based on the user's input
    private void displayText() {
        String[] sideLengths = numberInputEditText.getText().toString().split(",");
        String outputString = "Good input:\n";
        if ( sideLengths.length!= 3)
            outputString = "Not a Comma Seperated List of 3 Numbers";
        else {
            int count=0;
            for (String side : sideLengths) {

                try {
                    Float temp = Float.parseFloat(side);
                    outputString += temp.toString() ;
                    if(count++ < sideLengths.length-1)
                            outputString += " | ";
                } catch (NumberFormatException | NullPointerException nfe) {
                    outputString = "Not a Comma Seperated List of 3 Numbers";
                    break;
                }
            }
        }

        resultsTextView.setText(outputString);

    }
}
