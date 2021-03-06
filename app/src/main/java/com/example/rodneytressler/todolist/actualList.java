package com.example.rodneytressler.todolist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

//actualList was initially intended to be a Category until it was decided that the only difference in each class
// was name, so a naming block was more efficient.
public class actualList extends AppCompatActivity {
    private Button taskAddButton;

// Declare fields to be used throughout the class.
    ArrayList<actualListItem> arrayOfTasks = new ArrayList<actualListItem>();
    String filename = "TodoItemsCool";
    Gson gson = new Gson();
    List<actualListItem> todos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //run setupNotes, which will both read and write files to allow for persistence.
        setupNotes();

        //sets Custom Adapter so that the item layout can be placed into a List View on the main list.
        final CustomAdapter adapter = new CustomAdapter(this, arrayOfTasks);
        ListView groceryListView = (ListView) findViewById(R.id.actual_list_full_list);
        groceryListView.setAdapter(adapter);


        //Sets up each item in the List View to, on click, bring up another intent to allow editing.
        groceryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                actualListItem item = arrayOfTasks.get(position);
                Intent detailScreen = new Intent(getApplicationContext(), userInput.class);
                detailScreen.putExtra("editTitle", item.getTitle());
                detailScreen.putExtra("editText", item.getText());
                detailScreen.putExtra("editCategory", item.getCategory());
                startActivityForResult(detailScreen, 1);
                adapter.remove(arrayOfTasks.get(position));
                adapter.notifyDataSetChanged();

            }
        });


        //Allows each item in the List View, on long click, to be completed/deleted.
        groceryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(actualList.this);
                alertBuilder.setTitle("Task Completed?");
                alertBuilder.setMessage("You sure?");
                alertBuilder.setNegativeButton("Cancel", null);
                alertBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        actualListItem note = arrayOfTasks.get(position);
                        arrayOfTasks.remove(position);
                        adapter.notifyDataSetChanged();
                        writeTodos();
                    }
                });
                alertBuilder.create().show();
                return true;
            }
        });

        //Add task button, brings up the Edit Text user input Activity.
        taskAddButton = (Button) findViewById(R.id.groceries_button_add);
        taskAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput();
            }
        });

    }


    //Reads all of the items in the list so that they can be brought up upon app creation.
    private void readTodos(File todoFile) {
        FileInputStream inputStream = null;
        String todosText = "";
        try {
            inputStream = openFileInput(todoFile.getName());
            byte[] input = new byte[inputStream.available()];
            while (inputStream.read(input) != -1) {
            }
            todosText = new String(input);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            actualListItem[] todoList = gson.fromJson(todosText, actualListItem[].class);
            todos = Arrays.asList(todoList);
        }
    }


    //Writes all of the items into a file to be read by readTodos on create.
    private void writeTodos() {
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);

            String json = gson.toJson(arrayOfTasks);
            byte[] bytes = json.getBytes();
            outputStream.write(bytes);

            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (Exception ignored) {
            }
        }
    }



    //Method that starts the input Activity with the results being returned to the list to be posted.
    public void userInput() {
        Intent intent = new Intent(this, userInput.class);
        startActivityForResult(intent, 1);
    }


    //Instructions for the information that's returned on the User Input activity.
    //Makes sure that the information is added to the adapter and List View.
    protected void onActivityResult(int requestCode, int resultCode, Intent view) {
        super.onActivityResult(requestCode, resultCode, view);
        getIntent();
        if (resultCode == RESULT_OK) {
            final CustomAdapter adapter = new CustomAdapter(this, arrayOfTasks);
            ListView groceryListView = (ListView) findViewById(R.id.actual_list_full_list);
            groceryListView.setAdapter(adapter);
            String awesomeTitle = view.getStringExtra("titleResult");
            String awesomeText = view.getStringExtra("textResult");
            String awesomeDate = view.getStringExtra("dateResult");
            String awesomeCategory = view.getStringExtra("categoryResult");
            String awesomeDay = view.getStringExtra("dayDude");
            String awesomeMonth = view.getStringExtra("monthDude");
            String awesomeTime = view.getStringExtra("timeDude");
            Date date = new Date();
            actualListItem item1 = new actualListItem(awesomeTitle, awesomeText, awesomeDate, awesomeCategory, date, awesomeDay, awesomeMonth, awesomeTime);
            adapter.add(item1);
            Collections.sort(arrayOfTasks);

            //Sets short click to allow editing and for the information to carry over to the user input Activity.
            groceryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    actualListItem item = arrayOfTasks.get(position);
                    Intent detailScreen = new Intent(getApplicationContext(), userInput.class);
                    detailScreen.putExtra("editTitle", item.getTitle());
                    detailScreen.putExtra("editText", item.getText());
                    detailScreen.putExtra("editCategory", item.getCategory());
                    detailScreen.putExtra("dayResult", item.getDay());
                    detailScreen.putExtra("monthResult", item.getMonth());
                    detailScreen.putExtra("timeResult", item.getTime());
                    startActivityForResult(detailScreen, 1);
                    adapter.remove(arrayOfTasks.get(position));
                    adapter.notifyDataSetChanged();

                }
            });

            //Allows for long-click to give the ability to delete/complete Tasks.
            groceryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(actualList.this);
                    alertBuilder.setTitle("Task Completed?");
                    alertBuilder.setMessage("You sure?");
                    alertBuilder.setNegativeButton("Cancel", null);
                    alertBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            actualListItem note = arrayOfTasks.get(position);
                            deleteFile("##" + note.getTitle());
                            arrayOfTasks.remove(position);
                            adapter.notifyDataSetChanged();
                            writeTodos();
                        }
                    });
                    alertBuilder.create().show();
                    return true;
                }
            });
        } else {
            Toast.makeText(this, "Add Cancelled", Toast.LENGTH_SHORT).show();
        }
        writeTodos();
    }

    //Begins the initiation of Persistence within the app. Calls for the reading and writing.
    public void setupNotes() {
        arrayOfTasks = new ArrayList<>();

        File filesDir = this.getFilesDir();
        File todoFile = new File(filesDir + File.separator + filename);
        if (todoFile.exists()) {
            readTodos(todoFile);

            for (actualListItem todo : todos) {
                arrayOfTasks.add(todo);


            }
        } else {
            // If the file doesn't exist, create it. Think it looks better without initial tasks, though.
//            Date date = new Date();
//            todos.add(new actualListItem("todo 1","A todo", "thing", "thing", date));
//            todos.add(new actualListItem("todo 2","Another todo", "thing", "thing", date));
//            todos.add(new actualListItem("todo 3","One more todo", "thing", "thing", date));

            writeTodos();
        }
    }

}





