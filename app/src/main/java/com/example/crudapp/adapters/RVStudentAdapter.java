package com.example.crudapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudapp.common.AddNew;
import com.example.crudapp.R;
import com.example.crudapp.database.DbHelper;
import com.example.crudapp.models.StudentModel;

import java.util.ArrayList;

public class RVStudentAdapter extends  RecyclerView.Adapter<RVStudentAdapter.ViewHolder> {

    Context context;
    ArrayList<StudentModel> studentModelArrayList;

    public RVStudentAdapter(Context context, ArrayList<StudentModel> studentModelArrayList) {
        this.context = context;
        this.studentModelArrayList = studentModelArrayList;
    }



    @NonNull
    @Override
    public RVStudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_student_row, parent, false);
        return new ViewHolder(view) ;

    }

    @Override
    public void onBindViewHolder(@NonNull RVStudentAdapter.ViewHolder holder, int position) {
        StudentModel model = studentModelArrayList.get(position);
        holder.tvName.setText(model.getName());
        holder.tvAddress.setText(model.getAddress());
        holder.tvGender.setText(String.valueOf(model.getGender()));

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddNew.class);
                i.putExtra("id", model.getId());
                i.putExtra("name", model.getName());
                i.putExtra("address", model.getAddress());
                i.putExtra("gender", model.getGender());
                i.putExtra("title", "Update Record");
                i.putExtra("btnText", "Update");

                context.startActivity(i);
            }
        });

        // deleting single row
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDeleteDialog = new AlertDialog.Builder(context);
                alertDeleteDialog.setTitle("Delete ");
                alertDeleteDialog.setIcon(R.drawable.ic_baseline_delete_24);
                alertDeleteDialog.setMessage("Are you sure you want to delete  this book?");

                alertDeleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // Making object of DbHelper class so that we can have access to the deleteBook method
                        DbHelper dbHelper = new DbHelper(context);
                        // deleting single row

                        dbHelper.deleteStudent(model.getId());
                        notifyDataSetChanged();

                        Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show();

//                        Intent intent = new Intent(context, TaskOne.class);
//                        context.startActivity(intent);
//                        ((Activity)context).finish();


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
        return studentModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvAddress,tvGender;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.name);
            tvAddress = itemView.findViewById(R.id.address);
            tvGender = itemView.findViewById(R.id.gender);
            btnEdit = itemView.findViewById(R.id.edit_button);
            btnDelete = itemView.findViewById(R.id.delete_button);


        }
    }




}
