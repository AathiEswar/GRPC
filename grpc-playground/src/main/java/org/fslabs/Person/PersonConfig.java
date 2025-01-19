package org.fslabs.Person;

import com.fslabs.models.person1Options.Person;
import com.fslabs.models.person2Options.PersonOuterClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonConfig {
    // for logging purposes
    public static final Logger logger = LoggerFactory.getLogger(PersonConfig.class);

    public static void main(String[] args) {

        Person person1 = Person.newBuilder()
                .setName("Aathi")
                .setAge(22)
                .build();

        logger.info("{}" , person1);
    }
}
