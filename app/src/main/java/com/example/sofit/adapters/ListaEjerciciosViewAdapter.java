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

public class ListaEjerciciosViewAdapter extends RecyclerView.Adapter<ListaEjerciciosViewAdapter.ExerciseViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(ModelExercise item);
    }
    public interface DeleteListener {
        void deleteItem(ModelExercise ex);
    }
    private List<ModelExercise> exercises;
    private final ListaEjerciciosViewAdapter.OnItemClickListener listener;
    private final ListaEjerciciosViewAdapter.DeleteListener deleteListener;

    public ListaEjerciciosViewAdapter(List<ModelExercise> listExercises, ListaEjerciciosViewAdapter.OnItemClickListener listener,
                                      ListaEjerciciosViewAdapter.DeleteListener deleteListener) {
        this.exercises = listExercises;
        this.listener = listener;
        this.deleteListener=deleteListener;
    }

    @NonNull
    @Override
    public ListaEjerciciosViewAdapter.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.line_recycler_exercise, parent, false);
        return new ExerciseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        ModelExercise ex = exercises.get(position);
        holder.bindUser(ex, listener, deleteListener);

    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }


    protected class ExerciseViewHolder extends RecyclerView.ViewHolder {

        private TextView exerciseTextView;
        private ImageButton imgbtn;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseTextView=(TextView)itemView.findViewById(R.id.ejercicio);
            imgbtn = (ImageButton) itemView.findViewById(R.id.imageButton3);
        }

        public void bindUser(final ModelExercise ex, final OnItemClickListener listener,
                             final DeleteListener deleteListener) {

            exerciseTextView.setText(ex.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(ex);
                }
            });
            imgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteListener.deleteItem(ex);
                }
            });

        }

    }

}




