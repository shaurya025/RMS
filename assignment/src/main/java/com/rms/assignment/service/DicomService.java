package com.rms.assignment.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;

import com.rms.assignment.exception.DicomNotFoundException;
import com.rms.assignment.model.Dicom;
import com.rms.assignment.model.Radiologist;
import com.rms.assignment.payload.PatientDicomDetails;
import com.rms.assignment.repository.DicomRepository;
import org.springframework.core.io.Resource;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
public class DicomService {

    private String FOLDER_PATH = "C:\\Users\\saxen\\Desktop\\assignment\\src\\main\\resources\\static\\dicoms\\";

    @Autowired
    public DicomRepository dicomRepository;

    @Autowired
    public radiologistService radiologist_service;

    public String uploadImageToFileSystem(MultipartFile file,int radiologist_id) throws IOException {


        Random random = new Random();
        String filePath=FOLDER_PATH+file.getOriginalFilename() + random.nextInt();



        Dicom dicom = new Dicom();
        dicom.setFileUrl(filePath);
        dicom.setRadiologist(radiologist_service.getRadiologist(radiologist_id));


        File newFile = new File(filePath);
        file.transferTo(newFile);

        Path dicomFile = newFile.toPath();
        DicomInputStream dis = new DicomInputStream(dicomFile.toFile());


        @SuppressWarnings("deprecation")
        Attributes fileAttributes = dis.readDataset(-1 , -1);

        LocalDate localDate = LocalDate.of(1970, Month.JANUARY, 01);

        // DCM4CHE reads patient Attributes
        String dicomPatientId = fileAttributes.getString(Tag.PatientID) == null ? "NONE" : fileAttributes.getString(Tag.PatientID);
        String patientName = fileAttributes.getString(Tag.PatientName) == null ? "NONE" : fileAttributes.getString(Tag.PatientName);
        Date date = fileAttributes.getDate(Tag.StudyDate) == null ? Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : fileAttributes.getDate(Tag.StudyDate);
        String modality = fileAttributes.getString(Tag.Modality) == null ? "NONE" : fileAttributes.getString(Tag.Modality) ;
        String age = fileAttributes.getString(Tag.PatientAge) == null ? "NONE" : fileAttributes.getString(Tag.PatientAge);
        Date birthDate = fileAttributes.getDate(Tag.PatientBirthDate) == null ? Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())  : fileAttributes.getDate(Tag.PatientBirthDate);
        String patientSex = fileAttributes.getString(Tag.PatientSex) == null ? "NONE" : fileAttributes.getString(Tag.PatientSex);


        dicom.setPatientId(dicomPatientId);
        dicom.setPatientName(patientName);
        dicom.setStudyDate(date);
        dicom.setModality(modality);
        dicom.setAge(age);
        dicom.setBirthDate(birthDate);
        dicom.setPatientSex(patientSex);





        Dicom fileData=dicomRepository.save(dicom);

        if (fileData != null) {
            return  filePath;
        }
        return null;
    }


    public Resource downloadImage(int dicomId) throws IOException{

        Optional<Dicom> optional_dicom= dicomRepository.findById(dicomId);
        Dicom dicom;
        if(!optional_dicom.isPresent()){
            throw new DicomNotFoundException("Dicom not available");
        }

        dicom=optional_dicom.get();
        String fileUrl = dicom.getFileUrl();



// Convert URI to file system path
        Path path = Paths.get(fileUrl);

        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file", e);
        }

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("The file doesn't exist or is not readable");
        }

    }

   public List<String>  getPatientDicomDetails(int dicom_id) {
         Optional<Dicom> optional_dicom= dicomRepository.findById(dicom_id);
         Dicom dicom;
         if(!optional_dicom.isPresent()){
             throw new DicomNotFoundException("Dicom not available");
         }

             dicom=optional_dicom.get();

       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

       // Convert Date object to string
       String StudydateString = dateFormat.format(dicom.getStudyDate());
       String BirthDateString = dateFormat.format(dicom.getBirthDate());

       List<String>patientDicomDetails= new ArrayList<>();

       patientDicomDetails.add(dicom.getPatientId());
       patientDicomDetails.add(dicom.getPatientName());
       patientDicomDetails.add(dicom.getModality());
       patientDicomDetails.add(StudydateString);
       patientDicomDetails.add(dicom.getAge());

       patientDicomDetails.add(dicom.getPatientSex());
       patientDicomDetails.add(BirthDateString);


       // PatientDicomDetails patientDicomDetails = new PatientDicomDetails(dicom.getPatientId(),dicom.getPatientName(),dicom.getModality(),StudydateString,dicom.getAge(),dicom.getPatientSex(),BirthDateString);
        return patientDicomDetails;
   }



   public List<Dicom> getAllDicomsByRadiologistId(int radiologist_id){
       return dicomRepository.findByRadiologistId(radiologist_id);
   }

   public void CommentOnDicom(int dicomId,String comment){
       Optional<Dicom> optional_dicom= dicomRepository.findById(dicomId);
       Dicom dicom;
       if(!optional_dicom.isPresent()){
           throw new DicomNotFoundException("Dicom not available");
       }

       dicom=optional_dicom.get();
       dicom.setComment(comment);
       dicomRepository.save(dicom);

   }

   public String getCommentyrr(int dicomId){
       Optional<Dicom> optional_dicom= dicomRepository.findById(dicomId);
       Dicom dicom;
       if(!optional_dicom.isPresent()){
           throw new DicomNotFoundException("Dicom not available");
       }

       dicom=optional_dicom.get();
       return dicom.getComment();
   }

   public String updateDicom(int dicomId,MultipartFile file) throws IOException{

       Random random = new Random();
       String filePath=FOLDER_PATH+file.getOriginalFilename() + random.nextInt();



       Optional<Dicom> optional_dicom= dicomRepository.findById(dicomId);
       Dicom dicom;
       if(!optional_dicom.isPresent()){
           throw new DicomNotFoundException("Dicom not available");
       }

       dicom=optional_dicom.get();
       dicom.setFileUrl(filePath);
       dicom.setRadiologist(dicom.getRadiologist());


       File newFile = new File(filePath);
       file.transferTo(newFile);

       Path dicomFile = newFile.toPath();
       DicomInputStream dis = new DicomInputStream(dicomFile.toFile());


       @SuppressWarnings("deprecation")
       Attributes fileAttributes = dis.readDataset(-1 , -1);

       LocalDate localDate = LocalDate.of(1970, Month.JANUARY, 01);

       // DCM4CHE reads patient Attributes
       String dicomPatientId = fileAttributes.getString(Tag.PatientID) == null ? "NONE" : fileAttributes.getString(Tag.PatientID);
       String patientName = fileAttributes.getString(Tag.PatientName) == null ? "NONE" : fileAttributes.getString(Tag.PatientName);
       Date date = fileAttributes.getDate(Tag.StudyDate) == null ? Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : fileAttributes.getDate(Tag.StudyDate);
       String modality = fileAttributes.getString(Tag.Modality) == null ? "NONE" : fileAttributes.getString(Tag.Modality) ;
       String age = fileAttributes.getString(Tag.PatientAge) == null ? "NONE" : fileAttributes.getString(Tag.PatientAge);
       Date birthDate = fileAttributes.getDate(Tag.PatientBirthDate) == null ? Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())  : fileAttributes.getDate(Tag.PatientBirthDate);
       String patientSex = fileAttributes.getString(Tag.PatientSex) == null ? "NONE" : fileAttributes.getString(Tag.PatientSex);


       dicom.setPatientId(dicomPatientId);
       dicom.setPatientName(patientName);
       dicom.setStudyDate(date);
       dicom.setModality(modality);
       dicom.setAge(age);
       dicom.setBirthDate(birthDate);
       dicom.setPatientSex(patientSex);





       Dicom fileData=dicomRepository.save(dicom);
       if (fileData != null) {
           return  filePath;
       }
       return null;



   }


}