package com.abhishek.mvvmnotesapp.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.mvvmnotesapp.Activity.UpdateNotesActivity;
import com.abhishek.mvvmnotesapp.MainActivity;
import com.abhishek.mvvmnotesapp.Model.Notes;
import com.abhishek.mvvmnotesapp.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesViewHolder> {


    MainActivity mainActivity;
    List<Notes> notes;
    List<Notes> allNotesitem;

    public NotesAdapter(MainActivity mainActivity, List<Notes> notes) {

        this.mainActivity = mainActivity;
        this.notes = notes;
        allNotesitem = new ArrayList<>( notes );
    }


    public void searchNotes(List<Notes> filteredName){

        this.notes = filteredName;
        notifyDataSetChanged();

    }



    @NonNull
    @Override
    public notesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new notesViewHolder(LayoutInflater.from(mainActivity).inflate(R.layout.item_notes,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull notesViewHolder holder, int position) {

        Notes note = notes.get(position);

        if(note.notesPriority.equals("1")){
            holder.notesPriority.setBackgroundResource(R.drawable.green_shape);
        }else if(note.notesPriority.equals("2")){
            holder.notesPriority.setBackgroundResource(R.drawable.yellow_shape);
        }else{
            holder.notesPriority.setBackgroundResource(R.drawable.red_shape);
        }
        holder.title.setText(note.notesTitle);
        holder.subtitle.setText(note.notesSubtitle);
        holder.notes.setText(note.notesDate);

        holder.itemView.setOnClickListener(view -> {

            Intent intent = new Intent(mainActivity, UpdateNotesActivity.class);
            intent.putExtra("id",note.id);
            intent.putExtra("title",note.notesTitle);
            intent.putExtra("subtitle",note.notesSubtitle);
            intent.putExtra("priority",note.notesPriority);
            intent.putExtra("notes",note.notes);
            mainActivity.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class notesViewHolder extends RecyclerView.ViewHolder {

        TextView title, subtitle, notes;
        View notesPriority;

        public notesViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.notesTitle);
            subtitle = itemView.findViewById(R.id.notesSubtitle);
            notes = itemView.findViewById(R.id.notesDate);
            notesPriority = itemView.findViewById(R.id.notesPriority);
        }
    }
}
