package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.example.todolist.databinding.ActivityDataInsertBinding;

import java.util.Objects;

public class DataInsertActivity extends AppCompatActivity {


    ActivityDataInsertBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityDataInsertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String type= getIntent().getStringExtra("type");
        if(type.equals("update")){
            setTitle("Update");
            binding.title.setText(getIntent().getStringExtra("title"));
            binding.desc.setText(getIntent().getStringExtra("desc"));
            int id= getIntent().getIntExtra("id",0);
            binding.add.setText("Update");
            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("title", binding.title.getText().toString());
                    intent.putExtra("desc",binding.desc.getText().toString());
                    intent.putExtra("id",id);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });


        }else{
            setTitle("Add new Task/Note");
            binding.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("title", binding.title.getText().toString());
                    intent.putExtra("desc",binding.desc.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }



    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(DataInsertActivity.this,MainActivity.class));
    }
}