package com.if4a.jdmcarlist.Model;

import java.util.List;

public class ModelResponse
{
    private String kode, pesan;

    private List<ModelJDMCar> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelJDMCar> getData() {
        return data;
    }
}
