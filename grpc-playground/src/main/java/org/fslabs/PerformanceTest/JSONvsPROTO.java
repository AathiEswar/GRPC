package org.fslabs.PerformanceTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fslabs.models.scalarTypes.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Runnable -> is a functional Interface with sole purpose of running a task given to it

public class JSONvsPROTO {
    private static final Logger logger = LoggerFactory.getLogger(JSONvsPROTO.class);
    private static final ObjectMapper objMapper = new ObjectMapper();

    public static void main(String[] args) {
        Person person1 = Person.newBuilder()
                .setFullName("Daisku Kambae")
                .setAge(31)
                .setEmployed(true)
                .setBankAccountNumber(1234567890126L)
                .setSalary(Integer.MAX_VALUE)
                .setBankBalance(-100987)
                .build();

        PersonJSON jsonPerson = new PersonJSON(
                "Gojo Satoru",
                31,
                false,
                0,
                99999999999L,
                10000000
        );

        for (int i = 0; i < 5; i++) {
            runTest("json", () -> json(jsonPerson));
            runTest("proto", () -> proto(person1));
        }

        logger.info("Json length : {}", jsonLength(jsonPerson));
        logger.info("Proto length : {}", protoLen(person1));
    }

    public static void proto(Person person) {
        try {
            byte[] byteData = person.toByteArray();
            Person.parseFrom(byteData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int protoLen(Person person) {
        try {
            byte[] byteData = person.toByteArray();
            Person.parseFrom(byteData);
            return byteData.length;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void json(PersonJSON person) {
        try {
            byte[] byteData = objMapper.writeValueAsBytes(person);
            objMapper.readValue(byteData, PersonJSON.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int jsonLength(PersonJSON person) {
        try {
            byte[] byteData = objMapper.writeValueAsBytes(person);
            objMapper.readValue(byteData, PersonJSON.class);
            return byteData.length;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void runTest(String testName, Runnable runnable) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 1_000_000; i++) {
            runnable.run();
        }

        long end = System.currentTimeMillis();
        logger.info("Time taken for {} is {} ms", testName, (end - start));
    }
}
