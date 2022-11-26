package com.example.recyclesql;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Character> bleach_char = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();
                        int id = data.getIntExtra("CHARACTER_ID", -1);

                        Character character = new Character();
                        character.setId(id);
                        character.setName(data.getStringExtra("CHARACTER_NAME"));
                        character.setPower(data.getStringExtra("CHARACTER_POWER"));

                        if(id != -1){
                            databaseHelper.updateCharacter(character);
                        } else {
                            databaseHelper.insertCharacter(character);
                        }
                        Toast.makeText(MainActivity.this, "Data karakter berhasil disimpan", Toast.LENGTH_SHORT).show();
                        bleach_char.clear();
                        getAllCharacters();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.rv_characters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new RecyclerViewAdapter(this, bleach_char);
        getAllCharacters();
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnClickListener() {
            @Override
            public void onBtnClick(Character character, View v) {
                int id = v.getId();
                if (id == R.id.btn_update){
                    Intent intent = new Intent(v.getContext(), CharacterForm.class);
                    intent.putExtra("CHARACTER_ID", character.getId());
                    intent.putExtra("CHARACTER_NAME", character.getName());
                    intent.putExtra("CHARACTER_POWER", character.getPower());
                    launcher.launch(intent);
                } else if (id == R.id.btn_delete){
                    databaseHelper.deleteCharacter(character);
                    bleach_char.clear();
                    getAllCharacters();
                }
            }

            @Override
            public void onItemClick(Character character) {
                Toast.makeText(MainActivity.this, "Karakter berhasil disimpan", Toast.LENGTH_SHORT).show();
            }
        });

        ExtendedFloatingActionButton btn_add;
        btn_add = findViewById(R.id.add_char);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CharacterForm.class);
                launcher.launch(intent);
            }
        });
    }

    private void getAllCharacters(){
        bleach_char.addAll(databaseHelper.getAllProducts());
        recyclerViewAdapter.notifyDataSetChanged();
    }
}