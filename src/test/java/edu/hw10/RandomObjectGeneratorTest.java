package edu.hw10;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomObjectGeneratorTest {
    public static class Person {
        @RandomObjectGenerator.NotNull
        public String name;
        @RandomObjectGenerator.NotNull
        @RandomObjectGenerator.Min(0)
        @RandomObjectGenerator.Max(150)
        public Integer age;

        @RandomObjectGenerator.Min(0)
        @RandomObjectGenerator.Max(200)
        public Double height;

        public Person() {
        }

        public Person(String name, Integer age, Double height) {
            this.name = name;
            this.age = age;
            this.height = height;
        }

        public static Person create(String name, Integer age, Double height) {
            return new Person(name, age, height);
        }
    }

    public record PersonRecord(
        @RandomObjectGenerator.NotNull String name,
        @RandomObjectGenerator.NotNull
        @RandomObjectGenerator.Min(0)
        @RandomObjectGenerator.Max(150) Integer age,
        @RandomObjectGenerator.Min(0)
        @RandomObjectGenerator.Max(200)
        Double height) {
    }

    @Test
    void testNextObjectPOJO() {
        RandomObjectGenerator rog = new RandomObjectGenerator();
        Person person = rog.nextObject(Person.class);
        assertNotNull(person);
        assertNotNull(person.name);
        assertTrue(person.age >= 0 && person.age <= 150);
        assertTrue(person.height == null || (person.height >= 0 && person.height <= 200));
    }

    @Test
    void testNextObjectPOJOWithFactoryMethod() {
        RandomObjectGenerator rog = new RandomObjectGenerator();
        Person person = rog.nextObject(Person.class, "create");
        assertNotNull(person);
        assertNotNull(person.name);
        assertTrue(person.age >= 0 && person.age <= 150);
        assertTrue(person.height == null || (person.height >= 0 && person.height <= 200));
    }

    @Test
    void testNextObjectRecord() {
        RandomObjectGenerator rog = new RandomObjectGenerator();
        PersonRecord personRecord = rog.nextObject(PersonRecord.class);
        assertNotNull(personRecord);
        assertNotNull(personRecord.name);
        assertTrue(personRecord.age >= 0 && personRecord.age <= 150);
        assertTrue(personRecord.height == null || (personRecord.height >= 0 && personRecord.height <= 200));
    }
}
