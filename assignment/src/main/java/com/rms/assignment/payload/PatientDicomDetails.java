package com.rms.assignment.payload;

import java.util.Date;

public class PatientDicomDetails {

    private String patientId;
    private String patientName;
    private String modality;
    private String studyDate;
    private String age;
    private String patientSex;
    private String birthDate;

    public PatientDicomDetails(String patientId, String patientName, String modality, String studyDate, String age, String patientSex, String birthDate) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.modality = modality;
        this.studyDate = studyDate;
        this.age = age;
        this.patientSex = patientSex;
        this.birthDate = birthDate;
    }
}