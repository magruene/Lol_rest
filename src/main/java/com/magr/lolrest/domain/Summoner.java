package com.magr.lolrest.domain;

import com.magr.lolrest.rest.dto.MasteryPagesDto;
import com.magr.lolrest.rest.dto.SummonerDto;

/**
 * Created by Marc on 06.05.2015.
 */
public class Summoner {
    private SummonerDto summoner;
    private MasteryPagesDto masteries;

    public Summoner(SummonerDto summoner, MasteryPagesDto masteries) {
        this.summoner = summoner;
        this.masteries = masteries;
    }

    public SummonerDto getSummoner() {
        return summoner;
    }

    public void setSummoner(SummonerDto summoner) {
        this.summoner = summoner;
    }

    public MasteryPagesDto getMasteries() {
        return masteries;
    }

    public void setMasteries(MasteryPagesDto masteries) {
        this.masteries = masteries;
    }
}
