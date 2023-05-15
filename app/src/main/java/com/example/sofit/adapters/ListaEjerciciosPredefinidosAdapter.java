package com.example.sofit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofit.R;
import com.example.sofit.model.ModelExercise;

import java.util.List;

public class ListaEjerciciosPredefinidosAdapter extends RecyclerView.Adapter<ListaEjerciciosPredefinidosAdapter.EjercicioPredefinidoViewHolder> {


    private final OnItemClickListener listener;
    private final List<ModelExercise> ejercicios;
    public ListaEjerciciosPredefinidosAdapter(List<ModelExercise> ejercicios, ListaEjerciciosPredefinidosAdapter.OnItemClickListener listener) {
        this.ejercicios = ejercicios;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EjercicioPredefinidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.line_recycler_exercise, parent, false);
        return new EjercicioPredefinidoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EjercicioPredefinidoViewHolder holder, int position) {
        ModelExercise ej = ejercicios.get(position);
        holder.bindUser(ej, listener);

    }

    @Override
    public int getItemCount() {
        return ejercicios.size();
    }


    public interface OnItemClickListener {
        void onItemClick(ModelExercise item);
    }

    protected class EjercicioPredefinidoViewHolder extends RecyclerView.ViewHolder {

        private final TextView ejercicioTextView;
        private ImageButton imgbtn;

        public EjercicioPredefinidoViewHolder(@NonNull View itemView) {
            super(itemView);
            ejercicioTextView = (TextView) itemView.findViewById(R.id.ejercicio);
        }

        public void bindUser(final ModelExercise ejercicio,
                             final ListaEjerciciosPredefinidosAdapter.OnItemClickListener listener) {

            ejercicioTextView.setText(ejercicio.getName());

            itemView.setOnClickListener(v ->
                    listener.onItemClick(ejercicio)
            );
        }

    }
}
