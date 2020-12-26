package com.BookKeeping.note;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BaseApp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mcontext;
    private List<NoteBean> mDataList;

    public NoteAdapter(Context context, List<NoteBean> mDataList) {
        this.mcontext = context;
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notelist, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final NoteBean notedata = mDataList.get(position);
        TextView tv_date = holder.itemView.findViewById(R.id.tv_date);
        TextView tv_note = holder.itemView.findViewById(R.id.tv_note);

        tv_date.setText(notedata.getDate().substring(0,10));
        tv_note.setText(notedata.getNote());

        holder.itemView.findViewById(R.id.detials).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("ID", notedata.getId());
                bundle.putString("DATE", notedata.getDate());
                bundle.putString("NOTE", notedata.getNote());
                bundle.putString("PHOTO_PATH", notedata.getPhoto());
                Intent intent = new Intent(mcontext, NewNoteActivity.class);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
