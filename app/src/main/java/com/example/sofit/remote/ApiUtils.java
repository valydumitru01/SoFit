package com.example.sofit.remote;

import retrofit2.Retrofit;

public class ApiUtils {
    public static String BASE_URL="https://exercisedb.p.rapidapi.com/";
    public static String API_KEY = "44e657a162msh0a2dcf988fc87bdp19730ajsnff0e8fe1be04";
    public static String HOST = "exercisedb.p.rapidapi.com";

    public static ExerciseDBAPI createExerciseDBAPI() {
        Retrofit retrofit= RetrofitClient.getClient(ApiUtils.BASE_URL);

        return retrofit.create(ExerciseDBAPI.class);
    }


}
