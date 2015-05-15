package com.magr.lolrest.domain;

import java.util.List;

public class Summoners {

    private List<Summoner> summoners;

    public Summoners(List<Summoner> summoners) {
        this.summoners = summoners;
    }

    public List<Summoner> getSummoners() {
        return summoners;
    }

    public void setSummoners(List<Summoner> summoners) {
        this.summoners = summoners;
    }
}