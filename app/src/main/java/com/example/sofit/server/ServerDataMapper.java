package com.example.sofit.server;

import com.example.sofit.model.ModelExercise;
import com.example.sofit.server.exerciselist.ExerciseData;

import java.util.ArrayList;
import java.util.List;

public class ServerDataMapper {


    /**
     * Converts the output from JSON to our domain
     *
     * @param data
     * @return Exercise - domain
     */
    public static List<ModelExercise> convertExerciseDataListToDomain(List<ExerciseData> data) {
        List<ModelExercise> exercises = new ArrayList<>();
        for (ExerciseData exerciseData : data) {
            exercises.add(convertExerciseDataToDomain(exerciseData));
        }
        return exercises;
    }

    /**
     * Converts the output from JSON to Exercise (domain)
     *
     * @param data
     * @return Exercise - domain
     */
    public static ModelExercise convertExerciseDataToDomain(ExerciseData data) {
        return new ModelExercise(data.getName(), data.getGifUrl());
    }
}
