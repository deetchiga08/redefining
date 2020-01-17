package com.briller.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Gail {

    private Long surveyId;
    private Long patientId;
    private Long gailId;
    private double score;
    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gail_gail_id_seq")
    @SequenceGenerator(name = "gail_gail_id_seq",sequenceName = "gail_gail_id_seq", allocationSize = 1)
    @Column(name="gail_id")
    public Long getGailId(){ return gailId;}
    public void setGailId(Long gailId){this.gailId = gailId;}

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient", referencedColumnName = "patient_id")*/
    @Column(name="patient_id")
    public Long getPatientId(){return patientId;}
    public void setPatientId(Long patientId){this.patientId=patientId;}


    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "survey", referencedColumnName = "survey_id")*/
    @Column(name="survey_id")
    public Long getSurveyId(){return surveyId;}
    public void setSurveyId(Long surveyId){this.surveyId=surveyId;}


    @Basic
    @Column(name="score")
    public double getScore(){return score;}
    public void setScore(double score){this.score=score;}

    @Basic
    @Column(name = "created_dt")
    @CreatedDate
    public LocalDateTime getCreatedDt() {
        return createdDt;
    }
    public void setCreatedDt(LocalDateTime createdDt) {
        this.createdDt = createdDt;
    }

    @Basic
    @Column(name = "updated_dt")
    @LastModifiedDate
    public LocalDateTime getUpdatedDt() {
        return updatedDt;
    }
    public void setUpdatedDt(LocalDateTime updatedDt) {
        this.updatedDt = updatedDt;
    }
}
