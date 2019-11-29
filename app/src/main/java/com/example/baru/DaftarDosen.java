package com.example.baru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DaftarDosen extends AppCompatActivity {
    private ArrayList<DataDosen> mahasiswaArrayList;
    DataDosenService dataDosenService;
    public static DaftarDosen ma;
    public boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar_dosen);
        mahasiswaArrayList = new ArrayList<>();
        dataDosenService = RetrofitClient.getRetrofitInstance()
                .create(DataDosenService.class);
        ma=this;
        getAllDataDosen();
        recyclerView = findViewById(R.id.rvdd);

        Call<ArrayList<Dosen>> call = dataDosenService.getDosenAll("72170112");// memanggil data yang sudah ada
        call.enqueue(new Callback<ArrayList<Dosen>>() {
            @Override
            public void onResponse(Call<ArrayList<Dosen>> call, Response<ArrayList<Dosen>> response) {
                mahasiswaArrayList = response.body();
                mahasiswaAdapter = new DosenAdapter(response.body());

                //for(Dosen dosen:response.body())
                //{
                //mahasiswaArrayList.add(new Dosen(dosen.getNidn(),dosen.getNama(),dosen.getGelar(),dosen.getEmail(),dosen.getAlamat()));
                // mahasiswaArrayList = response.body();
                //mahasiswaAdapter = new DosenAdapter(dosen);
                    /*System.out.println("Nama :"+dosen.getNama());
                    System.out.println("NIDN :"+dosen.getNidn());
                    System.out.println(mahasiswaArrayList);*/
                // }





            }

            @Override
            public void onFailure(Call<ArrayList<DataDosen>> call, Throwable t) {
                Toast.makeText(DaftarDosen.this,"Something wrong....",Toast.LENGTH_LONG).show();
                //System.out.println(t.get);
            }
        });
        registerForContextMenu(recyclerView);

    }

    public void getAllDataDosen()
    {

    }

    private void addData() {
        //mahasiswaArrayList = new ArrayList<>();
        mahasiswaArrayList.add(new Dosen("NIDN - NAMA DOSEN","Gelar","email","alamat"));
        mahasiswaArrayList.add(new Dosen("NIDN - NAMA DOSEN","Gelar","email","alamat"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.utama,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(DaftarDosen.this, InsertDosen.class);
        switch (item.getItemId()){
            case R.id.item1:
                //update = false;
                startActivity(intent);
                return true;
            case R.id.item2:
                intent.putExtra("update",true);
                intent.putExtra("nama","sapa");
                //update = true;
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        DataDosen dosen = mahasiswaArrayList.get(item.getGroupId());
        if (item.getTitle() == "Ubah data dosen "){
            Intent intent = new Intent(DaftarDosen.this,InsertDosen.class);
            intent.putExtra("id_dosen",DataDosen.getId());
            intent.putExtra("nama_dosen",DataDosen.getNama());
            intent.putExtra("nidn",DataDosen.getNidn());
            intent.putExtra("alamat",DataDosen.getAlamat());
            intent.putExtra("email",DataDosen.getEmail());
            intent.putExtra("gelar",DataDosen.getGelar());
            intent.putExtra("foto",DataDosen.getFoto());
            intent.putExtra("is_update",true);
            startActivity(intent);
        }else if (item.getTitle() == "Delete data dosen ") {
            DataDosenService service = RetrofitClient.getRetrofitInstance().create(DataDosenService.class);
            Call<Dosen> call = service.delDosen(
                    "72170112",dosen.getId());// memanggil data yang sudah ada
            call.enqueue(new Callback<Dosen>() {
                @Override
                public void onResponse(Call<DataDosen> call, Response<DataDosen> response) {
                    //Toast.makeText(DaftarDosen.this, "Behasil Kehapus !!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DaftarDosen.this, DaftarDosen.class);
                    startActivity(intent);

                }

                @Override
                public void onFailure(Call<DataDosen> call, Throwable t) {
                    //progressDialog.dismiss();
                    Toast.makeText(DaftarDosen.this, "Failed...", Toast.LENGTH_LONG).show();
                }

            });
        }

        return super.onContextItemSelected(item);
    }
}{
}
