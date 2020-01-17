package com.briller.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Race {



    private int raceId;
    private String raceName;
    private String subRace;
    private Boolean subRaceInd;




    @Id
    @Basic
    @Column(name = "race_id", nullable = false)
    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }


    @Basic
    @Column(name = "race_name", nullable = false)
    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    @Basic
    @Column(name = "sub_race", nullable = false)
    public String getSubRace() {
        return subRace;
    }

    public void setSubRace(String subRace) {
        this.subRace = subRace;
    }

    @Basic
    @Column(name = "sub_race_ind", nullable = false)
    public Boolean getSubRaceInd() {
        return subRaceInd;
    }

    public void setSubRaceInd(Boolean subRaceInd) {
        this.subRaceInd = subRaceInd;
    }


}
