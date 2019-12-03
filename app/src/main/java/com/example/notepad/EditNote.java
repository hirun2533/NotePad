package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class EditNote extends AppCompatActivity {

    private static  final String TAG = "EditNote";
    private EditText NoteTitle, NoteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        NoteTitle = findViewById(R.id.NoteTitle);
        NoteText = findViewById(R.id.NoteText);

        setTitle("Multi Notes");


        Intent intents = getIntent();
        if(intents.hasExtra("Titles") && intents.hasExtra("Texts")){
            String title = intents.getStringExtra("Titles");
            NoteTitle.setText(title);
            String NoteTexts = intents.getStringExtra("Texts");
            NoteText.setText(NoteTexts);

        }
        else {
            NoteTitle.setText("");
            NoteText.setText("");
        }



    }
    @Override
    public void onBackPressed(){

       final String title = NoteTitle.getText().toString();

        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                Intent intent = new Intent();

                intent.putExtra("InputText1", NoteTitle.getText().toString());
                intent.putExtra("InputText2", NoteText.getText().toString());

                setResult(RESULT_OK, intent);
                finish();

            }
        });

        build.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                    finish();
            }
        });

        build.setMessage("Save note '"+ title + "'?");
        build.setTitle("Your note is not saved!");

        AlertDialog alert = build.create();
        alert.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.save_activity, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        Intent intent = new Intent();
        intent.putExtra("InputText1", NoteTitle.getText().toString());
        intent.putExtra("InputText2", NoteText.getText().toString());

        setResult(RESULT_OK, intent);
        finish();

        return true;
    }
}
