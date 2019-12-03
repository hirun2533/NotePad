package com.example.notepad;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final String TAG = "NoteAdapter";
    private ArrayList<Note> noteList;
    private MainActivity mainAct;

    NoteAdapter(ArrayList<Note> note, MainActivity ma){
        this.noteList = note;
        mainAct = ma;

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: FILLING VIEW HOLDER note ");

        Note note = noteList.get(position);
        String NoteText;
        NoteText = note.getNoteText();
        if(NoteText.length() > 80){ NoteText = note.getNoteText().substring(0, 79) + "..."; }

        Date date = new Date(note.getDate());
        DateFormat formatter = new SimpleDateFormat("EE MMM dd, hh:mm aa");
        String timeCurrent = formatter.format(date);
        holder.title.setText(note.getTitle());
        holder.NoteText.setText(NoteText);
        holder.date.setText(timeCurrent);

    }


    @Override
    public int getItemCount() {
        return noteList.size();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType){

        Log.d(TAG, "onCreateViewHolder: MAKING NEW MyViewHolder");

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_item, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new MyViewHolder(itemView);

    }


}
