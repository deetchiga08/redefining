package com.briller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Patient {
    private Long patientId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    private int age;
    private String countryCode;
    private String phoneNo;
    private int raceId;
    private String raceEthnicity;
    private String subRace;
    private String countryName;
    private String islandName;
    private String fatherIsland;
    private String motherIsland;
    private int numPregnancies;
    private int firstPregnancyAge;
    private int numChildren;
    private int pregnancyStatusNow;
    private int breastFeedingStatus;
    private int firstMenstrualPeriod;
    private String lastMenstrualPeriod;
    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;
    private int agreeStatus;
    private Survey survey;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_patient_id_seq")
    @SequenceGenerator(name = "patient_patient_id_seq",sequenceName = "patient_patient_id_seq", allocationSize = 1)
    @Column(name="patient_id")
    public Long getPatientId(){ return patientId;}
    public void setPatientId(Long patientId){this.patientId = patientId;}

    @Basic
    @Column(name="first_name")
    public String getFirstName(){ return firstName; }
    public void setFirstName(String firstName){ this.firstName = firstName;}

    @Basic
    @Column(name="middle_name")
    public String getMiddleName(){ return middleName; }
    public void setMiddleName(String middleName){ this.middleName = middleName;}

    @Basic
    @Column(name="last_name")
    public String getLastName(){ return lastName; }
    public void setLastName(String lastName){ this.lastName = lastName;}

    @Basic
    @Column(name="dob")
    //@JsonFormat(pattern = "MM-dd-yyyy")
    public Date getDateOfBirth(){return dateOfBirth;}
    public void setDateOfBirth(Date dateOfBirth){this.dateOfBirth=dateOfBirth;}

    @Basic
    @Column(name="age")
    public int getAge(){return age;}
    public void setAge(int age){this.age=age;}

    @Basic
    @Column(name="country_code")
    public String getCountryCode(){return countryCode;}
    public void setCountryCode(String countryCode){this.countryCode=countryCode;}

    @Basic
    @Column(name="phone_number")
    public String getPhoneNo(){return phoneNo;}
    public void setPhoneNo(String phoneNo){this.phoneNo=phoneNo;}


    @Basic
    @Column(name="race_id")
    public int getRaceId(){ return raceId; }
    public void setRaceId(int raceId){ this.raceId = raceId;}


    @Basic
    @Column(name="race_ethnicity")
    public String getRaceEthnicity(){ return raceEthnicity; }
    public void setRaceEthnicity(String raceEthnicity){ this.raceEthnicity = raceEthnicity;}


    @Basic
    @Column(name="sub_race")
    public String getSubRace(){ return subRace; }
    public void setSubRace(String subRace){ this.subRace = subRace;}



    @Basic
    @Column(name = "country_name")
    public String getCountryName(){ return countryName; }
    public void setCountryName(String countryName){this.countryName=countryName;}

    @Basic
    @Column(name = "island_name")
    public String getIslandName(){ return islandName; }
    public void setIslandName(String islandName){this.islandName=islandName;}

    @Basic
    @Column(name = "father_island")
    public String getFatherIsland(){ return fatherIsland; }
    public void setFatherIsland(String fatherIsland){this.fatherIsland=fatherIsland;}

    @Basic
    @Column(name = "mother_island")
    public String getMotherIsland(){ return motherIsland; }
    public void setMotherIsland(String motherIsland){this.motherIsland=motherIsland;}


    @Basic
    @Column(name="num_of_pregnancies")
    public int getNumPregnancies(){return numPregnancies;}
    public void setNumPregnancies(int numPregnancies){this.numPregnancies=numPregnancies;}

    @Basic
    @Column(name = "first_pregnancy_age")
    public int getFirstPregnancyAge(){return firstPregnancyAge;}
    public void setFirstPregnancyAge(int firstPregnancyAge){this.firstPregnancyAge=firstPregnancyAge;}

    @Basic
    @Column(name="num_of_children")
    public int getNumChildren(){return numChildren;}
    public void setNumChildren(int numChildren){this.numChildren=numChildren;}

    @Basic
    @Column(name="pregnancy_status_now")
    public int getPregnancyStatusNow(){return pregnancyStatusNow;}
    public void setPregnancyStatusNow(int pregnancyStatusNow){this.pregnancyStatusNow=pregnancyStatusNow;}

    @Basic
    @Column(name="breast_feeding_status_now")
    public int getBreastFeedingStatus(){return breastFeedingStatus;}
    public void setBreastFeedingStatus(int breastFeedingStatus){this.breastFeedingStatus=breastFeedingStatus;}

    @Basic
    @Column(name="first_menstrual_period")
    public int getFirstMenstrualPeriod(){return firstMenstrualPeriod;}
    public void setFirstMenstrualPeriod(int firstMenstrualPeriod){this.firstMenstrualPeriod=firstMenstrualPeriod;}

    @Basic
    @Column(name="last_menstrual_period")
    public String getLastMenstrualPeriod(){return lastMenstrualPeriod;}
    public void setLastMenstrualPeriod(String lastMenstrualPeriod){this.lastMenstrualPeriod=lastMenstrualPeriod;}



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

    @Basic
    @Column(name = "agree_status")
    public int getAgreeStatus(){return agreeStatus;}
    public void setAgreeStatus(int agreeStatus){this.agreeStatus=agreeStatus;}


    @OneToOne(cascade = CascadeType.ALL,fetch =FetchType.EAGER)
    @JoinColumn(name="survey_id")
    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }








  /*  @Override
    public String toString() {
        return "Patient{" +
                "patientId=" + patientId +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", age=" + age +
                ", countryCode='" + countryCode + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", raceEthnicity='" + raceEthnicity + '\'' +
                ", subRace='" + subRace + '\'' +
                ", countryName='" + countryName + '\'' +
                ", islandName='" + islandName + '\'' +
                ", fatherIsland='" + fatherIsland + '\'' +
                ", motherIsland='" + motherIsland + '\'' +
                ", numPregnancies=" + numPregnancies +
                ", firstPregnancyAge=" + firstPregnancyAge +
                ", numChildren=" + numChildren +
                ", pregnancyStatusNow=" + pregnancyStatusNow +
                ", breastFeedingStatus=" + breastFeedingStatus +
                ", firstMenstrualPeriod=" + firstMenstrualPeriod +
                ", lastMenstrualPeriod='" + lastMenstrualPeriod + '\'' +
                ", createdDt='" + createdDt + '\'' +
                ", updatedDt='" + updatedDt + '\'' +
                '}';
    }

*/

}