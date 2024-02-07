package com.example.todolist;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepo {

    private NoteDao noteDao;
    private LiveData<List<Note>> noteList;

    public NoteRepo(Application application){
        NoteDatabase noteDatabase =NoteDatabase.getInstance(application);
        noteDao =noteDatabase.noteDao();
        noteList= noteDao.getAllData();
    }

    public void insertData(Note note){new insertTask(noteDao).execute(note);}
    public void updateData(Note note){new updateTask(noteDao).execute(note);}
    public void deleteData(Note note){new deleteTask(noteDao).execute(note);}
    public LiveData<List<Note>> getAllData(){
        return noteList;
    }


    private static class insertTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;
        public insertTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class deleteTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;
        public deleteTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class updateTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;
        public updateTask(NoteDao noteDao) {

            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
}
