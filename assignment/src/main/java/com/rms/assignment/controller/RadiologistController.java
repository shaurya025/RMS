package com.rms.assignment.controller;

import com.rms.assignment.model.Dicom;
import com.rms.assignment.model.Radiologist;

import com.rms.assignment.payload.PatientDicomDetails;
import com.rms.assignment.repository.RadiologistRepository;

import com.rms.assignment.service.DicomService;
import com.rms.assignment.service.radiologistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class RadiologistController {

@Autowired
private radiologistService radiologist_service;

@Autowired
private DicomService dicom_service;

    @PostMapping(value="/add")
    public Radiologist addRadiologist(@RequestBody Radiologist radiologist){
    return radiologist_service.addRadiologist(radiologist);
    }

    @GetMapping(value="/home")
    public List<Radiologist>getRadiologists(@RequestParam(value="pageNumber",defaultValue = "0",required = false)int pageNumber,
        @RequestParam(value="pageSize",defaultValue="6",required=false)int pageSize
        ){

        return radiologist_service.getRadiologists(pageNumber,pageSize);
    }

    @GetMapping(value="/home/{radiologistId}")
    public Radiologist getRadiologist(@PathVariable("radiologistId")int radiologistId){
        return radiologist_service.getRadiologist(radiologistId);
    }

    @DeleteMapping(value="/home/delete/{radiologistId}")
    public void deleteRadiologist(@PathVariable("radiologistId") int radiologistId){
    radiologist_service.deleteRadiologist(radiologistId);
    }

    @PutMapping(value="/edit/{radiologistId}")
    public Radiologist editRadiologist(@PathVariable("radiologistId") int radiologistId, @RequestBody Radiologist radiologist){
        return  radiologist_service.editRadiologist(radiologistId,radiologist);
    }

    @GetMapping(value="/filterbyname")
    public List<Radiologist> search_radiologists(@RequestParam("name") String name_of_radiologist){
        return (List<Radiologist>)radiologist_service.search_radiologists(name_of_radiologist);
    }

    @GetMapping(value="/filterbytype")
    public List<Radiologist>  filter_radiologists(@RequestParam ("type") String type_of_radiologist){
        return (List<Radiologist>) radiologist_service.filter_radiologists(type_of_radiologist);
    }
    @GetMapping(value="/filterbyemail")
    public List<Radiologist>  filter_radiologists_by_email(@RequestParam ("email") String email_of_radiologist){
        return (List<Radiologist>) radiologist_service.filter_radiologists_by_email(email_of_radiologist);
    }

    @GetMapping(value="/filterbycontact")
    public List<Radiologist>  filter_radiologists_by_contact(@RequestParam ("contact") String contact_of_radiologist){
        return (List<Radiologist>) radiologist_service.filter_radiologists_by_contact(contact_of_radiologist);
    }
    @GetMapping(value="/filterbyusername")
    public List<Radiologist>  filter_radiologists_by_username(@RequestParam ("username") String username_of_radiologist){
        return (List<Radiologist>) radiologist_service.filter_radiologists_by_username(username_of_radiologist);
    }

    @GetMapping("/sortByNameAsc")
    public List<Radiologist> findAllRadiologistsSortedByNameAsc() {
        return radiologist_service.findAllRadiologistsSortedByNameAsc();
    }

    @GetMapping("/sortByNameDesc")
    public List<Radiologist> findAllRadiologistsSortedByNameDesc() {
        return radiologist_service.findAllRadiologistsSortedByNameDesc();
    }

    @GetMapping("/sortByEmailDesc")
    public List<Radiologist> findAllRadiologistsSortedByEmailDesc() {
        return radiologist_service.findAllRadiologistsSortedByEmailDesc();
    }
    @GetMapping("/sortByEmailAsc")
    public List<Radiologist> findAllRadiologistsSortedByEmailAsc() {
        return radiologist_service.findAllRadiologistsSortedByEmailAsc();
    }

    @GetMapping("/sortByUsernameDesc")
    public List<Radiologist> findAllRadiologistsSortedByUsernameDesc() {
        return radiologist_service.findAllRadiologistsSortedByUsernameDesc();
    }

    @GetMapping("/sortByUsernameAsc")
    public List<Radiologist> findAllRadiologistsSortedByUsernameAsc() {
        return radiologist_service.findAllRadiologistsSortedByUsernameAsc();
    }



    @GetMapping("/current-user")
    public String getLoggedInUser(Principal principal){
        return principal.getName();
    }






}



