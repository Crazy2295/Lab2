package com.example.crazy.lab2.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDoneException;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crazy.lab2.R;
import com.example.crazy.lab2.interfaces.CityClickResponse;
import com.example.crazy.lab2.utils.DBHelper;

import java.util.List;

public class AdapterChooseCity extends RecyclerView.Adapter<AdapterChooseCity.MyViewHolder> {
    private int fragType = 0;

    private List<String> mDataset;
    private CityClickResponse delegate = null;
    private Context context = null;
    private long userId = -1;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        DBHelper dbHelper;

        MyViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.choose_city_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delegate.itemResponse(mDataset.get(getAdapterPosition()));
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (fragType == 0)
                        CreateAlertForChooseCity(getAdapterPosition());
                    else
                        CreateAlertForSavedCity(getAdapterPosition());
                    return true;
                }
            });
        }


        void CreateAlertForChooseCity(final int number) {
            dbHelper = new DBHelper(context);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setCancelable(true)
                    .setIcon(R.drawable.luna_logo)
                    .setMessage("Город: " + mDataset.get(getAdapterPosition()))
                    .setTitle("Выберите")
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SQLiteDatabase db = dbHelper.getReadableDatabase();
                            String query = "SELECT id FROM cities WHERE name ='" +
                                    mDataset.get(getAdapterPosition()) + "' LIMIT 1;";
                            SQLiteStatement sqlSt = db.compileStatement(query);

                            long cityId = -1;

                            try {
                                cityId = sqlSt.simpleQueryForLong();
                            } catch (SQLiteDoneException e) {
                                Toast.makeText(context, "Can't find city`s id", Toast.LENGTH_SHORT).show();
                            } catch (SQLException e) {
                                Toast.makeText(context, "Script wrong", Toast.LENGTH_SHORT).show();
                            }

                            query = "SELECT id FROM saved_cities WHERE user_id =" +
                                    userId + " AND city_id =" + cityId + " LIMIT 1;";
                            sqlSt = db.compileStatement(query);
                            try{
                                sqlSt.simpleQueryForLong();
                                Toast.makeText(context, "Duplicate row!", Toast.LENGTH_SHORT).show();
                            } catch (SQLiteDoneException e) {

                                db = dbHelper.getWritableDatabase();
                                query = "INSERT INTO saved_cities (user_id, city_id) VALUES (" +
                                        userId + ", " + cityId + ");";
                                sqlSt = db.compileStatement(query);
                                sqlSt.executeInsert();
                            }
                        }
                    })
                    .setNeutralButton("Погода", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delegate.itemResponse(mDataset.get(getAdapterPosition()));
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        void CreateAlertForSavedCity(final int number) {
            dbHelper = new DBHelper(context);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setCancelable(true)
                    .setIcon(R.drawable.luna_logo)
                    .setMessage("Город: " + mDataset.get(getAdapterPosition()) + "?")
                    .setTitle("Выберите")
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            SQLiteDatabase db = dbHelper.getReadableDatabase();
                            String query = "SELECT id FROM cities WHERE name ='" +
                                    mDataset.get(getAdapterPosition()) + "' LIMIT 1;";
                            SQLiteStatement sqlSt = db.compileStatement(query);

                            long cityId = -1;

                            try {
                                cityId = sqlSt.simpleQueryForLong();
                            } catch (SQLiteDoneException e) {
                                Toast.makeText(context, "Can't find city`s id", Toast.LENGTH_SHORT).show();
                            } catch (SQLException e) {
                                Toast.makeText(context, "Script wrong", Toast.LENGTH_SHORT).show();
                            }

                            db.delete("saved_cities", "user_id = ? AND city_id = ?",
                                    new String[] {String.valueOf(userId), String.valueOf(cityId)});
                        }
                    })
                    .setNeutralButton("Погода", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delegate.itemResponse(mDataset.get(getAdapterPosition()));
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public AdapterChooseCity(List<String> myDataset, CityClickResponse _delegate,
                             Context _context, long _userId, int _fragType) {
        mDataset = myDataset;
        delegate = _delegate;
        context = _context;
        userId = _userId;
        fragType = _fragType; // 0 - for FragmentChooseCity; 1 - for FragmentSavedCity
    }

    @Override
    public AdapterChooseCity.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_choose_city, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String text = mDataset.get(position);
        holder.mTextView.setText(text);

        if (text.length() > 7)
            holder.mTextView.setTextColor(Color.parseColor("green"));
        else if (text.length() > 5)
            holder.mTextView.setTextColor(Color.parseColor("red"));
        else
            holder.mTextView.setTextColor(Color.parseColor("blue"));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
