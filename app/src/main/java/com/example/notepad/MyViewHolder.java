package com.example.notepad;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class MyViewHolder extends RecyclerView.ViewHolder {
     TextView title;
     TextView date;
     TextView NoteText;


     MyViewHolder(View view){
         super(view);
         title = view.findViewById(R.id.title);
         date = view.findViewById(R.id.date);
         NoteText = view.findViewById(R.id.NoteText);

     }


}
