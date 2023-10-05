package com.example.crudapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudapp.R;
import com.example.crudapp.models.ApiCallModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class ApiCallAdapter extends RecyclerView.Adapter<ApiCallAdapter.ViewHolder> {
    Context context;
    private ArrayList<ApiCallModel> ApiDataList;

    public ApiCallAdapter(Context context, ArrayList<ApiCallModel> apiDataList) {
        this.context = context;
        ApiDataList = apiDataList;
    }

    @NonNull
    @Override
    public ApiCallAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_api_call_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApiCallAdapter.ViewHolder holder, int position) {
        ApiCallModel data = ApiDataList.get(position);
        holder.tvName.setText(data.getName());

        String stringDate = data.getAdmissionDate();
        holder.tvClass.setText(data.getStdClass());
        holder.tvDate.setText(formattedDate(data.getAdmissionDate()));
        holder.tvStatus.setText(data.getStatus());
    }

    @Override
    public int getItemCount() {
        return ApiDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvClass, tvDate, tvStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvClass = itemView.findViewById(R.id.tvClass);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }

    public String formattedDate(String dateAsString){
        // for removing all non digit characters using regex
        String timestampStr = dateAsString.replaceAll("\\D+", "");

        // Parsing string into long
        long timestamp = Long.parseLong(timestampStr);

        // Convert milliseconds to seconds
        long timestampSeconds = timestamp / 1000;

        // Create a Date object
        Date date = new Date(timestamp);

        // Format the Date object
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        String formattedDate = sdf.format(date);

        return sdf.format(date);
    }
}
