package org.fslabs.ScalarTypes;

import com.fslabs.models.scalarTypes.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScalarTypes {
    public static final Logger logger = LoggerFactory.getLogger(ScalarTypes.class);

    public static void main(String[] args) {

        // if no value is given then its not assigned
        Person person1 = Person.newBuilder()
                .setFullName("Daisku Kambae")
                .setAge(31)
                .setEmployed(true)
                .setBankAccountNumber(1234567890126L)
                .setSalary(Integer.MAX_VALUE)
                .setBankBalance(-100987)
                .build();

        logger.info("person 1 : {}" , person1);
    }
}
