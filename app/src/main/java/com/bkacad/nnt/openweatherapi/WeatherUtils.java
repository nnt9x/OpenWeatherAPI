package com.bkacad.nnt.openweatherapi;

public final class WeatherUtils {
    private static String API_KEY = "928133397391e6af373468b74849e7ab";

    public static String createURL(String city){
        return String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s",city, API_KEY);
    }
}
