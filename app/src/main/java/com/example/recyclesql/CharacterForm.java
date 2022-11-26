package com.example.recyclesql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CharacterForm extends AppCompatActivity {

    private EditText char_name, char_power;
    private Button submit;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        char_name = findViewById(R.id.et_name);
        char_power = findViewById(R.id.et_power);
        submit = findViewById(R.id.btn_submit);

        Intent intent = getIntent();
        id = intent.getIntExtra("PRODUCT_ID", -1);

        if (id != -1){
            char_name.setText(intent.getStringExtra("PRODUCT_NAME"));
            char_power.setText(intent.getStringExtra("PRODUCT_POWER"));
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama_karakter = char_name.getText().toString();
                String karakter_power = char_power.getText().toString();
                storeCharacter(nama_karakter, karakter_power);
            }
        });
    }

    private void storeCharacter(String nama_karakter, String karakter_power){
        Intent intent = new Intent();
        intent.putExtra("PRODUCT_ID",id);
        intent.putExtra("PRODUCT_NAME",nama_karakter);
        intent.putExtra("PRODUCT_POWER",karakter_power);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}