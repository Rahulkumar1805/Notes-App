package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.todolist.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView noData;

    private NoteViewModel noteViewModel;
    ActivityMainBinding binding;
    rvAdapter  adapter;

    LiveData<List<Note>> listnoteslive;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteViewModel= new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(NoteViewModel.class);
        noData=findViewById(R.id.noData);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Find the ImageView using the binding
        noData = binding.noData;

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DataInsertActivity.class);
                intent.putExtra("type","addmode");
                startActivityForResult(intent,1);
            }
        });
        adapter = new rvAdapter();
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setHasFixedSize(true);

        binding.rv.setAdapter(adapter);


        listnoteslive =noteViewModel.getNoteList();
        listnoteslive.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);

                // Check if the list is empty
                if (notes.isEmpty()) {
                    noData.setVisibility(View.VISIBLE); // Set visibility to VISIBLE
                } else {
                    noData.setVisibility(View.GONE); // Set visibility to GONE
                }
                for (Note note : notes) {
                    Log.e("checkk", "Note: " + note.getTitle() + " - " + note.getTodo());
                }

            }

        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction==ItemTouchHelper.RIGHT){
                    noteViewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this,"Task Deleted",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent= new Intent(MainActivity.this,DataInsertActivity.class);
                    intent.putExtra("type","update");
                    intent.putExtra("title",adapter.getNote(viewHolder.getAdapterPosition()).getTitle());
                    intent.putExtra("desc",adapter.getNote(viewHolder.getAdapterPosition()).getTodo());
                    intent.putExtra("id",adapter.getNote(viewHolder.getAdapterPosition()).getId());
                    startActivity(intent);


                }

            }
        }).attachToRecyclerView(binding.rv);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            String title = data.getStringExtra("title");
            String desc = data.getStringExtra("desc");
            Note note = new Note(title, desc);
            noteViewModel.insert(note);

            Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show();
        } else if (requestCode==2) {
            String title = data.getStringExtra("title");
            String desc = data.getStringExtra("desc");
            int id = data.getIntExtra("id", 0);
            Note note = new Note(title, desc);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        listnoteslive=noteViewModel.getNoteList();
        adapter.notifyDataSetChanged();


    }
}