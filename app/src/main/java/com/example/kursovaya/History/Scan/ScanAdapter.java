package com.example.kursovaya.History.Scan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kursovaya.DBHelper;
import com.example.kursovaya.History.Calc.CalcAdapter;
import com.example.kursovaya.History.Calc.CalcItem;
import com.example.kursovaya.History.Calc.CalcSheet;
import com.example.kursovaya.R;

import java.util.List;

public class ScanAdapter  extends RecyclerView.Adapter<ScanAdapter.ViewHolder> {
    private DBHelper dbHelper;
    private Context context;
    private List<ScanItem> favItemList;

    public ScanAdapter(Context context, List<ScanItem> favItemList) {
        this.context = context;
        this.favItemList = favItemList;
    }

    @NonNull
    @Override
    public ScanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
                parent, false);
        dbHelper = new DBHelper(context);
        return new ScanAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ScanAdapter.ViewHolder holder, int position) {
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
                    Log.d("MyLog", getAdapterPosition() + "");
                    Intent intent = new Intent(context, ScanSheet.class);
                    intent.putExtra("ID", getAdapterPosition());
                    context.startActivity(intent);
                }
            });
            //remove from fav after click
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final ScanItem favItem = favItemList.get(position);
                    SQLiteDatabase database = dbHelper.getWritableDatabase();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Вы точно хотите удалить запись?");
                    builder.setTitle("Подтверждение");
                    builder.setNegativeButton("Удалить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int delCount = database.delete(DBHelper.TABLE_SCAN, DBHelper.KEY_ID + " =" + favItem.getKey_id(), null);
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
