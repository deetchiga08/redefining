package com.briller.controller;

import com.briller.Response.ftdcResponse;
import com.briller.model.Patient;
import com.briller.service.PatientService;
import com.briller.service.UserPatientService;
import com.briller.utility.PageResource;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Api("Radiologist Service on patients for FTDC Questionaire Application")
@RestController
@RequestMapping("/api/user")
public class UserPatientController {


    @Autowired
    UserPatientService userPatientService;

    @Autowired
    PatientService patientService;

    /**
     *
     * @param patientDetails
     * @return patientDetails
     */

    @PostMapping("/updatePatient")
    public ResponseEntity<?> postPatient(@RequestBody Patient patientDetails) {
        Patient res=userPatientService.getdataandInsert(patientDetails);
        if(res==null)
        {
            return new ResponseEntity<>(new ftdcResponse( "false","Error in editing  patient details"), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(new ftdcResponse( "true",res,"change in patient details is successFull"), HttpStatus.OK);
        }

    }



    @GetMapping("/getPatient")
    public ResponseEntity<?> getPatient(@RequestParam int page, @RequestParam int size) {
        List<Patient> data;
        data = userPatientService.getpatient( page,size);
        if (data == null) {
            return new ResponseEntity<>(new ftdcResponse("false", "Error in regions data"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ftdcResponse("true", data, "Regions data successFull"), HttpStatus.OK);
        }
    }

    /**
     *
     * @return all the patient details in desc order
     */

  /*  @GetMapping("/PatientList1")
    public ResponseEntity<?> getPatientList(@RequestParam int page, @RequestParam int size){
        Page<Object> data=null;
        data=userPatientService.patientList(page,size);
        if(data.isEmpty()) {
            return new ResponseEntity<>(new ftdcResponse( "false","no data"), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(new ftdcResponse( data, " List retrieved Successfully"), HttpStatus.OK);
        }
    }


    @GetMapping("/PatientList")
    public ResponseEntity<?> getPatientList(){
        List<Map<String,Object>> dat=null;
        dat=userPatientService.getList();
        if(dat.isEmpty()) {
            return new ResponseEntity<>(new ftdcResponse( "false","no data"), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(new ftdcResponse( dat, " List retrieved Successfully"), HttpStatus.OK);
        }
    }

    /**
     *
     * @param patientId (get patient details by id)
     * @return data of particular patient
     */
  /*  @GetMapping("/patient")
    public ResponseEntity<?> getBypatientId(@RequestParam Long patientId){

        List<Map<String, Object>> patient = userPatientService.patientListById(patientId);

        if(patient.isEmpty())
        {
            return new ResponseEntity<>(new ftdcResponse( "false","No patient in this id"), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ftdcResponse( "true",patient,"data for the particular patient"), HttpStatus.OK);}

    }

   /**
    * @param firstName
     * @param lastName
     * @param phoneNo
     * @return if exists=0 or more than 0
     */
    @PostMapping("/check")
    public ResponseEntity<?> checkPatient(@RequestParam String lastName,@RequestParam String firstName,@RequestParam String phoneNo,@RequestParam Long patientId) {
        List<Patient> data;
        data = userPatientService.checkpatient(lastName,firstName,phoneNo,patientId);

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
     *
     * @param reviewStatus
     * @param createdDate1
     * @param createdDate2
     * @param score1
     * @param score2
     * @return
     */

    @PostMapping("/filterPatients")
    public ResponseEntity<?> patientListByFilter(@RequestParam int reviewStatus,@RequestParam String createdDate1,@RequestParam String createdDate2,@RequestParam Double score1,@RequestParam Double score2)
    {
        List<Map<String, Object>> data;

        data = userPatientService.patientListByFilter(reviewStatus,createdDate1,createdDate2,score1, score2);
        if(data.isEmpty())
        {
            return new ResponseEntity<>(new ftdcResponse( "false","no data"), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new ftdcResponse( "true",data,"data's for this filter"), HttpStatus.OK);
        }
    }
}

