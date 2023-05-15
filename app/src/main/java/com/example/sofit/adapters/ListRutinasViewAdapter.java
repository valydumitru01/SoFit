package com.example.sofit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofit.R;
import com.example.sofit.model.Routine;

import java.util.List;

public class ListRutinasViewAdapter extends RecyclerView.Adapter<ListRutinasViewAdapter.RutinaViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Routine rutina);
    }
    public interface DeleteListener {
        void deleteItem(Routine rutina);
    }
    private List<Routine> rutinas;
    private final OnItemClickListener listener;
    private final DeleteListener deleteListener;

    public ListRutinasViewAdapter(List<Routine> listaRutinas, OnItemClickListener listener, DeleteListener deleteListener) {
        this.rutinas = listaRutinas;
        this.listener = listener;
        this.deleteListener=deleteListener;
    }

    @NonNull
    @Override
    public ListRutinasViewAdapter.RutinaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linea_recyclerview_rutine, parent, false);
        return new RutinaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RutinaViewHolder holder, int position) {
        Routine rutina = rutinas.get(position);
        holder.bindUser(rutina, listener, deleteListener);

    }

    @Override
    public int getItemCount() {
        return rutinas.size();
    }


    protected class RutinaViewHolder extends RecyclerView.ViewHolder {

        private TextView rutinaTextView;
        private ImageButton imgbtn;

        public RutinaViewHolder(@NonNull View itemView) {
            super(itemView);
            rutinaTextView=(TextView)itemView.findViewById(R.id.rutina);
            imgbtn = (ImageButton) itemView.findViewById(R.id.imageButton2);
        }

        public void bindUser(final Routine rutina, final OnItemClickListener listener, final DeleteListener deleteListener) {

            rutinaTextView.setText(rutina.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(rutina);
                }
            });
            imgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteListener.deleteItem(rutina);
                }
            });

        }

    }
}
