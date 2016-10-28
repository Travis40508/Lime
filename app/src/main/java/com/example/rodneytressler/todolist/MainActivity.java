package com.example.rodneytressler.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView buttonGrocery;
    private TextView prompt;
    private Button buttonPrompt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGrocery = (ImageView) findViewById(R.id.main_button_groceries);
        prompt = (TextView) findViewById(R.id.startPrompt);
        buttonPrompt = (Button) findViewById(R.id.button_prompt);

        startApp();
        buttonGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groceryList();
            }
        });
    }
    public void groceryList() {
        Intent intent = new Intent(this, Groceries.class);
        startActivity(intent);
    }
    public void startApp() {
        prompt.setText(R.string.prompt1);
        buttonPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondPrompt();
            }
        });
    }
    public void secondPrompt() {
        prompt.setText(R.string.prompt2);
        buttonPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thirdPrompt();
            }
        });
    }

    public void thirdPrompt() {
        prompt.setText(R.string.prompt3);
        buttonPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fourthPrompt();
            }
        });
    }
    public void fourthPrompt() {
        prompt.setText(R.string.prompt4);
        buttonPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fifthPrompt();
            }
        });
    }
    public void fifthPrompt() {
        prompt.setText(R.string.prompt5);
        buttonPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sixthPrompt();
            }
        });
    }
    public void sixthPrompt() {
        prompt.setText(R.string.prompt6);
        buttonPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seventhPrompt();
            }
        });
    }
    public void seventhPrompt() {
        prompt.setText(R.string.prompt7);
        buttonPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prompt.setVisibility(View.GONE);
                buttonPrompt.setVisibility(View.GONE);
                buttonGrocery.setVisibility(View.VISIBLE);
            }
        });
    }
}
