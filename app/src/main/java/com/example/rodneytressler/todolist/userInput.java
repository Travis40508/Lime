package com.example.rodneytressler.todolist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class userInput extends AppCompatActivity {
    private static final int SELECTED_PICTURE = 1;
    ImageView iv;

    private Button userInputSaveButton;
    private EditText userInputTitle;
    private EditText userInputText;
    private EditText userInputMonth;
    private EditText userInputDay;
    private EditText userInputTime;
    private EditText userInputcategory;
    private Button addImageButton;
    private String fullDate;
    groceryListItem item1;
    ArrayList<groceryListItem> arrayOfTasks = new ArrayList<groceryListItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);

        iv = (ImageView) findViewById(R.id.image_input);
        userInputTitle = (EditText) findViewById(R.id.user_input_title);
        userInputText = (EditText) findViewById(R.id.user_input_text);
        userInputMonth = (EditText) findViewById(R.id.user_input_date_month);
        userInputDay = (EditText) findViewById(R.id.user_input_date_day);
        userInputTime = (EditText) findViewById(R.id.user_input_date_time);
        userInputSaveButton = (Button) findViewById(R.id.user_input_button_save);
        userInputcategory = (EditText) findViewById(R.id.category_edit);
        addImageButton = (Button) findViewById(R.id.user_input_button_image);


        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClick();
            }
        });
        userInputSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String month = userInputMonth.getText().toString();
                String day = userInputDay.getText().toString();
                String time = userInputTime.getText().toString();
                String fullDate = month + "/" + day + " " + time;
                String title = userInputTitle.getText().toString();
                String text = userInputText.getText().toString();
                String category = userInputcategory.getText().toString();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("titleResult", title);
                returnIntent.putExtra("textResult", text);
                returnIntent.putExtra("dateResult", fullDate);
                returnIntent.putExtra("categoryResult", category);
                returnIntent.putExtra("dayDude", day);
                returnIntent.putExtra("monthDude", month);
                returnIntent.putExtra("timeDude", time);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }
        });
        Intent intent = getIntent();
        String title = intent.getStringExtra("editTitle");
        String text = intent.getStringExtra("editText");
        String category = intent.getStringExtra("editCategory");
        String day = intent.getStringExtra("dayResult");
        String month = intent.getStringExtra("monthResult");
        String time = intent.getStringExtra("timeResult");
        userInputTitle.setText(title);
        userInputText.setText(text);
        userInputcategory.setText(category);
        userInputDay.setText(day);
        userInputMonth.setText(month);
        userInputTime.setText(time);
    }

    public void btnClick() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, SELECTED_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case SELECTED_PICTURE:
                if(resultCode ==RESULT_OK) {
                    Uri uri = data.getData();
                    String[]projection={MediaStore.Images.Media.DATA};

                    Cursor cursor=getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath=cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    Drawable d = new BitmapDrawable(yourSelectedImage);

                    iv.setImageURI(uri);


                }
                break;
        }
    }

}
