package com.springboot.thymeleaf.pagination.v2.bootstrap;

import com.github.javafaker.Faker;
import com.springboot.thymeleaf.pagination.v2.model.Resident;
import com.springboot.thymeleaf.pagination.v2.service.ResidentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Random;

// Causes Lombok to generate a logger field.
@Slf4j
// Causes Lombok to generate a constructor with 1 parameter for each field that requires special handling.
@RequiredArgsConstructor
@Component
public class DefaultResidentsLoader implements CommandLineRunner {

    private static final String[] GENDER = {"Male", "Female", "Transgender", "Not to specify"};
    private static final Random RANDOM = new Random();

    private final ResidentService service;
    private final Faker faker;

    @Override
    public void run(String... args) throws Exception {
        loadResidentsData();
    }

    private void loadResidentsData() {
        if (service.getResidentsCount() == 0) {
            for (int x = 0; x < 100; x++) {
                service.save(createNewResident());
            }
            log.info("Default residents are successfully saved in the database.");
        } else {
            log.info("Default residents are already present in the database.");
        }
    }

    private Resident createNewResident() {
        final String firstName = faker.name().firstName();
        final String lastName = faker.name().lastName();
        final String emailAddress = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@company.com";
        final LocalDate birthdate = faker.date().birthday(25, 58).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        final int age = Period.between(birthdate, LocalDate.now()).getYears();
        final String gender = GENDER[RANDOM.nextInt(GENDER.length)];

        return Resident.builder()
                .fullName(firstName + " " + lastName)
                .age((long) age)
                .gender(gender)
                .phoneNumber(faker.phoneNumber().cellPhone())
                .emailAddress(emailAddress)
                .dateOfBirth(birthdate)
                .homeAddress(faker.address().fullAddress())
                .nationality(faker.nation().nationality())
                .firstLanguage(faker.nation().language())
                .build();
    }
}
