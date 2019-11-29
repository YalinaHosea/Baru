package com.example.baru;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InsertDosen extends AppCompatActivity {
    EditText edtNama, edtNidn, edtAlamat, edtEmail, edtGelar, edtFoto;
    Button btnSimpan, btnBack;
    DataDosenService dataDosenService;
    DaftarDosen a;
    boolean update = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_dosen);

        edtNama = findViewById(R.id.txtNama);
        edtNidn = findViewById(R.id.txtNidn);
        edtAlamat = findViewById(R.id.txtAlamat);
        edtEmail = findViewById(R.id.txtEmail);
        edtGelar = findViewById(R.id.txtGelar);
        edtFoto = findViewById(R.id.txtFoto);

        cek_update();

        ProgressDialog progressDialog;
        dataDosenService = RetrofitClient.getRetrofitInstance()
                .create(DataDosenService.class);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tambah_dosen();
            }
        });
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DaftarDosen.ma.getAllDataDosen();
                finish();
            }
        });
    }
    private void tambah_dosen(){
        Call<Dosen> call;
        if(update) {
            call = dataDosenService.updateDosen("72170168", 164, edtNama.getText().toString(),
                    edtNidn.getText().toString(), edtAlamat.getText().toString(), edtEmail.getText().toString(),
                    edtGelar.getText().toString(), edtFoto.getText().toString());
        }
        else {
            call = dataDosenService.postDosen("72170168", edtNama.getText().toString(),
                    edtNidn.getText().toString(), edtAlamat.getText().toString(), edtEmail.getText().toString(),
                    edtGelar.getText().toString(), edtFoto.getText().toString());
        }
        call.enqueue(new Callback<Dosen>() {
            @Override
            public void onResponse(Call<Dosen> call, Response<Dosen> response) {
                Intent intent = new Intent(InsertDosen.this,DaftarDosen.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Dosen> call, Throwable t) {
                Toast.makeText(InsertDosen.this,"Something wrong....",Toast.LENGTH_LONG).show();
                //System.out.println(t.get);
            }
        });
    }
    void cek_update()
    {
        Bundle extras = getIntent().getExtras();
        if (extras == null){
            return;
        }
        update = extras.getBoolean("update");
        edtNama.setText(extras.getString("nama"));

    }
}
