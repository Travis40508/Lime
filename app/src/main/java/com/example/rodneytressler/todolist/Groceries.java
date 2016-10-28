package com.example.rodneytressler.todolist;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
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

public class Groceries extends AppCompatActivity {
    private Button groceriesAddButton;


    ArrayList<groceryListItem> arrayOfTasks = new ArrayList<groceryListItem>();
    String filename = "TodoItemsCool";
    Gson gson = new Gson();
    List<groceryListItem> todos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceries);
        setupNotes();
        final CustomAdapter adapter = new CustomAdapter(this, arrayOfTasks);
        ListView groceryListView = (ListView) findViewById(R.id.groceries_list_full_list);
        groceryListView.setAdapter(adapter);

        groceryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                groceryListItem item = arrayOfTasks.get(position);
                Intent detailScreen = new Intent(getApplicationContext(), userInput.class);
                detailScreen.putExtra("editTitle", item.getTitle());
                detailScreen.putExtra("editText", item.getText());
                detailScreen.putExtra("editCategory", item.getCategory());
                startActivityForResult(detailScreen, 1);
                adapter.remove(arrayOfTasks.get(position));
                adapter.notifyDataSetChanged();

            }
        });
        groceryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Groceries.this);
                alertBuilder.setTitle("Task Completed?");
                alertBuilder.setMessage("You sure?");
                alertBuilder.setNegativeButton("Cancel", null);
                alertBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        groceryListItem note = arrayOfTasks.get(position);
                        arrayOfTasks.remove(position);
                        adapter.notifyDataSetChanged();
                        writeTodos();
                    }
                });
                alertBuilder.create().show();
                return true;
            }
        });
        groceriesAddButton = (Button) findViewById(R.id.groceries_button_add);
        groceriesAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput();
            }
        });

    }

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
            groceryListItem[] todoList = gson.fromJson(todosText, groceryListItem[].class);
            todos = Arrays.asList(todoList);
        }
    }

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


    public void userInput() {
        Intent intent = new Intent(this, userInput.class);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent view) {
        super.onActivityResult(requestCode, resultCode, view);
        getIntent();
        if (resultCode == RESULT_OK) {
            final CustomAdapter adapter = new CustomAdapter(this, arrayOfTasks);
            ListView groceryListView = (ListView) findViewById(R.id.groceries_list_full_list);
            groceryListView.setAdapter(adapter);
            String awesomeTitle = view.getStringExtra("titleResult");
            String awesomeText = view.getStringExtra("textResult");
            String awesomeDate = view.getStringExtra("dateResult");
            String awesomeCategory = view.getStringExtra("categoryResult");
            String awesomeDay = view.getStringExtra("dayDude");
            String awesomeMonth = view.getStringExtra("monthDude");
            String awesomeTime = view.getStringExtra("timeDude");
            Date date = new Date();
            groceryListItem item1 = new groceryListItem(awesomeTitle, awesomeText, awesomeDate, awesomeCategory, date, awesomeDay, awesomeMonth, awesomeTime);
            adapter.add(item1);
            Collections.sort(arrayOfTasks);


            groceryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    groceryListItem item = arrayOfTasks.get(position);
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
            groceryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Groceries.this);
                    alertBuilder.setTitle("Task Completed?");
                    alertBuilder.setMessage("You sure?");
                    alertBuilder.setNegativeButton("Cancel", null);
                    alertBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            groceryListItem note = arrayOfTasks.get(position);
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

    public void setupNotes() {
        arrayOfTasks = new ArrayList<>();

        File filesDir = this.getFilesDir();
        File todoFile = new File(filesDir + File.separator + filename);
        if (todoFile.exists()) {
            readTodos(todoFile);

            for (groceryListItem todo : todos) {
                arrayOfTasks.add(todo);


            }
        } else {
            // If the file doesn't exist, create it
//            Date date = new Date();
//            todos.add(new groceryListItem("todo 1","A todo", "thing", "thing", date));
//            todos.add(new groceryListItem("todo 2","Another todo", "thing", "thing", date));
//            todos.add(new groceryListItem("todo 3","One more todo", "thing", "thing", date));

            writeTodos();
        }
    }

}





