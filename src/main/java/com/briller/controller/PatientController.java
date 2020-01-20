package com.briller.controller;

import com.briller.Response.ftdcResponse;
import com.briller.model.*;
import com.briller.service.GailModel;
import com.briller.service.PatientService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Api("Patient Service for FTDC Questionaire Application")
@RestController
@RequestMapping("/api/patient")
public class PatientController {


    @Autowired
    GailModel gailModel;

    @Autowired
    PatientService patientService;

    /**
     * @param patientDetails
     * @return patientDetails
     */

   @PostMapping("/savePatient")
    public ResponseEntity<?> postPatient(@RequestBody Patient patientDetails) {
       System.out.println("inside controller before calling service function");
        Patient res = patientService.getdataandInsert(patientDetails);
    if (res == null) {
            return new ResponseEntity<>(new ftdcResponse("false", "Error in saving patient"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ftdcResponse("true", res, "successFull insertion of patient details"), HttpStatus.OK);
        }

    }

    /**
    * @param firstName
     * @param lastName
     * @param phoneNo
     * @return if exists=0 or more than 0
     */
    @PostMapping("/checkPatients")
    public ResponseEntity<?> checkPatient(@RequestParam String firstName,@RequestParam String lastName,@RequestParam String phoneNo) {
        List<Patient> data;
        data = patientService.checkpatient(lastName,firstName,phoneNo);

        if(data.isEmpty())
        {
            return new ResponseEntity<>(new ftdcResponse("false", "the patient does not exists"), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(new ftdcResponse("true",  "patient already exists"), HttpStatus.OK);
        }

    }




    /**
     * @return all the regions
     */

    @GetMapping("/getRegions")
    public ResponseEntity<?> getRegions() {
        List<Region> data;
        data = patientService.getdata();
        if (data == null) {
            return new ResponseEntity<>(new ftdcResponse("false", "Error in regions data"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ftdcResponse("true", data, "Regions data successFull"), HttpStatus.OK);
        }
    }


    /**
     * @return all the race
     */

    @GetMapping("/getRace")
    public ResponseEntity<?> getRace() {
        List<Race> data;
        data = patientService.getRaceData();
        if (data == null) {
            return new ResponseEntity<>(new ftdcResponse("false", "Error in race data"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ftdcResponse("true", data, "race data successFull"), HttpStatus.OK);
        }
    }


    /**
     * @return all the country
     */

    @GetMapping("/getCountry")
    public ResponseEntity<?> getCountryData() {
        List<Country> data;
        data = patientService.getCountryData();
        if (data == null) {
            return new ResponseEntity<>(new ftdcResponse("false", "Error in country data"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ftdcResponse("true", data, "country data successFull"), HttpStatus.OK);
        }
    }

 /*   @PostMapping("/checkgail")
    public double checkGail(@RequestBody Map<String, Integer> gail) {

        System.out.println("inside checkgail");
        System.out.println("age" + gail.get("age"));
        gail.get("age");
        System.out.println("no_of_biopsy" + gail.get("no_of_biopsy"));
        gail.get("no_of_biopsy");
        gail.get("biopsy_malignancy");
        gail.get("first_menstrual_period");
        gail.get("first_pregnancy_age");
        gail.get("family_breast_cancer_history_status");
        System.out.println("no_of_biopsy" + gail.get("no_of_biopsy"));
        gail.get("race_ethnicity");

        double response = gailModel.riskSummary(gail);
        System.out.println(response);
        return response;


    }*/



    }

