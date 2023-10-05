package com.example.crudapp.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudapp.R;
import com.example.crudapp.common.AddNew;
import com.example.crudapp.database.DbHelper;
import com.example.crudapp.models.LlApiModel;
import com.example.crudapp.tasks.TaskThree;
import com.example.crudapp.tasks.TaskThreeAddEdit;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LlApiAdapter extends RecyclerView.Adapter<LlApiAdapter.ViewHolder> {

    Context context;
    private ArrayList<LlApiModel> llApiDataList;

    public LlApiAdapter(Context context, ArrayList<LlApiModel> llApiDataList) {
        this.context = context;
        this.llApiDataList = llApiDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_ll_api_row, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LlApiModel data = llApiDataList.get(position);
        holder.tvId.setText(String.valueOf(data.getId()));
        holder.tvName.setText(data.getName());
        holder.tvFName.setText(data.getfName());
        String gender = data.getGender();
        if (gender.equals("M")) {
            holder.tvGender.setText("Male");
        }else {
            holder.tvGender.setText("Female");
        }

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, TaskThreeAddEdit.class);
                i.putExtra("id", data.getId());
                i.putExtra("name", data.getName());
                i.putExtra("fName", data.getfName());
                i.putExtra("gender", gender);
                i.putExtra("title", "Update Record");
                i.putExtra("btnText", "Update");
                context.startActivity(i);

            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDeleteDialog = new AlertDialog.Builder(context);
                alertDeleteDialog.setTitle("Delete ");
                alertDeleteDialog.setIcon(R.drawable.ic_baseline_delete_24);
                alertDeleteDialog.setMessage("Are you sure you want to delete  this book?");

                alertDeleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        int id = data.getId();

                        try {
                            final String apiUrl = "http://sms1.logicslabs.com/temp/testDelete?id="+id;
                            URL url = new URL(apiUrl);
                            HttpURLConnection httpURLConnection1 = (HttpURLConnection) url.openConnection();
//            Log.e("newData" , "value of httpURLConnection1 " + httpURLConnection1);
                            InputStream inputStream1 = httpURLConnection1.getInputStream();
                            BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
                            String line1 = "";
                            String data1 = "";

                            while (line1 != null) {
                                line1 = bufferedReader1.readLine();
                                data1 = data1 + line1;
                            }

//            Log.e("newData" , "value of data " + data1);

                            JSONObject jsonObject = new JSONObject(data1);
                            String data = jsonObject.getString("Data");
                            String status = jsonObject.getString("status");
                            if (status.equals("success")) {
                                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }else{
                                Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                            }

                        }catch (Exception rt){
                            // Log.e("DataItem",  String.valueOf(rt.toString())); // Log the item
                        }

                    }
                });

                alertDeleteDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alertDeleteDialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return llApiDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvId, tvName, tvFName, tvGender;
        Button btnEdit, btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvName = itemView.findViewById(R.id.tv_name);
            tvFName = itemView.findViewById(R.id.tv_fName);
            tvGender = itemView.findViewById(R.id.tv_gender);
            btnEdit = itemView.findViewById(R.id.edit_button);
            btnDelete = itemView.findViewById(R.id.delete_button);

        }
    }

}
