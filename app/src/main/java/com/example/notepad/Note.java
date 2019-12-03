package com.example.notepad;




public class Note {

    private String title;
    private String NoteText;
    private long date;


   public  Note(String title, String noteText, long date){

        this.title = title;
        this.NoteText = noteText;
        this.date = date;

    }

    public String getTitle() { return title; }

    public String getNoteText() { return NoteText; }

    public long getDate() { return date; }

    public void setTitle(String title) { this.title = title; }

    public void setNoteText(String noteText) { this.NoteText = noteText; }

    public void setDate(long date) { this.date = date; }

    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", NoteText='" + NoteText + '\'' +
                ", date=" + date +
                '}';
    }




}
