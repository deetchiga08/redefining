package com.briller.model;

import java.util.List;

public class patietDTO {


    List<Patient> patients;
    List<Gail> gails;

    Patient patient;
    Gail gail;


    public patietDTO(List<Patient> patients, List<Gail> gails){
        this.patients= patients;
        this.gails = gails;
    }

    public  patietDTO(Patient patient,Gail gail)
    {
        this.patient=patient;
        this.gail=gail;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<Gail> getGails() {
        return gails;
    }


    public Patient getPatient() {
        return patient;
    }

    public Gail getGail() {
        return gail;
    }



}
