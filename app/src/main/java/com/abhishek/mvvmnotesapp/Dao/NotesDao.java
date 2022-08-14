package com.abhishek.mvvmnotesapp.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.abhishek.mvvmnotesapp.Model.Notes;

import java.util.List;

@androidx.room.Dao
public interface NotesDao {

    @Query("SELECT * FROM Notes_Database")
    LiveData<List<Notes>> getallNotes();


    @Query("SELECT * FROM Notes_Database ORDER BY notes_Priority DESC")
    LiveData<List<Notes>> highToLow();


    @Query("SELECT * FROM Notes_Database ORDER BY notes_Priority ASC")
    LiveData<List<Notes>> lowToHigh();



    @Insert
    void insertNotes(Notes... notes);

    @Query("DELETE FROM Notes_Database WHERE id=:id")
    void deleteNotes(int id);

    @Update
    void updateNotes(Notes notes);




}
