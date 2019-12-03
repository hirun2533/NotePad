    package com.example.notepad;

    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AlertDialog;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.TextView;
    import android.widget.Toast;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.io.BufferedInputStream;
    import java.io.BufferedReader;
    import java.io.FileInputStream;
    import java.io.FileNotFoundException;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.io.UnsupportedEncodingException;
    import java.nio.charset.StandardCharsets;
    import java.util.ArrayList;
    import java.util.List;



    public class MainActivity extends AppCompatActivity
            implements View.OnClickListener,View.OnLongClickListener {

        private static final int CODE_FOR_NOTE_ACTIVITY = 222;
        private ArrayList<Note> noteList = new ArrayList<>();
        private RecyclerView recyclerView;
        private NoteAdapter mAdapter;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            recyclerView = findViewById(R.id.recycler);
            mAdapter = new NoteAdapter(noteList, this);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            doRead();
            setTitle("Multi Notes(" + noteList.size() + ")");

        }

        @Override
        public void onClick(View v) {

            int pos = recyclerView.getChildAdapterPosition(v);
            Note select = noteList.get(pos);

            Intent update = new Intent(this, EditNote.class);
            update.putExtra("Titles", select.getTitle());
            update.putExtra("Texts", select.getNoteText());
            setResult(RESULT_OK, update);
            startActivityForResult(update, CODE_FOR_NOTE_ACTIVITY);

            noteList.remove(pos);
            mAdapter.notifyDataSetChanged();

        }


        private void doRead() {

            try {
                InputStream in = getApplicationContext().openFileInput("Note.json");
                BufferedReader read = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                StringBuilder builder = new StringBuilder();
                String receiveString;
                while ((receiveString = read.readLine()) != null) {
                    builder.append(receiveString);
                }
                read.close();

                JSONArray jsonArray = new JSONArray(builder.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String title = jsonObject.getString("TitleText");
                    String contents = jsonObject.getString("ContentText");
                    long time = jsonObject.getLong("Date");
                    Note n = new Note(title, contents, time);
                    noteList.add(n);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.menu_activity, menu);
            return true;
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.info:
                    Intent intent = new Intent(this, aboutActivity.class);
                    startActivity(intent);
                    Toast.makeText(this, "You want to see the information", Toast.LENGTH_SHORT).show();

                    break;

                case R.id.addnote:
                    Intent intent2 = new Intent(this, EditNote.class);
                    startActivityForResult(intent2, CODE_FOR_NOTE_ACTIVITY);
                    Toast.makeText(this, "You want to add note", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;

            }
            return true;
        }


        @Override
        public void onBackPressed() {

            super.onBackPressed();
        }

        @Override
        public boolean onLongClick(View v) {
            int pos = recyclerView.getChildAdapterPosition(v);
            final Note select = noteList.get(pos);

            final String title = select.getTitle();
            AlertDialog.Builder build = new AlertDialog.Builder(this);
            build.setPositiveButton("YES", new DialogInterface.OnClickListener() {


                @Override
                public void onClick(DialogInterface dialog, int id) {

                    for (int i = 0; i < noteList.size(); i++) {
                        if (title.equals(noteList.get(i).getTitle())) {
                            noteList.remove(i);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    setTitle("Multi Notes(" + noteList.size() + ")");

                }
            });

            build.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int id) {

                }
            });

            build.setMessage("Delete Note '" + title + "'?");
            AlertDialog alert = build.create();
            alert.show();

            return true;

        }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent){
                if(requestCode == CODE_FOR_NOTE_ACTIVITY){

                    if(resultCode == RESULT_OK){
                        String TextTitle = intent.getStringExtra("InputText1");
                        String TextContent = intent.getStringExtra("InputText2");

                        long time = System.currentTimeMillis();
                        Note newNote = new Note(TextTitle,TextContent,time);
                        noteList.add(0, newNote);
                        mAdapter.notifyDataSetChanged();


                        if(TextTitle.equals("")){
                            noteList.remove(newNote);
                            Toast.makeText(this,"Please put the title!", Toast.LENGTH_SHORT).show();
                        }

                        setTitle("Multi Notes(" + noteList.size() + ")");
                    }
                }

                super.onActivityResult(requestCode,resultCode,intent);
        }



        public void doNote() throws IOException, JSONException {
            FileOutputStream file = getApplicationContext().openFileOutput("Note.json", Context.MODE_PRIVATE);
            JSONArray jsonArray = new JSONArray();


            for(Note note : noteList){
                try{

                    JSONObject NoteJson = new JSONObject();
                    NoteJson.put("TitleText", note.getTitle());
                    NoteJson.put("ContentText", note.getNoteText());
                    NoteJson.put("Date", note.getDate());
                    jsonArray.put(NoteJson);


                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }

            String jsonText = jsonArray.toString();

            file.write(jsonText.getBytes());
            file.close();

        }


        protected void onPause(){
                super.onPause();

                try{
                    doNote();
                }
                  catch(IOException | JSONException e){

            }
        }

    }
