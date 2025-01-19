package org.fslabs.Composition;

import com.fslabs.models.composition.Address;
import com.fslabs.models.composition.School;
import com.fslabs.models.composition.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Composition {

    private static final Logger logger = LoggerFactory.getLogger(Composition.class);

    public static void main(String[] args) {
        Address address = Address.newBuilder()
                .setCity("Chennai")
                .setState("Tamil Nadu")
                .setStreet("abc street")
                .build();

        Student student = Student.newBuilder()
                .setId(1)
                .setName("Aathi Eswar")
                .setAddress(address)
                .build();

        School school = School.newBuilder()
                .setName("Velammal")
                .setAddress(address.toBuilder().setStreet("xyz street").build())
                .build();

        logger.info("Address : {}", address);
        logger.info("Student : {}", student);
        logger.info("School: {}", school);
    }
}
