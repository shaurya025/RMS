package com.rms.assignment.repository;

import com.rms.assignment.model.Radiologist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RadiologistRepository extends JpaRepository<Radiologist, Integer> {
    @Query("SELECT r FROM Radiologist r WHERE LOWER(r.name) LIKE CONCAT(LOWER(:prefix), '%')")
    List<Radiologist> findByNameStartsWith(@Param("prefix") String prefix);

    @Query("SELECT r FROM Radiologist r WHERE LOWER(r.type) LIKE CONCAT(LOWER(:type_of_radiologist), '%')")
    List<Radiologist>findByType(@Param("type_of_radiologist") String type_of_radiologist);

   @Query("SELECT r FROM Radiologist r WHERE LOWER(r.email) LIKE CONCAT(LOWER(:email_of_radiologist), '%')")
    List<Radiologist>findByEmail(@Param("email_of_radiologist") String email_of_radiologist);

    @Query("SELECT r FROM Radiologist r WHERE LOWER(r.username) LIKE CONCAT(LOWER(:username_of_radiologist), '%')")
    List<Radiologist>findByUsername(@Param("username_of_radiologist") String username_of_radiologist);

    @Query("SELECT r FROM Radiologist r WHERE (r.contact) LIKE CONCAT(:contact_of_radiologist, '%')")
    List<Radiologist>findByContact(@Param("contact_of_radiologist") String contact_of_radiologist);

    List<Radiologist>findAllByOrderByNameAsc();
    List<Radiologist>findAllByOrderByNameDesc();
    List<Radiologist>findAllByOrderByEmailDesc();
    List<Radiologist>findAllByOrderByEmailAsc();
    List<Radiologist>findAllByOrderByUsernameDesc();
    List<Radiologist>findAllByOrderByUsernameAsc();


}
