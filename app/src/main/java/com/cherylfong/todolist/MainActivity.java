package com.cherylfong.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView listView;

    static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        listView = findViewById(R.id.listView);
        listView.setAdapter(itemsAdapter);

        // dummy data
        items.add("fish");
        items.add("veggies");
        items.add("milk");
        items.add("fruits");

        setListViewListener();

    }

    public void onAddItem(View view){

        EditText editTextNewItem = findViewById(R.id.edit_newItem);
        String newItemString = editTextNewItem.getText().toString();
        itemsAdapter.add(newItemString);

        // save items to the file
        writeItems();
        editTextNewItem.setText(""); // clear
        Toast.makeText(getApplicationContext(), "New item added.", Toast.LENGTH_SHORT).show();
    }

    private void setListViewListener(){

        Log.d(LOG_TAG, "setListViewListener");

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d(LOG_TAG, "onItemLongClick remove item " + position );
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();

                writeItems();
                return true;
            }
        });
    }


    //
    // Cached files (internal memory of the application) is for persistence !
    //

    // return a file object from a file named "todo.txt"
    private File getDataFile(){

        // under 1MB of data
        return new File(getCacheDir(), "todo.txt");
    }


    // save items from file
    private void readItems(){

        try{

            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));

        }catch (IOException e){

            Log.e(LOG_TAG, "ERROR read from file:" + e.getMessage());
            items = new ArrayList<>();
        }
    }

    // write items to a file name "todo.txt
    private void writeItems(){

        try{
            FileUtils.writeLines(getDataFile(), items);

        }catch (IOException e){
            Log.e(LOG_TAG, "ERROR write to file:" + e.getMessage());
        }
    }
}
