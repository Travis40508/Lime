package com.example.rodneytressler.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//Class purpose is to present instructions to user and begin the app.
public class MainActivity extends AppCompatActivity {
    //Fields to be used in activity.
    private ImageView buttonStart;
    private TextView prompt;
    private Button buttonPrompt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaration of fields within the layout.
        buttonStart = (ImageView) findViewById(R.id.main_button_groceries);
        prompt = (TextView) findViewById(R.id.startPrompt);
        buttonPrompt = (Button) findViewById(R.id.button_prompt);

        startApp();
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groceryList();
            }
        });
    }
    //Gives user step-by-step instructions on how to use app.
    public void groceryList() {
        Intent intent = new Intent(this, actualList.class);
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

    //Makes prompts disappear and launches the start app button.
    public void seventhPrompt() {
        prompt.setText(R.string.prompt7);
        buttonPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prompt.setVisibility(View.GONE);
                buttonPrompt.setVisibility(View.GONE);
                buttonStart.setVisibility(View.VISIBLE);
            }
        });
    }
}
