package com.if4a.jdmcarlist.API;

public class RetroServer {
    private static final String alamatServer = "https://kulinerwesleym.000webhostapp.com/";

    private static Retrofit retro;

    public static Retrofit konekRetrofit(){
        if(retro == null){
            retro = new Retrofit.Builder().baseUrl(alamatServer).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retro;
    }
}
