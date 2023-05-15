package com.example.sofit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sofit.R;
import com.example.sofit.model.Serie;

import java.util.List;

public class ListSerieViewAdapter extends RecyclerView.Adapter<ListSerieViewAdapter.SerieViewHolder> {

    private List<Serie> series;


    public ListSerieViewAdapter(List<Serie> listSeries) {
        this.series = listSeries;
    }

    @NonNull
    @Override
    public ListSerieViewAdapter.SerieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.line_exercise_serie, parent, false);
        return new SerieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SerieViewHolder holder, int position) {
        Serie serie = series.get(position);
        holder.bindUser(serie,position);

    }

    @Override
    public int getItemCount() {
        return series.size();
    }


    protected class SerieViewHolder extends RecyclerView.ViewHolder {

        private EditText serietxt;
        private EditText reps;
        private EditText weight;

        public SerieViewHolder(@NonNull View itemView) {
            super(itemView);
            serietxt=(EditText)itemView.findViewById(R.id.editTextSeries);
            serietxt.setEnabled(false);
            reps=(EditText)itemView.findViewById(R.id.editTextReps);
            weight=(EditText)itemView.findViewById(R.id.editText_edit_profile_Weight);
        }

        public void bindUser(final Serie serie,int position) {
            serietxt.setText(String.valueOf(position+1));
            reps.setText(String.valueOf(serie.getReps()));
            weight.setText(String.valueOf(serie.getWeight()));

        }

    }
}
