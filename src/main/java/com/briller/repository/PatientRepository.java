package com.briller.repository;


import com.briller.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    String query="insert into history_details (patient_id,first_name,middle_name,last_name,dob,age,country_code,phone_number,race_ethnicity, sub_race,country_id,region_id,father_island,mother_island,num_of_pregnancies,first_pregnancy_age,num_of_children,pregnancy_status_now,nursing_status_now,"+
                                              "first_menstrual_period,last_menstrual_period,family_doctor,birth_control_status,birth_control_time,hormones_and_replacement,previous_mammogram,pm_location,dates_of_examination,"+
                                              "breast_biopsy,biopsy_malignancy, no_of_biopsy,diagnosis_detail,genetic_testing,br_ca1 ,br_ca2,other_gene,none_gene,not_known_gene,previous_breast_surgery_status,previous_breast_surgery_date,previous_breast_surgery,previous_breast_radiation_status,"+
                                              "previous_breast_radiation_date,previous_breast_radiation,previous_chemotherapy_status,previous_chemo_date,previous_chemo,routine_check_up_status,present_breast_complaint,skin_dimpling_status,nipple_retraction_status,"+
                                              "discharge_status,discharge_color,"+
                                              "family_breast_cancer_history_status,bc_mother_mside,bc_mother_fside,bc_father_mside,bc_father_fside,bc_gm_mside,bc_gm_fside,bc_aunt_mside,bc_aunt_fside,bc_cousin_mside,bc_cousin_fside,bc_sister_fside,"+
                                              "prostate_cancer,pc_father_status,pc_bro_status,pc_cousin_status,pc_uncle_status,pc_gf_status,score,created_by,updated_by,technician_notes)"+
                                              "select patient_id,first_name,middle_name,last_name,dob,age,country_code,phone_number,race_ethnicity,sub_race,country_id,region_id,father_island,mother_island,num_of_pregnancies,first_pregnancy_age,num_of_children,pregnancy_status_now,nursing_status_now,"+
                                              "first_menstrual_period,last_menstrual_period,family_doctor,birth_control_status,birth_control_time,hormones_and_replacement,previous_mammogram,pm_location,dates_of_examination,"+
                                              "breast_biopsy,biopsy_malignancy, no_of_biopsy,diagnosis_detail,genetic_testing,br_ca1,br_ca2,other_gene,none_gene,not_known_gene,previous_breast_surgery_status,previous_breast_surgery_date,previous_breast_surgery,previous_breast_radiation_status,"+
                                                "previous_breast_radiation_date,previous_breast_radiation,previous_chemotherapy_status,previous_chemo_date,previous_chemo,routine_check_up_status,present_breast_complaint,skin_dimpling_status,nipple_retraction_status,"+
                                               "discharge_status,discharge_color,"+
                                              "family_breast_cancer_history_status,bc_mother_mside,bc_mother_fside,bc_father_mside,bc_father_fside,bc_gm_mside,bc_gm_fside,bc_aunt_mside,bc_aunt_fside,bc_cousin_mside,bc_cousin_fside,bc_sister_fside,"+
                                              "prostate_cancer,pc_father_status,pc_bro_status,pc_cousin_status,pc_uncle_status,pc_gf_status,score,created_by,updated_by,technician_notes from patient where patient_id=patientId";

    @Query(value = "select a.first_name||' '|| a.middle_name || ' '|| a.last_name as displayname,a.patient_id,a.first_name,a.middle_name,a.last_name,a.phone_number," +
            " a.created_dt,a.score,a.region_id,a.country_id,c.country_name||','||b.region as countryname from patient a join region b on a.region_id=b.id join country c " +
            "on a.country_id=c.country_id order by a.created_dt desc ;", countQuery = "select count(*) from (select a.first_name||' '|| a.middle_name || ' '|| a.last_name as displayname,a.patient_id,a.first_name,a.middle_name,a.last_name,a.phone_number,a.created_dt,a.score,a.region_id,a.country_id,c.country_name||','||b.region as countryname from patient a join region b on a.region_id=b.id join country c on a.country_id=c.country_id order by a.created_dt desc)as count",
            nativeQuery = true)
     public List<Object>  findpatientList();


    /*@Query(value = "select a.score::numeric as score,a.first_name||' '|| a.middle_name || ' '|| a.last_name as displayname,a.patient_id,a.first_name,a.middle_name,a.last_name,a.phone_number, a.created_dt,a.score,a.region_id,a.country_id,c.country_name||','||b.region as countryname from patient a join region b on a.region_id=b.id join country c on a.country_id=c.country_id order by a.patient_id desc;" , nativeQuery = true)
    public List<Map<String,Object>> findlist();*/

    String findlistquery="select round(a.score::numeric,2) as score,a.first_name||' '|| a.middle_name || ' '|| a.last_name as displayname,a.patient_id,a.first_name,a.middle_name,a.last_name,a.phone_number, a.created_dt,a.score,a.region_id,a.country_id,c.country_name||','||b.region as countryname from patient a join region b on a.region_id=b.id join country c on a.country_id=c.country_id order by a.patient_id desc;";

    String countQuery = "select a.first_name||' '|| a.middle_name || ' '|| a.last_name as displayname,a.patient_id,a.first_name,a.middle_name,a.last_name,a.phone_number,a.created_dt,a.score,a.region_id,a.country_id,c.country_name||','||b.region as countryname from patient a join region b on a.region_id=b.id join country c on a.country_id=c.country_id order by a.created_dt desc";

    @Query(value="Select a.*, b.region, c.region as father_island_id, d.region as mother_island_id,e.country_name  from patient a Left join region b on" +
            " a.region_id = b.id Left join region c on a.father_island=c.id Left join region d on a.mother_island=d.id left join country e on a.country_id=e.country_id " +
            "where patient_id = ?1" ,nativeQuery = true)
    public List<Map<String, Object>> findByCondition(Long patientId);


      @Query(value="select count(*) as exists from patient where first_name=?1 and middle_name=?2 and last_name=?3 and phone_number=?4",nativeQuery = true)
    public List<Map<String,Object>> findBypatient(String firstName, String middleName,String lastName, String phoneNo);

    /*@Query(value="select count(*) as exists from patient where first_name=?1 and last_name=?2 and phone_number=?3 and patient_id!=?4",nativeQuery = true)
    public List<Map<String,Object>> findBypatient1(String firstName, String lastName, String phoneNo,Long patientId);*/

    String findBypatient1="select count(*) as exists1 from patient where first_name='firstName' and middle_name='middleName' and last_name='lastName' and phone_number='phoneNo' and patient_id!=patientId";

    /*@Query(value="select * from patient where review_status=?1 and dob between ?2 and ?3 and score between ?4 and ?5",nativeQuery = true)
    public List<Map<String,Object>> findByFilter(String reviewStatus, String dob1,String dob2,Double score1,Double score2);

    @Query(value="select * from patient where dob between '?1' and '?2' and score between ?3 and ?4 ",nativeQuery = true)
    public List<Map<String,Object>> findByFilter1(String dob1,String dob2,Double score1,Double score2);*/

    String review="select a.first_name||' '|| a.middle_name || ' '|| a.last_name as displayname,a.patient_id,a.first_name,a.middle_name,a.last_name,a.phone_number,a.created_dt,a.score,a.region_id,a.country_id,c.country_name||','||b.region as countryname" +
            " from patient a join region b on a.region_id=b.id join country c on a.country_id=c.country_id where  first_name is not null ";

    String cage="select extract(year from age(current_timestamp, 'dob')) as age";




   // List<Patient> findByfirstNameAndPatient_lastNameAndPatient_phoneNo(String firstName,String lastName, String phoneNo);

    List<Patient> findByLastNameAndFirstNameAndPhoneNo(String lastName,String firstName,String phoneNo);


    List<Patient> findByLastNameAndFirstNameAndPhoneNoAndPatientIdNot(String lastName,String firstName,String phoneNo,Long patientId);

    Patient  findByPatientId(Long patientId);


   List<Patient> findBySurvey_ReviewStatus(Boolean reviewStatus);

  //  Page<Patient> findByCreatedDtLessThan(LocalDateTime dateTime);


    //  Page<Patient> findByCreatedDtLessThan(LocalDateTime createddt);

 //  Page<Patient> findByCreatedDtGreaterThan(LocalDateTime createddt);



}
