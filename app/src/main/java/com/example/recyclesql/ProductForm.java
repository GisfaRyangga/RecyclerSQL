package com.example.recyclesql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProductForm extends AppCompatActivity {

    private EditText nama_barang, harga_barang;
    private Button submit;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_form);

        nama_barang = findViewById(R.id.et_Name);
        harga_barang = findViewById(R.id.et_Price);
        submit = findViewById(R.id.btn_submit);

        Intent intent = getIntent();
        id = intent.getIntExtra("PRODUCT_ID", -1);

        if (id != -1){
            nama_barang.setText(intent.getStringExtra("PRODUCT_NAME"));
            harga_barang.setText(String.valueOf(intent.getIntExtra("PRODUCT_PRICE", 0)));
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product_name = nama_barang.getText().toString();
                int product_price = Integer.parseInt(harga_barang.getText().toString());
                storeProduct(product_name, product_price);
            }
        });
    }

    private void storeProduct(String product_name, int product_price){
        Intent intent = new Intent();
        intent.putExtra("PRODUCT_ID",id);
        intent.putExtra("PRODUCT_NAME",product_name);
        intent.putExtra("PRODUCT_PRICE",product_price);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}