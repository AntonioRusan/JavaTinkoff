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
        public int age;

        @RandomObjectGenerator.Min(0)
        @RandomObjectGenerator.Max(200)
        public Integer height;

        public Person() {
        }

        public Person(String name, int age, Integer height) {
            this.name = name;
            this.age = age;
            this.height = height;
        }

        public static Person create() {
            return new Person();
        }
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
}
