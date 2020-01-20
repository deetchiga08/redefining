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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    // * @param patient
     * @return
     */
    public Patient getdataandInsert(Patient patient)
    {

        Survey survey=patient.getSurvey();
        Gail gail1=patient.getGail();
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
     * list of patients
     * @return
     */

    public  Page<Patient> getpatient(int page, int size){

        try{
            LOGGER.info("successFull Execution of getRegions query in PatientService");

            Pageable pageable =  PageRequest.of(page, size, Sort.by("createdDt").descending());
            Page<Patient> p=patientRepository.findAll(pageable);

            return p;
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

   public Patient patientListById(Long patientId){
        try{
        Patient data;
        data = patientRepository.findByPatientId(patientId);
        LOGGER.info("listed particular data of patient in userPatient service"+patientId);
        return data;
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


    public List<Patient> checkpatient1(Boolean reviewStatus){
        try{
            List<Patient> patient = patientRepository.findBySurvey_ReviewStatus(reviewStatus);
            //  LOGGER.info("successFull execution of checkpatient query in patientService for"+firstName+"   "+lastName+"   "+phoneNo);
            return patient;
        }
        catch(Exception e)
        {
            LOGGER.error("error in execution of check patient already exists query in patientService"+e.getMessage());
            throw  e;
        }
    }


  /*  public patietDTO patientListByFilter(int page, int size,int reviewStatus, String createdDate1, String createdDate2, Double score1, Double score2) {


        Pageable pageable = PageRequest.of(page, size);
        List<Patient> p = new ArrayList<>();
        List<Gail> g1 = new ArrayList<>();
        if (reviewStatus == 2) {
            if (createdDate1 == null && createdDate2 == null) {
                if(score1== null && score2==null)
                {
                    Page<Patient> page1=patientRepository.findAll(pageable);
                    List<Patient> l= page1.getContent();
                    System.out.println("the data is "+l);
                    for(Patient pa:l)
                    {
                        Gail gail=gailRepository.findByPatientId(pa.getPatientId());
                        g1.add(gail);
                    }

                    patietDTO PatientDto=new patietDTO(l,g1);

                    return PatientDto;
                }
                else if (score1 == null && score2!= null) {

                    Page<Gail> g = gailRepository.findByScoreLessThan(score2, pageable);
                    List<Gail> l = g.getContent();
                    for (Gail gail : l) {
                        Patient list = patientRepository.findByPatientId(gail.getPatientId());
                        p.add(list);
                    }
                    patietDTO PatientDto = new patietDTO(p, l);

                    return PatientDto;
                } else if (score1 != null && score2==null) {
                    Page<Gail> g = gailRepository.findByScoreGreaterThan(score1, pageable);
                    List<Gail> l = g.getContent();
                    for (Gail gail : l) {
                        Patient list = patientRepository.findByPatientId(gail.getPatientId());
                        p.add(list);
                    }

                    patietDTO PatientDto = new patietDTO(p, l);

                    return PatientDto;
                } else if(score1 != null && score2 != null ){

                    Page<Gail> g = gailRepository.findByScoreBetween(score1, score2, pageable);
                    List<Gail> l = g.getContent();
                    for (Gail gail : l) {
                        Patient list = patientRepository.findByPatientId(gail.getPatientId());
                        p.add(list);
                    }
                    patietDTO PatientDto = new patietDTO(p, l);

                    return PatientDto;
                }

            } else if (score1 == null && score2 == null) {
                if (createdDate1 == null && createdDate2!=null) {

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime dateTime = LocalDateTime.parse(createdDate2, formatter);
                    Page<Patient> g = patientRepository.findByCreatedDtLessThan(dateTime);
                    List<Patient> l=g.getContent();
                    for(Patient patient:l)
                    {
                        Gail gail=gailRepository.findByPatientId(patient.getPatientId());
                        g1.add(gail);
                    }
                    patietDTO PatientDto=new patietDTO(l,g1);

                    return PatientDto;
                }

            }
        }
    }
*/

}
