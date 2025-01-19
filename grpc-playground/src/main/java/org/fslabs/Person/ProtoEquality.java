package org.fslabs.Person;

import com.fslabs.models.person1Options.Person;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtoEquality {
    public static final Logger logger = LoggerFactory.getLogger(ProtoEquality.class);

    public static void main(String[] args) {

        // initialize objects
        Person person1 = createPerson();
        Person person2 = createPerson();

        // Types of Compare : == and .equals()

        // .equals -> compares values
        logger.info("using equlas : {}" , person1.equals(person2));

        // using == , compares the reference
        logger.info("usign == : {}" , person1 == person2);

        // mutability -> no , coz there is no method available to change the values
        // person2.setAge() is not available

        // create another instance with existing object

        // use toBuilder();

        Person person3 =  person1.toBuilder()
                .setAge(16)
                .setName("Rin Itoshi")
                .build(); // new instance from person 1

        logger.info("person 3 : {}" , person3 );

        // clear a particular field use the clear method while building it
        Person person4 =  person1.toBuilder()
                .clearName()
                .setAge(100)
                .build(); // new instance from person 1

        logger.info("person 4 : {}" , person4);

        //comparing person 4 with person 1 ?
        logger.info("person1 equals person4? {}" , person1.equals(person4));
        logger.info("person1 == person4? {}" , person1 == person4);

        // both are false coz newBuilder always create a new instance
    }



    // method to create static Object from proto generated code
    public static Person createPerson(){
        return Person.newBuilder()
                .setName("Isagi")
                .setAge(16)
                .build();
    }
}
