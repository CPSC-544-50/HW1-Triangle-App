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
        numberInputEditText = findViewById(R.id.numberInputEditText);
        setupButton();
    }

    private void setupButton() {
        checkNumberButton = findViewById(R.id.checkNumberButton);

        // This handles what happens when the button is clicked
        checkNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkNumber();
            }
        });
    }

    // Changes resultsTextView based on the user's input
    private void checkNumber() {
        if (numberInputEditText.getText().toString().equals(COMPARE_STRING)) {
            resultsTextView.setText(getResources().getString(R.string.correctString));
        } else if (numberInputEditText.getText().toString().equals("")) {
            resultsTextView.setText(getResources().getString(R.string.enterANumberString));
        } else {
            resultsTextView.setText(getResources().getString(R.string.incorrectString));
        }
    }
}
