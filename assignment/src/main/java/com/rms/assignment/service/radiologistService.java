package com.rms.assignment.service;

import com.rms.assignment.exception.RadiologistNotFoundException;
import com.rms.assignment.model.Radiologist;
import com.rms.assignment.repository.RadiologistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Component
public class radiologistService {
    @Autowired
    private RadiologistRepository radiologistRepository;

    // function to add a radiologist

    public Radiologist addRadiologist(Radiologist radiologist){
        return radiologistRepository.save(radiologist);
    }

    // function to getAll radiologists
    //(Used in Homepage to display all radiologists)
    public List<Radiologist>getRadiologists(int pageNumber,int pageSize){

         PageRequest p = PageRequest.of(pageNumber,pageSize);
         Page<Radiologist> myPage=radiologistRepository.findAll(p);
         List<Radiologist> radiologist_list=myPage.getContent();
         return radiologist_list;
    }

    //function to get a particular radiologist
    public Radiologist getRadiologist(int radiologistId){

        Optional<Radiologist> optionalRadiologist=radiologistRepository.findById(radiologistId);
        if(!optionalRadiologist.isPresent()){
            throw new RadiologistNotFoundException("Radiologist record not available");
        }else{
           return optionalRadiologist.get();
        }

    }

    // function to edit a particular radiologist

    public Radiologist editRadiologist(int radiologistId,Radiologist radiologist ){
        Optional<Radiologist> existingRadiologist = radiologistRepository.findById(radiologistId);
        if(!existingRadiologist.isPresent()){
            return null;
        }else{
            Radiologist radiologistToUpdate = existingRadiologist.get();
            radiologistToUpdate.setName(radiologist.getName());
            radiologistToUpdate.setContact(radiologist.getContact());
            radiologistToUpdate.setUsername(radiologist.getUsername());
            radiologistToUpdate.setType(radiologist.getType());
            radiologistToUpdate.setEmail(radiologist.getEmail());
            return radiologistRepository.save(radiologistToUpdate);
        }
    }

    public void deleteRadiologist(int radiologistId){
        radiologistRepository.deleteById(radiologistId);
    }


    public List<Radiologist> search_radiologists(String name_of_radiologist){

        return (List<Radiologist>) radiologistRepository.findByNameStartsWith(name_of_radiologist);

    }

    public List<Radiologist> filter_radiologists(String type_of_radiologist){
        return (List<Radiologist>) radiologistRepository.findByType(type_of_radiologist);
    }

    public List<Radiologist> filter_radiologists_by_email(String email_of_radiologist){
        return (List<Radiologist>) radiologistRepository.findByEmail(email_of_radiologist);
    }

    public List<Radiologist> filter_radiologists_by_username(String username_of_radiologist){
        return (List<Radiologist>) radiologistRepository.findByUsername(username_of_radiologist);
    }
    public List<Radiologist> filter_radiologists_by_contact(String contact_of_radiologist){
        return (List<Radiologist>) radiologistRepository.findByContact(contact_of_radiologist);
    }

    public List<Radiologist> findAllRadiologistsSortedByNameAsc() {
        return radiologistRepository.findAllByOrderByNameAsc();
    }

    public List<Radiologist> findAllRadiologistsSortedByNameDesc() {
        return radiologistRepository.findAllByOrderByNameDesc();
    }

    public List<Radiologist> findAllRadiologistsSortedByEmailDesc() {
        return radiologistRepository.findAllByOrderByEmailDesc();
    }

    public List<Radiologist> findAllRadiologistsSortedByEmailAsc() {
        return radiologistRepository.findAllByOrderByEmailAsc();
    }
    public List<Radiologist> findAllRadiologistsSortedByUsernameDesc() {
        return radiologistRepository.findAllByOrderByUsernameDesc();
    }
    public List<Radiologist> findAllRadiologistsSortedByUsernameAsc() {
        return radiologistRepository.findAllByOrderByUsernameAsc();
    }



}
