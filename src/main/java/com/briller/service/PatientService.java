package com.briller.service;

import com.briller.model.*;
import com.briller.repository.*;
import net.bytebuddy.dynamic.scaffold.TypeInitializer;
import org.joda.time.Years;
//import org.omg.CORBA.Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Description;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import sun.awt.Symbol;
import com.briller.service.GailModel;

import java.text.Format;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.text.SimpleDateFormat;
import java.util.*;

@Description("service and methods for patient")
@Service
public class PatientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PatientService.class);


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


        Map<String,Integer> gail =new HashMap<String,Integer>();
        System.out.println("inside checkgail");
        gail.put("age",age);
        gail.put("no_of_biopsy",noOfBiopsy);
        gail.put("biopsy_malignancy",biopsyMaliGnancy);
        gail.put("first_menstrual_period",firstMenstrualPeriod);
        gail.put("first_pregnancy_age",firstPregnancyAge);
        gail.put("family_breast_cancer_history_status",familyHistory);
        gail.put("race_ethnicity",race);
        System.out.println("gail "+gail);
        System.out.println("before executing gail model");
        double response = gailModel.riskSummary(gail);
        System.out.println(response);

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
        gail1.setPatientId(pId);
        gail1.setSurveyId(sId);
        gail1.setScore(response);
        gailRepository.save(gail1);
        LOGGER.info("successFull execution of inserting in patient table in patientService");
        return res;
        }
        catch(Exception e)
        {
            LOGGER.error("Error in executing in saving patient data in patient table in patientService"+e.getMessage());
            throw  e;
        }
    }

    /**
     * checks if already same user already exists
     * @param lastName
     * @param firstName
     * @param phoneNo
     * @return
     */

   public List<Patient> checkpatient(String lastName,String firstName,String phoneNo){
        try{
        List<Patient> patient = patientRepository.findByLastNameAndFirstNameAndPhoneNo(lastName,firstName,phoneNo);
        LOGGER.info("successFull execution of checkpatient query in patientService for"+firstName+"   "+lastName+"   "+phoneNo);
        return patient;
        }
        catch(Exception e)
        {
            LOGGER.error("error in execution of check patient already exists query in patientService"+e.getMessage());
            throw  e;
        }
    }



    /**
     * to get all region data
     * @return
     */

    public List<Region> getdata(){

        try{
        LOGGER.info("successFull Execution of getRegions query in PatientService");
        return regionRepository.findAll();}
        catch(Exception e)
        {
            LOGGER.error("error in execution of getRegions query in patientService"+e.getMessage());
            throw e;
        }
    }

    /**
     * to get all race data
     * @return
     */

   public List<Race> getRaceData(){
        List<Race> race;
        try{
            LOGGER.info("successFull Execution of getRegions query in PatientService");
            return raceRepository.findAll();}
        catch(Exception e)
        {
            LOGGER.error("error in execution of getRace query in patientService"+e.getMessage());
            throw e;
        }
    }

    /**
     * to get all country data
     * @return
     */

    public List<Country> getCountryData(){
        try{
            LOGGER.info("successFull Execution of getCountry query in PatientService");
            return countryRepository.findAll();
        }
        catch(Exception e)
        {
            LOGGER.error("error in execution of getcountry query in patientService"+e.getMessage());
            throw e;
        }
    }




}
