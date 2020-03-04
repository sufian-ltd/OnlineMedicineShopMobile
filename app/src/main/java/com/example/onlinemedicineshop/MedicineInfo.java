package com.example.onlinemedicineshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicineInfo extends AppCompatActivity {

    ListView listView;
    List<HealthTipsEntity> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_info);
        listView=findViewById(R.id.listView);
        getAllMedicineInfo();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MedicineInfo.this,MedicineInfoDetailActivity.class);
                intent.putExtra("tip",list.get(i));
                startActivity(intent);
            }
        });
    }
    private void getAllMedicineInfo() {
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<HealthTipsEntity>> call=apiInterface.getAllMedicineInfo();
        call.enqueue(new Callback<List<HealthTipsEntity>>() {
            @Override
            public void onResponse(Call<List<HealthTipsEntity>> call, Response<List<HealthTipsEntity>> response) {
                list=response.body();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<HealthTipsEntity>> call, Throwable t) {
                Toast.makeText(MedicineInfo.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setAdapter(){
        TipsAdapter adapter=new TipsAdapter(this,list);
        listView.setAdapter(adapter);
    }

}
