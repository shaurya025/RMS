package com.rms.assignment.controller;

import com.rms.assignment.model.Dicom;
import com.rms.assignment.payload.PatientDicomDetails;
import com.rms.assignment.service.DicomService;
import com.rms.assignment.service.radiologistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class FileController {

    @Autowired
    private DicomService dicom_service;
    private radiologistService radiologist_service;

    @PostMapping(value = "/upload/{radiologist_id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadImageToFileSystem(@RequestPart("file") MultipartFile file, @PathVariable("radiologist_id") int radiologist_id) throws IOException {

        return dicom_service.uploadImageToFileSystem(file, radiologist_id);

    }

    @GetMapping("/view/{radiologist_id}")
    public List<Dicom> getAllDicomsByRadiologistId(@PathVariable("radiologist_id") int radiologist_id){

        return (List<Dicom>)dicom_service.getAllDicomsByRadiologistId(radiologist_id);

    }

    @GetMapping("/viewPatientDetails/{dicomId}")
    public List<String> getPatientDicomDetails(@PathVariable("dicomId") int dicomId){

        List<String> response=  dicom_service.getPatientDicomDetails(dicomId);
        return response;

    }


    @GetMapping("/downloadImage/{dicomId}")
    public ResponseEntity<?> downLoadImage (@PathVariable("dicomId") int dicomId, HttpServletRequest request) throws IOException{
        Resource resource= dicom_service.downloadImage(dicomId);
        String mimeType;

        try{
            mimeType= request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch(IOException e){
            mimeType=  MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        mimeType= (mimeType==null)? MediaType.APPLICATION_OCTET_STREAM_VALUE : mimeType;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(resource);
    }

    @PutMapping("/saveComment/{dicomId}")
    public void CommentOnDicom (@PathVariable("dicomId")int dicomId,@RequestParam("comment") String comment){
        dicom_service.CommentOnDicom(dicomId,comment);
    }

    @GetMapping("/getComment/{dicomId}")
    public String getCommentyrr(@PathVariable("dicomId")int dicomId){
        return dicom_service.getCommentyrr(dicomId);
    }

    @PutMapping("/update/{dicomId}")
    public String updateDicom(@PathVariable("dicomId")int dicomId,MultipartFile file) throws IOException {
       return dicom_service.updateDicom(dicomId,file);
    }

}
