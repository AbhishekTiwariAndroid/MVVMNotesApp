package com.abhishek.mvvmnotesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.abhishek.mvvmnotesapp.Activity.InsertNotesActivity;
import com.abhishek.mvvmnotesapp.Adapter.NotesAdapter;
import com.abhishek.mvvmnotesapp.Model.Notes;
import com.abhishek.mvvmnotesapp.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    FloatingActionButton newNotesBtn;
    NotesViewModel notesViewModel;
    RecyclerView notesRecycler;
    NotesAdapter adapter;

    TextView nofilter, hightolow, lowtohigh;
    List<Notes> filternotesalllist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesRecycler = findViewById(R.id.notesRecycler);

        newNotesBtn = findViewById(R.id.newNotesBtn);


        nofilter = findViewById(R.id.nofilter);
        hightolow = findViewById(R.id.hightolow);
        lowtohigh = findViewById(R.id.lowtohigh);

        nofilter.setBackgroundResource(R.drawable.filter_selected_shape);


        nofilter.setOnClickListener(view -> {
            loadData(0);
            hightolow.setBackgroundResource(R.drawable.filter_un_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_un_shape);
            nofilter.setBackgroundResource(R.drawable.filter_selected_shape);

        });


        hightolow.setOnClickListener(view -> {
            loadData(1);
            hightolow.setBackgroundResource(R.drawable.filter_selected_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_un_shape);
            nofilter.setBackgroundResource(R.drawable.filter_un_shape);

        });


        lowtohigh.setOnClickListener(view -> {
            loadData(2);
            hightolow.setBackgroundResource(R.drawable.filter_un_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_selected_shape);
            nofilter.setBackgroundResource(R.drawable.filter_un_shape);

        });


        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        newNotesBtn.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, InsertNotesActivity.class));
        });


//        notesViewModel.getallNotes.observe(this,notes -> {
//
//            notesRecycler.setLayoutManager(new GridLayoutManager(this,2));
//            adapter = new NotesAdapter(MainActivity.this,notes);
//            notesRecycler.setAdapter(adapter);
//
//        });

        notesViewModel.getallNotes.observe(this, new Observer<List<Notes>>( ) {
            @Override
            public void onChanged(List<Notes> notes) {
                setAdapter(notes);
                filternotesalllist = notes;
            }
        });


    }

    private void loadData(int i) {

        if (i == 0) {

            notesViewModel.getallNotes.observe(this, new Observer<List<Notes>>( ) {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filternotesalllist = notes;
                }
            });

        } else if (i == 1) {
            notesViewModel.hightolow.observe(this, new Observer<List<Notes>>( ) {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filternotesalllist = notes;
                }
            });
        } else if (i == 2) {

            notesViewModel.lowtohigh.observe(this, new Observer<List<Notes>>( ) {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filternotesalllist = notes;
                }
            });


        }
    }


    public void setAdapter(List<Notes> notes) {
        notesRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new NotesAdapter(MainActivity.this, notes);
        notesRecycler.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater( ).inflate(R.menu.search_notes, menu);

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);

        SearchView searchView = (SearchView) menuItem.getActionView( );
        searchView.setQueryHint("Search Notes here...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener( ) {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                NotesFilter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void NotesFilter(String s) {

//        Log.e("@@@@" ,"NotesFilter: " +s);

        ArrayList<Notes> FilterNames = new ArrayList<>( );

        for (Notes notes : this.filternotesalllist) {

            if (notes.notesTitle.contains(s) || notes.notesSubtitle.contains(s)) {

                FilterNames.add(notes);
            }
        }

        this.adapter.searchNotes(FilterNames);


    }
}