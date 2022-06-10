package com.example.kursovaya.History.Calc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kursovaya.DBHelper;
import com.example.kursovaya.History.Scan.ScanSheet;
import com.example.kursovaya.R;
import com.example.kursovaya.scanner.ScanActivity;

import java.util.List;

public class CalcAdapter extends RecyclerView.Adapter<CalcAdapter.ViewHolder> {
    private DBHelper dbHelper;
    private Context context;
    private List<CalcItem> favItemList;

    public CalcAdapter(Context context, List<CalcItem> favItemList) {
        this.context = context;
        this.favItemList = favItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
                parent, false);
        dbHelper = new DBHelper(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.CalcTextTitle.setText(favItemList.get(position).getItem_title());
        holder.CalcTextSum.setText(favItemList.get(position).getItem_sum());
    }

    @Override
    public int getItemCount() {
        return favItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView CalcTextTitle, CalcTextSum;
        Button favBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            CalcTextTitle = itemView.findViewById(R.id.CalcTextTitle);
            CalcTextSum = itemView.findViewById(R.id.CalcTextSum);
            favBtn = itemView.findViewById(R.id.favBtn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CalcSheet.class);
                    intent.putExtra("ID", getAdapterPosition());
                    context.startActivity(intent);
                }
            });

            //remove from fav after click
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final CalcItem favItem = favItemList.get(position);
                    SQLiteDatabase database = dbHelper.getWritableDatabase();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Вы точно хотите удалить запись?");
                    builder.setTitle("Подтверждение");
                    builder.setNegativeButton("Удалить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int delCount = database.delete(DBHelper.TABLE_CALC, DBHelper.KEY_ID + " =" + favItem.getKey_id(), null);
                            database.execSQL("VACUUM");
                            removeItem(position);
                        }
                    }).setPositiveButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }

    private void removeItem(int position) {
        favItemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,favItemList.size());
    }
}
