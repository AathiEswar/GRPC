package org.fslabs.Serialization;

import com.fslabs.models.scalarTypes.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SerialAndDeserial {
    public static final Logger logger = LoggerFactory.getLogger(SerialAndDeserial.class);

    // declase PATH to read and write
    private static final Path PATH = Path.of("person.out");

    public static void main(String[] args) throws IOException {
        Person person1 = Person.newBuilder()
                .setFullName("Daisku Kambae")
                .setAge(31)
                .setEmployed(true)
                .setBankAccountNumber(1234567890126L)
                .setSalary(Integer.MAX_VALUE)
                .setBankBalance(-100987)
                .build();

        serialize(person1);

        Person dPerson = deserialize();

        logger.info("Deserialized Data : {}" , dPerson);

        logger.info("Equality : {}" , person1.equals(dPerson));

        // byte stream
        logger.info("Byte array : {}" , dPerson.toByteArray());

    }

    public static void serialize(Person person) throws IOException {
        person.writeTo(Files.newOutputStream(PATH));
    }

    public static Person deserialize() throws IOException {
        return Person.parseFrom(Files.newInputStream(PATH));
    }
}
