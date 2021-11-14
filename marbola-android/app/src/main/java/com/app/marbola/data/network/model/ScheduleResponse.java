package com.app.marbola.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by arief on 11/05/18.
 */

public class ScheduleResponse {

    @Expose
    @SerializedName("data")
    private List<Schedule> data;

    @Expose
    @SerializedName("status")
    private int status;

    @Expose
    @SerializedName("message")
    private String message;

    public List<Schedule> getData() {
        return data;
    }

    public void setData(List<Schedule> data) {
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

    public static class Schedule {

        @Expose
        @SerializedName("away_team_api_id")
        private int awayTeamApiId;

        @Expose
        @SerializedName("away_team_logo")
        private String awayTeamLogoUrl;

        @Expose
        @SerializedName("away_team_name")
        private String awayTeamName;

        @Expose
        @SerializedName("date")
        private String dateSchedule;

        @Expose
        @SerializedName("home_team_api_id")
        private int homeTeamApiId;

        @Expose
        @SerializedName("home_team_logo")
        private String homeTeamLogoUrl;

        @Expose
        @SerializedName("home_team_name")
        private String homeTeamName;

        @Expose
        @SerializedName("id")
        private int idSchedule;

        @Expose
        @SerializedName("league_name")
        private String leagueName;

        @Expose
        @SerializedName("prediction_result")
        private int predictionResult;

        @Expose
        @SerializedName("referee_name")
        private String refereeName;

        @Expose
        @SerializedName("stadium")
        private String stadium;

        @Expose
        @SerializedName("status_final_time")
        private int statusFinalTime;

        @Expose
        @SerializedName("time")
        private String timeSchedule;

        public int getAwayTeamApiId() {
            return awayTeamApiId;
        }

        public void setAwayTeamApiId(int awayTeamApiId) {
            this.awayTeamApiId = awayTeamApiId;
        }

        public String getAwayTeamLogoUrl() {
            return awayTeamLogoUrl;
        }

        public void setAwayTeamLogoUrl(String awayTeamLogoUrl) {
            this.awayTeamLogoUrl = awayTeamLogoUrl;
        }

        public String getAwayTeamName() {
            return awayTeamName;
        }

        public void setAwayTeamName(String awayTeamName) {
            this.awayTeamName = awayTeamName;
        }

        public String getDateSchedule() {
            return dateSchedule;
        }

        public void setDateSchedule(String dateSchedule) {
            this.dateSchedule = dateSchedule;
        }

        public int getHomeTeamApiId() {
            return homeTeamApiId;
        }

        public void setHomeTeamApiId(int homeTeamApiId) {
            this.homeTeamApiId = homeTeamApiId;
        }

        public String getHomeTeamLogoUrl() {
            return homeTeamLogoUrl;
        }

        public void setHomeTeamLogoUrl(String homeTeamLogoUrl) {
            this.homeTeamLogoUrl = homeTeamLogoUrl;
        }

        public String getHomeTeamName() {
            return homeTeamName;
        }

        public void setHomeTeamName(String homeTeamName) {
            this.homeTeamName = homeTeamName;
        }

        public int getIdSchedule() {
            return idSchedule;
        }

        public void setIdSchedule(int idSchedule) {
            this.idSchedule = idSchedule;
        }

        public String getLeagueName() {
            return leagueName;
        }

        public void setLeagueName(String leagueName) {
            this.leagueName = leagueName;
        }

        public int getPredictionResult() {
            return predictionResult;
        }

        public void setPredictionResult(int predictionResult) {
            this.predictionResult = predictionResult;
        }

        public String getRefereeName() {
            return refereeName;
        }

        public void setRefereeName(String refereeName) {
            this.refereeName = refereeName;
        }

        public String getStadium() {
            return stadium;
        }

        public void setStadium(String stadium) {
            this.stadium = stadium;
        }

        public int getStatusFinalTime() {
            return statusFinalTime;
        }

        public void setStatusFinalTime(int statusFinalTime) {
            this.statusFinalTime = statusFinalTime;
        }

        public String getTimeSchedule() {
            return timeSchedule;
        }

        public void setTimeSchedule(String timeSchedule) {
            this.timeSchedule = timeSchedule;
        }

    }

}
