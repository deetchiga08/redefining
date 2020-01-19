package com.briller.service;

import com.briller.model.Gail;
import com.briller.model.Patient;
import com.briller.model.Survey;
import com.briller.model.patietDTO;
import com.briller.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

@Description("service for radiologist")
@Service
public class UserPatientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserPatientService.class);

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    GailModel gailModel;



    @Autowired
    GailRepository gailRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    RegionRepository regionRepository;


    @Autowired
    RaceRepository raceRepository;

    @Autowired
    CountryRepository countryRepository;


    @Autowired
    @Qualifier("postgresJdbcTemplate")
    private JdbcTemplate postgresJdbcTemplate;

    /**
     * details of the patient gets saved in patient table and history table
     * @param patient
     * @return
     */
    public Patient getdataandInsert(Patient patient)
    {

        Survey survey=patient.getSurvey();
        Date dob=patient.getDateOfBirth();
        int noOfBiopsy=survey.getNoOfBiopsy();
        System.out.println("noofbiopsy in patient Service"+noOfBiopsy);
        int biopsyMaliGnancy=survey.getBiopsyMalignancy();
        System.out.println("biopsymalignancy "+biopsyMaliGnancy);
        int firstMenstrualPeriod=patient.getFirstMenstrualPeriod();
        System.out.println("1 st mentural period"+firstMenstrualPeriod);
        int firstPregnancyAge=patient.getFirstPregnancyAge();
        System.out.println(" 1st preg age"+firstPregnancyAge);
        int familyHistory=survey.getFamilyBCHistoryStatus();
        System.out.println("family history"+familyHistory);
        int race=patient.getRaceId();
        System.out.println("race"+race);
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter.format(dob);
        System.out.println("sring s "+s);
        String query1=patientRepository.cage;
        String value1=query1.replace("dob",s);
        Map<String, Object> db=postgresJdbcTemplate.queryForMap(value1);
        System.out.println("db map"+db);
        double ag= (double) db.get("age");
        System.out.println("before converting into int age"+ag);
        int age=(int)ag;
        System.out.println("age"+age);
        patient.setAge(age);

        try{

            Patient res = patientRepository.save(patient);
            patient.setSurvey(survey);
            Survey res1=surveyRepository.save(survey);
            Long pId=res.getPatientId();
            System.out.println("the patientId"+pId);
            Long sId=res1.getSurveyId();
            System.out.println("the sid"+sId);
            Gail gail1 = new Gail();
            System.out.println("before setting the id");
            Gail g=gailRepository.findByPatientIdAndSurveyId(pId,sId);
            if (g != null) {
                Long gailId = g.getGailId();
                System.out.println("the gailId before setting gailId"+gailId);
                gail1.setGailId(gailId);
                gail1.setPatientId(pId);
                gail1.setSurveyId(sId);
                Map<String, Integer> gail = new HashMap<String, Integer>();
                System.out.println("inside checkgail");
                gail.put("age", age);
                gail.put("no_of_biopsy", noOfBiopsy);
                gail.put("biopsy_malignancy", biopsyMaliGnancy);
                gail.put("first_menstrual_period", firstMenstrualPeriod);
                gail.put("first_pregnancy_age", firstPregnancyAge);
                gail.put("family_breast_cancer_history_status", familyHistory);
                gail.put("race_ethnicity", race);
                System.out.println("gail " + gail);
                System.out.println("before executing gail model");
                double response = gailModel.riskSummary(gail);
                System.out.println(response);
                gail1.setScore(response);
                gailRepository.save(gail1);
                LOGGER.info("successFull execution of inserting in patient table in patientService");
            }
            return res;
        }
        catch(Exception e)
        {
            LOGGER.error("Error in executing in saving patient data in patient table in patientService"+e.getMessage());
            throw  e;
        }
    }
    /**
     * list of patients
     * @return
     */

    public patietDTO getpatient(int page, int size){

        try{
            LOGGER.info("successFull Execution of getRegions query in PatientService");

            Pageable pageable =  PageRequest.of(page, size, Sort.by("createdDt").descending());
            Page<Patient> p=patientRepository.findAll(pageable);
            List<Patient> l= p.getContent();
            System.out.println("the data is "+l);
            List<Gail> g = new ArrayList<>();

            for(Patient patient:l)
            {
                Gail gail=gailRepository.findByPatientId(patient.getPatientId());
                g.add(gail);
            }

            patietDTO PatientDto=new patietDTO(l,g);

            return PatientDto;
        }
        catch(Exception e)
        {
            LOGGER.error("error in execution of getRegions query in patientService"+e.getMessage());
            throw e;
        }
    }



    /**
     * details of single patient by id
     * @param patientId
     * @return
     */

   public patietDTO patientListById(Long patientId){
        try{
        Patient data;
        data = patientRepository.findByPatientId(patientId);
        Gail gail=gailRepository.findByPatientId(data.getPatientId());
            patietDTO PatientDto=new patietDTO(data,gail);
        LOGGER.info("listed particular data of patient in userPatient service"+patientId);
        return PatientDto;
        }

        catch (Exception e){

                    LOGGER.error("error in findByCondition in patientById in userPatientById in userPatient service" + e.getMessage());
                    throw e;

        }
    }

    /**
     * checks if already same user already exists
     * @param firstName
     * @param lastName
     * @param phoneNo
     * @return
     */

    public List<Patient> checkpatient(String lastName,String firstName,String phoneNo,Long patientId){
        try{
            List<Patient> patient = patientRepository.findByLastNameAndFirstNameAndPhoneNoAndPatientIdNot(lastName,firstName,phoneNo,patientId);
            //  LOGGER.info("successFull execution of checkpatient query in patientService for"+firstName+"   "+lastName+"   "+phoneNo);
            return patient;
        }
        catch(Exception e)
        {
            LOGGER.error("error in execution of check patient already exists query in patientService"+e.getMessage());
            throw  e;
        }
    }


    public List<Map<String, Object>> patientListByFilter(int reviewStatus, String createdDate1, String createdDate2, Double score1, Double score2){
        List<Map<String, Object>> data;
        String condition1="";
        String condition2="";
        String condition3="";
        String condition4="";
        String condition5="";
        String condition6="order by patient_id desc";


        if(reviewStatus==2)
        {
            System.out.println("insidee review status =2");
            condition1="";
        }
        else if(reviewStatus==0)
        {
            System.out.println("inside review status =0");
            condition1="\nand review_status='0'";
        }
        else if(reviewStatus==1)

        {
            System.out.println("inside review status ==1");
            condition1="\nand review_status='1'";
        }

        if(!createdDate1.equals(""))
        {
            System.out.println("inside from date!=null");
            condition2="\nand created_dt::date >= '"+createdDate1+"'";
        }
        if(!createdDate2.equals(""))
        {
            System.out.println("inside to date !=null");
            condition3="\n and created_dt::date<='"+createdDate2+"'";
        }

        if(score1!=null)
        {
            System.out.println("inside from score!=null");
            condition4="\nand score >= "+score1+"";
        }
        if(score2!=null)
        {
            System.out.println("inside from score2!=null");
            condition5="\nand score <= "+score2+"";
        }

        String query=patientRepository.review+condition1+condition2+condition3+condition4+condition5+condition6;
        System.out.println("the query is"+query);
        String value= query.replace("score1",String.valueOf(score1)).replace("score2",String.valueOf(score2));
        System.out.println("the value"+value);
        data=postgresJdbcTemplate.queryForList(value);
        System.out.println("the data"+data);
        return data;


    }


}
