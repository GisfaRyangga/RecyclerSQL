package com.example.recyclesql;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Product> values = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();
                        int id = data.getIntExtra("PRODUCT_ID", -1);

                        Product product = new Product();
                        product.setId(id);
                        product.setName(data.getStringExtra("PRODUCT_NAME"));
                        product.setPrice(data.getIntExtra("PRODUCT_PRICE", 0));

                        if(id != -1){
                            databaseHelper.updateProduct(product);
                        } else {
                            databaseHelper.insertProductItem(product);
                        }
                        Toast.makeText(MainActivity.this, "Produk berhasil disimpan", Toast.LENGTH_SHORT).show();
                        values.clear();
                        getAllProducts();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.rv_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new RecyclerViewAdapter(this, values);
        getAllProducts();
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnClickListener() {
            @Override
            public void onBtnClick(Product product, View v) {
                int id = v.getId();
                if (id == R.id.btn_update){
                    Intent intent = new Intent(v.getContext(), ProductForm.class);
                    intent.putExtra("PRODUCT_ID", product.getId());
                    intent.putExtra("PRODUCT_NAME", product.getName());
                    intent.putExtra("PRODUCT_PRICE", product.getPrice());
                    launcher.launch(intent);
                } else if (id == R.id.btn_delete){
                    databaseHelper.deleteProduct(product);
                    values.clear();
                    getAllProducts();
                }
            }

            @Override
            public void onItemClick(Product product) {
                Toast.makeText(MainActivity.this, "Produk berhasil disimpan", Toast.LENGTH_SHORT).show();
            }
        });

        ExtendedFloatingActionButton efab;
        efab = findViewById(R.id.tambah_item);
        efab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductForm.class);
                launcher.launch(intent);
            }
        });
    }

    private void getAllProducts(){
        values.addAll(databaseHelper.getAllProducts());
        recyclerViewAdapter.notifyDataSetChanged();
    }
}