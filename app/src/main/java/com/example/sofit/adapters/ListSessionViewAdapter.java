package com.example.sofit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofit.R;
import com.example.sofit.model.ModelSession;

import java.util.List;

public class ListSessionViewAdapter extends RecyclerView.Adapter<ListSessionViewAdapter.DayViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(ModelSession item);
    }
    public interface DeleteListener {
        void deleteItem(ModelSession session);
    }
    private List<ModelSession> sessions;
    private final OnItemClickListener listener;
    private final DeleteListener deleteListener;

    public ListSessionViewAdapter(List<ModelSession> listaSessions, OnItemClickListener listener, DeleteListener deleteListener) {
        this.sessions = listaSessions;
        this.listener = listener;
        this.deleteListener=deleteListener;
    }

    @NonNull
    @Override
    public ListSessionViewAdapter.DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.line_recyclerview_day, parent, false);
        return new DayViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        ModelSession session = sessions.get(position);
        holder.bindUser(session, listener, deleteListener);

    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }


     protected class DayViewHolder extends RecyclerView.ViewHolder {

        private TextView diaTextView;
        private ImageButton imgbtn;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            diaTextView=(TextView)itemView.findViewById(R.id.session);
            imgbtn = (ImageButton) itemView.findViewById(R.id.imageButton);
        }

         public void bindUser(final ModelSession session, final OnItemClickListener listener, final DeleteListener deleteListener) {

             diaTextView.setText(session.getName());

             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     listener.onItemClick(session);
                 }
             });
             imgbtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     deleteListener.deleteItem(session);
                 }
             });

         }

    }
}
