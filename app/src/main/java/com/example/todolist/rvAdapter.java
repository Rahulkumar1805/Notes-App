package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.databinding.ItemBinding;

import javax.security.auth.callback.Callback;

public class rvAdapter extends ListAdapter<Note,rvAdapter.viewHolder> {

    public rvAdapter(){
        super(CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getTodo().equals(newItem.getTodo());
        }
    };
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Note note =getItem(position);
        holder.binding.Title.setText(note.getTitle());
        holder.binding.ToDo.setText(note.getTodo());

    }

    public Note getNote(int position){
        return getItem(position);
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ItemBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding= ItemBinding.bind(itemView);
        }
    }
}
