package com.app.marbola.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by arief on 11/05/18.
 */

public class LeagueResponse {

    @Expose
    @SerializedName("data")
    private List<League> data;

    @Expose
    @SerializedName("status")
    private int status;

    @Expose
    @SerializedName("message")
    private String message;

    public List<League> getData() {
        return data;
    }

    public void setData(List<League> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class League {

        @Expose
        @SerializedName("country_id")
        private int countryId;

        @Expose
        @SerializedName("id")
        private int id;

        @Expose
        @SerializedName("name")
        private String name;

        public int getCountryId() {
            return countryId;
        }

        public void setCountryId(int countryId) {
            this.countryId = countryId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
