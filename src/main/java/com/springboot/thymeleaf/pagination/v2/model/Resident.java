package com.springboot.thymeleaf.pagination.v2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "resident")
// Causes Lombok to generate toString(), equals(), hashCode(), getter() & setter(), and Required arguments constructor in one go.
@Data
// Causes Lombok to implement the Builder design pattern for the Pojo class.
// Usage can be seen in DefaultResidentsLoader.java -> createNewResident() method.
@Builder
// Causes Lombok to generate a constructor with no parameters.
@NoArgsConstructor
// Causes Lombok to generate a constructor with 1 parameter for each field in your class.
@AllArgsConstructor
@Component
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "full_name", nullable = false)
    String fullName;
    @Column(name = "age", nullable = false)
    Long age;
    @Column(name = "gender", nullable = false)
    String gender;
    @Column(name = "phone_number", unique = false)
    String phoneNumber;
    @Column(name = "email_address", nullable = false, unique = false)
    String emailAddress;
    @Column(name = "date_of_birth", nullable = false)
    LocalDate dateOfBirth;
    @Column(name = "home_address")
    String homeAddress;
    @Column(name = "nationality")
    String nationality;
    @Column(name = "first_language")
    String firstLanguage;

}
