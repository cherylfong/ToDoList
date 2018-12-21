package com.cherylfong.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String text = getIntent().getExtras().getString("toDoValue");

        EditText editText = findViewById(R.id.edit_toDoItem);
        editText.setText(text);

        findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = ((EditText)findViewById(R.id.edit_toDoItem)).getText().toString();

                Intent intent = new Intent();

                intent.putExtra("newValue", value);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
