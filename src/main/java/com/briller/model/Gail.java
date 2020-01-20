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


    private Long patientId;
    private double score;
    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_patient_id_seq")
    @SequenceGenerator(name = "patient_patient_id_seq",sequenceName = "patient_patient_id_seq", allocationSize = 1)
    @Column(name="patient_id")
    public Long getPatientId(){return patientId;}
    public void setPatientId(Long patientId){this.patientId=patientId;}

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
