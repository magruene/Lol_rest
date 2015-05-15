package com.magr.lolrest.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.GregorianCalendar;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SummonerDto {

    private Long id;
    private String name;
    private long profileIconId;
    private long revisionDate;
    private int summonerLevel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(Long profileIconId) {
        this.profileIconId = profileIconId;
    }

    public long getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(long revisionDate) {
        this.revisionDate = revisionDate;
    }

    public int getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(int summonerLevel) {
        this.summonerLevel = summonerLevel;
    }
}