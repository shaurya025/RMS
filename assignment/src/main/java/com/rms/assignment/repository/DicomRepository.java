package com.rms.assignment.repository;

import com.rms.assignment.model.Dicom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface DicomRepository extends JpaRepository<Dicom, Integer> {

   @Query
    List<Dicom> findByRadiologistId(@Param("radiologist_id") int radiologist_id);



}
