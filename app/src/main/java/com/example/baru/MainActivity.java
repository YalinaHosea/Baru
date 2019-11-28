package com.example.baru;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    DataDosenService dataDosenService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataDosenService = RetrofitClient.getRetrofitInstance()
                .create(DataDosenService.class);
        getAllDataDosen();
    }
    private void getAllDataDosen()
    {
        Call<List<DataDosen>> call = dataDosenService.getDosenAll( "1");
        call.enqueue(new Callback<List<DataDosen>>() {
            @Override
            public void onResponse(Call<List<DataDosen>> call, Response<List<DataDosen>> response) {
                for(DataDosen dataDosen:response.body())
                {
                    System.out.println("Nama : "+dataDosen.getNama());
                    System.out.println("Nidn : "+dataDosen.getNidn());
                }
            }

            @Override
            public void onFailure(Call<List<DataDosen>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something wrong.....",
                        Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());
            }
        });

    }
}
