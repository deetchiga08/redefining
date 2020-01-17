package com.briller.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Survey {

    private Long surveyId;
    //private Patient patient;
    private String physicianName;
    private int birthControlStatus;
    private String birthControlTime;
    private int hormonesAndReplacement;
    private int previousMammogram;
    private String previousMammogramLocation;
    private String datesOfPM;
    private int breastBiopsy;
    private int biopsyMalignancy;
    private int noOfBiopsy;
    private String diagnosisDetail;
    private int geneticTesting;
    private Boolean brCa1;
    private Boolean brCa2;
    private Boolean otherGenes;
    private Boolean geneNone;
    private Boolean geneDonTKnow;
    private Boolean previousBreastSurgeryStatus;
    private Date previousBreastSurgeryDate;
    private String previousBreastSurgery;
    private Boolean previousBreastRadiationStatus;
    private Date previousBreastRadiationDate;
    private String previousBreastRadiation;
    private Boolean previousChemotherapyStatus;
    private Date previousChemotherapyDate;
    private String previousChemotherapy;
    private int routineCheckupStatus;
    private String presentBreastComplaint;
    private Boolean skinDimplingStatus;
    private Boolean nippleRetractionStatus;
    private Boolean dischargeStatus;
    private String dischargeColor;
    private int familyBCHistoryStatus;
    private Boolean bcMotherMside;
    private Boolean bcMotherFside;
    private Boolean bcFatherMside;
    private Boolean bcFatherFside;
    private Boolean bcGMMside;
    private Boolean bcGMFside;
    private Boolean bcAuntMside;
    private Boolean bcAuntFside;
    private Boolean bccousinMside;
    private Boolean bccousinFside;
    private Boolean bcSisterFside;
    private int prostateCancer;
    private Boolean pcFatherStatus;
    private Boolean pcBrotherStatus;
    private Boolean pcCousinStatus;
    private Boolean pcUncleStatus;
    private Boolean pcGrandFatherStatus;
     private String createdBy;
    private String updatedBy;
    private Boolean reviewStatus = false;
    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;
    private String technician_Notes;



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "survey_survey_id_seq")
    @SequenceGenerator(name = "survey_survey_id_seq",sequenceName = "survey_survey_id_seq", allocationSize = 1)
    @Column(name="survey_id")
    public Long getSurveyId(){ return surveyId;}
    public void setSurveyId(Long surveyId){this.surveyId = surveyId;}



   /* public Patient getPatient(){return patient;}
    public void setPatient(Patient patient){this.patient=patient;}*/



    @Basic
    @Column(name = "physician_name")
    public String getPhysicianName(){return physicianName;}
    public void setPhysicianName(String physicianName){this.physicianName=physicianName;}

    @Basic
    @Column(name="birth_control_status")
    public int getBirthControlStatus(){return birthControlStatus;}
    public void setBirthControlStatus(int birthControlStatus){this.birthControlStatus=birthControlStatus;}

    @Basic
    @Column(name="birth_control_time")
    public String getBirthControlTime(){return birthControlTime;}
    public void setBirthControlTime(String birthControlTime){this.birthControlTime = birthControlTime;}

    @Basic
    @Column(name="hormones_and_replacement")
    public int getHormonesAndReplacement(){return hormonesAndReplacement;}
    public void setHormonesAndReplacement(int hormonesAndReplacement){this.hormonesAndReplacement = hormonesAndReplacement;}



    @Basic
    @Column(name="previous_mammogram")
    public int getPreviousMammogram(){return previousMammogram;}
    public void setPreviousMammogram(int previousMammogram){this.previousMammogram=previousMammogram;}


    @Basic
    @Column(name = "pm_location")
    public String getPreviousMammogramLocation(){return previousMammogramLocation;}
    public void setPreviousMammogramLocation(String previousMammogramLocation){this.previousMammogramLocation=previousMammogramLocation;}

    @Basic
    @Column(name = "dates_of_pm")
    public String getDatesOfPM(){return datesOfPM;}
    public void setDatesOfPM(String datesOfPM){this.datesOfPM=datesOfPM;}

    @Basic
    @Column(name="breast_biopsy")
    public int getBreastBiopsy(){return breastBiopsy;}
    public void setBreastBiopsy(int breastBiopsy){this.breastBiopsy=breastBiopsy;}

    @Basic
    @Column(name="biopsy_malignancy")
    public int getBiopsyMalignancy(){return  biopsyMalignancy;}
    public void setBiopsyMalignancy(int biopsyMalignancy){this.biopsyMalignancy=biopsyMalignancy;}

    @Basic
    @Column(name = "no_of_biopsy")
    public int getNoOfBiopsy(){ return noOfBiopsy; }
    public void setNoOfBiopsy(int noOfBiopsy){this.noOfBiopsy=noOfBiopsy;}

    @Basic
    @Column(name="diagnosis_detail")
    public String getDiagnosisDetail(){return diagnosisDetail;}
    public void setDiagnosisDetail(String diagnosisDetail){this.diagnosisDetail=diagnosisDetail;}


    @Basic
    @Column(name="genetic_testing")
    public int getGeneticTesting(){return  geneticTesting;}
    public void setGeneticTesting(int geneticTesting){this.geneticTesting=geneticTesting;}


    @Basic
    @Column(name = "br_ca1")
    public Boolean getBrCa1(){return brCa1;}
    public void setBrCa1(Boolean brCa1){this.brCa1=brCa1;}

    @Basic
    @Column(name = "br_ca2")
    public Boolean getBrCa2(){return brCa2;}
    public void setBrCa2(Boolean brCa2){this.brCa2=brCa2;}

    @Basic
    @Column(name = "other_gene")
    public Boolean getOtherGenes(){return otherGenes;}
    public void setOtherGenes(Boolean otherGenes){this.otherGenes=otherGenes;}

    @Basic
    @Column(name = "none_gene")
    public Boolean getGeneNone(){return geneNone;}
    public void setGeneNone(Boolean geneNone){this.geneNone=geneNone;}

    @Basic
    @Column(name = "not_known_gene")
    public Boolean getGeneDonTKnow(){return geneDonTKnow;}
    public void setGeneDonTKnow(Boolean geneDonTKnow){this.geneDonTKnow=geneDonTKnow;}

    @Basic
    @Column(name="previous_breast_surgery_status")
    public Boolean getPreviousBreastSurgeryStatus(){return previousBreastSurgeryStatus;}
    public void setPreviousBreastSurgeryStatus(Boolean previousBreastSurgeryStatus){this.previousBreastSurgeryStatus=previousBreastSurgeryStatus;}

    @Basic
    @Column(name="previous_breast_surgery_date")
    public Date getPreviousBreastSurgeryDate(){return previousBreastSurgeryDate;}
    public void setPreviousBreastSurgeryDate(Date previousBreastSurgeryDate){this.previousBreastSurgeryDate=previousBreastSurgeryDate;}

    @Basic
    @Column(name="previous_breast_surgery")
    public String getPreviousBreastSurgery(){return previousBreastSurgery;}
    public void setPreviousBreastSurgery(String previousBreastSurgery){this.previousBreastSurgery=previousBreastSurgery;}

    @Basic
    @Column(name="previous_breast_radiation_status")
    public Boolean getPreviousBreastRadiationStatus(){return previousBreastRadiationStatus;}
    public void setPreviousBreastRadiationStatus(Boolean previousBreastRadiationStatus){this.previousBreastRadiationStatus=previousBreastRadiationStatus;}

    @Basic
    @Column(name="previous_breast_radiation_date")
    public Date getPreviousBreastRadiationDate(){return previousBreastRadiationDate;}
    public void setPreviousBreastRadiationDate(Date previousBreastRadiationDate){this.previousBreastRadiationDate=previousBreastRadiationDate;}

    @Basic
    @Column(name="previous_breast_radiation")
    public String getPreviousBreastRadiation(){return previousBreastRadiation;}
    public void setPreviousBreastRadiation(String previousBreastRadiation){this.previousBreastRadiation=previousBreastRadiation;}

    @Basic
    @Column(name="previous_chemotherapy_status")
    public Boolean getPreviousChemotherapyStatus(){return previousChemotherapyStatus;}
    public void setPreviousChemotherapyStatus(Boolean previousChemotherapyStatus){this.previousChemotherapyStatus=previousChemotherapyStatus;}

    @Basic
    @Column(name="previous_chemo_date")
    public Date getPreviousChemotherapyDate(){return previousChemotherapyDate;}
    public void setPreviousChemotherapyDate(Date previousChemotherapyDate){this.previousChemotherapyDate=previousChemotherapyDate;}

    @Basic
    @Column(name="previous_chemo")
    public String getPreviousChemotherapy(){return previousChemotherapy;}
    public void setPreviousChemotherapy(String previousChemotherapy){this.previousChemotherapy=previousChemotherapy;}


    @Basic
    @Column(name="routine_check_up_status")
    public int getRoutineCheckupStatus(){return routineCheckupStatus;}
    public void setRoutineCheckupStatus(int routineCheckupStatus){this.routineCheckupStatus=routineCheckupStatus;}

    @Basic
    @Column(name="present_breast_complaint")
    public String getPresentBreastComplaint(){return presentBreastComplaint;}
    public void setPresentBreastComplaint(String presentBreastComplaint){this.presentBreastComplaint=presentBreastComplaint;}

    @Basic
    @Column(name="skin_dimpling_status")
    public Boolean getSkinDimplingStatus(){return skinDimplingStatus;}
    public void setSkinDimplingStatus(Boolean skinDimplingStatus){this.skinDimplingStatus=skinDimplingStatus;}

    @Basic
    @Column(name="nipple_retraction_status")
    public Boolean getNippleRetractionStatus(){return nippleRetractionStatus;}
    public void setNippleRetractionStatus(Boolean nippleRetractionStatus){this.nippleRetractionStatus=nippleRetractionStatus;}

    @Basic
    @Column(name="discharge_status")
    public Boolean getDischargeStatus(){return dischargeStatus;}
    public void setDischargeStatus(Boolean dischargeStatus){this.dischargeStatus=dischargeStatus;}

    @Basic
    @Column(name="discharge_color")
    public String getDischargeColor(){return dischargeColor;}
    public void setDischargeColor(String dischargeColor){this.dischargeColor=dischargeColor;}

    @Basic
    @Column(name="family_breast_cancer_history_status")
    public int getFamilyBCHistoryStatus(){return familyBCHistoryStatus;}
    public void setFamilyBCHistoryStatus(int familyBCHistoryStatus){this.familyBCHistoryStatus=familyBCHistoryStatus;}

    @Basic
    @Column(name="bc_mother_mside")
    public Boolean getBcMotherMside(){return bcMotherMside;}
    public void setBcMotherMside(Boolean bcMotherMside){this.bcMotherMside=bcMotherMside;}

    @Basic
    @Column(name="bc_mother_fside")
    public Boolean getBcMotherFside(){return bcMotherFside;}
    public void setBcMotherFside(Boolean bcMotherFside){this.bcMotherFside=bcMotherFside;}


    @Basic
    @Column(name="bc_father_mside")
    public Boolean getBcFatherMside(){return bcFatherMside;}
    public void setBcFatherMside(Boolean bcFatherMside){this.bcFatherMside=bcFatherMside;}

    @Basic
    @Column(name="bc_father_fside")
    public Boolean getBcFatherFside(){return bcFatherFside;}
    public void setBcFatherFside(Boolean bcFatherFside){this.bcFatherFside=bcFatherFside;}


    @Basic
    @Column(name="bc_gm_mside")
    public Boolean getBcGMMside(){return bcGMMside;}
    public void setBcGMMside(Boolean bcGMMside){this.bcGMMside=bcGMMside;}

    @Basic
    @Column(name="bc_gm_fside")
    public Boolean getBcGMFside(){return bcGMFside;}
    public void setBcGMFside(Boolean bcGMFside){this.bcGMFside=bcGMFside;}

    @Basic
    @Column(name="bc_aunt_mside")
    public Boolean getBcAuntMside(){return bcAuntMside;}
    public void setBcAuntMside(Boolean bcAuntMside){this.bcAuntMside=bcAuntMside;}

    @Basic
    @Column(name="bc_aunt_fside")
    public Boolean getBcAuntFside(){return bcAuntFside;}
    public void setBcAuntFside(Boolean bcAuntFside){this.bcAuntFside=bcAuntFside;}

    @Basic
    @Column(name="bc_cousin_mside")
    public Boolean getBccousinMside(){return bccousinMside;}
    public void setBccousinMside(Boolean bccousinMside){this.bccousinMside = bccousinMside;}

    @Basic
    @Column(name="bc_cousin_fside")
    public Boolean getBccousinFside(){return bccousinFside;}
    public void setBccousinFside(Boolean bccousinFside){this.bccousinFside = bccousinFside;}

    @Basic
    @Column(name="bc_sister_fside")
    public Boolean getBcSisterFside(){return bcSisterFside;}
    public void setBcSisterFside(Boolean bcSisterFside){this.bcSisterFside = bcSisterFside;}


    @Basic
    @Column(name="prostate_cancer")
    public int getProstateCancer(){return prostateCancer;}
    public void setProstateCancer(int prostateCancer){this.prostateCancer=prostateCancer;}



    @Basic
    @Column(name="pc_father_status")
    public Boolean getPcFatherStatus(){return pcFatherStatus;}
    public void setPcFatherStatus(Boolean pcFatherStatus){this.pcFatherStatus=pcFatherStatus;}

    @Basic
    @Column(name="pc_bro_status")
    public Boolean getPcBrotherStatus(){return pcBrotherStatus;}
    public void setPcBrotherStatus(Boolean pcBrotherStatus){this.pcBrotherStatus=pcBrotherStatus;}

    @Basic
    @Column(name="pc_cousin_status")
    public Boolean getPcCousinStatus(){return pcCousinStatus;}
    public void setPcCousinStatus(Boolean pcCousinStatus){this.pcCousinStatus=pcCousinStatus;}

    @Basic
    @Column(name="pc_uncle_status")
    public Boolean getPcUncleStatus(){return pcUncleStatus;}
    public void setPcUncleStatus(Boolean pcUncleStatus){this.pcUncleStatus=pcUncleStatus;}

    @Basic
    @Column(name="pc_gf_status")
    public Boolean getPcGrandFatherStatus(){return pcGrandFatherStatus;}
    public void setPcGrandFatherStatus(Boolean pcGrandFatherStatus){this.pcGrandFatherStatus=pcGrandFatherStatus;}



    @Basic
    @Column(name = "technician_notes")
    public String getTechnician_Notes(){return technician_Notes;}
    public void setTechnician_Notes(String technician_Notes){this.technician_Notes=technician_Notes;}

    @Basic
    @Column(name = "created_by")
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "updated_by")
    public String getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }



    @Basic
    @Column(name="review_status")
    public Boolean getReviewStatus(){return reviewStatus;}
    public void setReviewStatus(Boolean reviewStatus){this.reviewStatus=reviewStatus;}

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





  /*  @Override
    public String toString() {
        return "Survey{" +
                "surveyId=" + surveyId +
                ", physicianName='" + physicianName + '\'' +
                ", birthControlStatus=" + birthControlStatus +
                ", birthControlTime='" + birthControlTime + '\'' +
                ", hormonesAndReplacement=" + hormonesAndReplacement +
                ", previousMammogram=" + previousMammogram +
                ", previousMammogramLocation='" + previousMammogramLocation + '\'' +
                ", datesOfPM='" + datesOfPM + '\'' +
                ", breastBiopsy=" + breastBiopsy +
                ", biopsyMalignancy=" + biopsyMalignancy +
                ", noOfBiopsy=" + noOfBiopsy +
                ", diagnosisDetail='" + diagnosisDetail + '\'' +
                ", geneticTesting=" + geneticTesting +
                ", brCa1=" + brCa1 +
                ", brCa2=" + brCa2 +
                ", otherGenes=" + otherGenes +
                ", geneNone=" + geneNone +
                ", geneDonTKnow=" + geneDonTKnow +
                ", previousBreastSurgeryStatus=" + previousBreastSurgeryStatus +
                ", previousBreastSurgeryDate='" + previousBreastSurgeryDate + '\'' +
                ", previousBreastSurgery='" + previousBreastSurgery + '\'' +
                ", previousBreastRadiationStatus=" + previousBreastRadiationStatus +
                ", previousBreastRadiationDate='" + previousBreastRadiationDate + '\'' +
                ", previousBreastRadiation='" + previousBreastRadiation + '\'' +
                ", previousChemotherapyStatus=" + previousChemotherapyStatus +
                ", previousChemotherapyDate='" + previousChemotherapyDate + '\'' +
                ", previousChemotherapy='" + previousChemotherapy + '\'' +
                ", routineCheckupStatus=" + routineCheckupStatus +
                ", presentBreastComplaint='" + presentBreastComplaint + '\'' +
                ", skinDimplingStatus=" + skinDimplingStatus +
                ", nippleRetractionStatus=" + nippleRetractionStatus +
                ", dischargeStatus=" + dischargeStatus +
                ", dischargeColor='" + dischargeColor + '\'' +
                ", familyBCHistoryStatus=" + familyBCHistoryStatus +
                ", bcMotherMside=" + bcMotherMside +
                ", bcMotherFside=" + bcMotherFside +
                ", bcFatherMside=" + bcFatherMside +
                ", bcFatherFside=" + bcFatherFside +
                ", bcGMMside=" + bcGMMside +
                ", bcGMFside=" + bcGMFside +
                ", bcAuntMside=" + bcAuntMside +
                ", bcAuntFside=" + bcAuntFside +
                ", bccousinMside=" + bccousinMside +
                ", bccousinFside=" + bccousinFside +
                ", bcSisterFside=" + bcSisterFside +
                ", prostateCancer=" + prostateCancer +
                ", pcFatherStatus=" + pcFatherStatus +
                ", pcBrotherStatus=" + pcBrotherStatus +
                ", pcCousinStatus=" + pcCousinStatus +
                ", pcUncleStatus=" + pcUncleStatus +
                ", pcGrandFatherStatus=" + pcGrandFatherStatus +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", reviewStatus=" + reviewStatus +
                ", createdDt='" + createdDt + '\'' +
                ", updatedDt='" + updatedDt + '\'' +
                ", technician_Notes='" + technician_Notes + '\'' +
                ", patient=" + patient +
                '}';
    }
*/






}
