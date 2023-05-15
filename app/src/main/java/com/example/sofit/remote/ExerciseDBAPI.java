package com.example.sofit.remote;

import com.example.sofit.server.exerciselist.ExerciseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Header;

public interface ExerciseDBAPI {


    /**
     * List all exercises
     * https://exercisedb.p.rapidapi.com/exercises
     * @param apikey
     * @param host
     * @return
     */
    @GET("exercises")
    Call<List<ExerciseData>> getListExercises(
            @Header("X-RapidAPI-Key") String apikey,
            @Header("X-RapidAPI-Host") String host
    );

    /**
     * Get an exercise by name
     * https://exercisedb.p.rapidapi.com/exercises
     * @param apikey
     * @param host
     * @return
     */
    @GET("exercises")
    Call<List<ExerciseData>> getExerciseByName(
            @Header("X-RapidAPI-Key") String apikey,
            @Header("X-RapidAPI-Host") String host
    );

    /**
     * List exercises by body part
     * https://exercisedb.p.rapidapi.com/exercises/bodyPart/{bodyPart}
     * @param bodyPart
     * @param apikey
     * @param host
     * @return Call<List<ExerciseData>> list of exercise data
     */
    @GET("exercises/bodyPart/{bodyPart}")
    Call<List<ExerciseData>> getListExercisesByBodyPart(
            @Path("bodyPart") String bodyPart,
            @Header("X-RapidAPI-Key") String apikey,
            @Header("X-RapidAPI-Host") String host
    );
    /**
     * List of body parts
     * https://exercisedb.p.rapidapi.com/exercises/bodyPart/{bodyPart}
     * @param apiKey
     * @param host
     * @return
     */
    @GET("/exercises/bodyPartList")
    Call<List<String>> getListBodyParts(
            @Header("X-RapidAPI-Key") String apiKey,
            @Header("X-RapidAPI-Host") String host
    );

    /**
     * Get exercise by id
     * @param id
     * @param apiKey
     * @param host
     * @return Call<ExerciseData> with the exercise desired
     */
    @GET("/exercise/{id}")
    Call<ExerciseData> getExerciseById(
            @Path("id") String id,
            @Header("X-RapidAPI-Key") String apiKey,
            @Header("X-RapidAPI-Host") String host
    );
}
