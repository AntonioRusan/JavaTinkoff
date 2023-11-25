package edu.hw7;

import edu.hw7.Task3.Person;
import edu.hw7.Task3.SynchronizedPersonDatabase;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SuppressWarnings("MagicNumber")
public class Task3Test {
    private final static Logger LOGGER = LogManager.getLogger();

    @Test
    @DisplayName("Проверка многопоточного добавления Person")
    void addManyPersonsThreads() {
        //given
        SynchronizedPersonDatabase db = new SynchronizedPersonDatabase();
        int numOfThreads = 10_000;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numOfThreads; i++) {
            int finalI = i;
            threads.add(new Thread(() -> db.add(new Person(
                finalI,
                "Ivan" + String.valueOf(finalI),
                "City-17",
                "8-800-555-35-35"
            ))));
        }
        for (var thread : threads) {
            thread.start();
        }
        try {
            for (var thread : threads) {
                thread.join();
            }
        } catch (Exception ex) {
            LOGGER.info("Exception: " + ex.getMessage());
        }
        //then
        assertThat(db.idToPerson.size()).isEqualTo(numOfThreads);
        assertThat(db.nameToPerson.size()).isEqualTo(numOfThreads);
        assertThat(db.addressToPerson.size()).isEqualTo(1);
        assertThat(db.phoneToPerson.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Проверка многопоточного поиска Person")
    void findPersonsThreads() {
        //given
        SynchronizedPersonDatabase db = new SynchronizedPersonDatabase();
        //when
        List<Thread> threads = new ArrayList<>() {{
            add(new Thread(() -> db.add(new Person(1, "Ivan", "City-17", "8-800-555-35-35"))));
            add(new Thread(() -> db.add(new Person(2, "Vitaly", "Garage", "8-999-666-14-01"))));
            add(new Thread(() -> db.add(new Person(3, "Petya", "City-17", "8-800-555-35-39"))));
            add(new Thread(() -> db.findByName("Ivan")));
            add(new Thread(() -> db.findByAddress("City-17")));
            add(new Thread(() -> db.findByPhone("8-999-666-14-01")));
            add(new Thread(() -> db.add(new Person(4, "Vanya", "City-17", "8-800-555-35-39"))));
        }};
        for (var thread : threads) {
            thread.start();
        }
        try {
            for (var thread : threads) {
                thread.join();
            }
        } catch (Exception ex) {
            LOGGER.info("Exception: " + ex.getMessage());
        }
        assertThat(db.idToPerson.size()).isEqualTo(4);
        assertThat(db.nameToPerson.size()).isEqualTo(4);
        assertThat(db.addressToPerson.size()).isEqualTo(2);
        assertThat(db.phoneToPerson.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Проверка однопоточного поиска Person")
    void findPersons() {
        //given
        SynchronizedPersonDatabase db = new SynchronizedPersonDatabase();
        //when
        List<Thread> threads = new ArrayList<>() {{
            add(new Thread(() -> db.add(new Person(1, "Ivan", "City-17", "8-800-555-35-35"))));
            add(new Thread(() -> db.add(new Person(2, "Vitaly", "Garage", "8-999-666-14-01"))));
            add(new Thread(() -> db.add(new Person(3, "Petya", "City-17", "8-800-555-35-39"))));
            add(new Thread(() -> db.add(new Person(4, "Vanya", "City-17", "8-800-555-35-39"))));
        }};
        for (var thread : threads) {
            thread.start();
        }
        try {
            for (var thread : threads) {
                thread.join();
            }
        } catch (Exception ex) {
            LOGGER.info("Exception: " + ex.getMessage());
        }
        var byName = db.findByName("Ivan");
        var byAddress = db.findByAddress("City-17");
        var byPhone = db.findByPhone("8-999-666-14-01");
        assertThat(byName.size()).isEqualTo(1);
        assertThat(byAddress.size()).isEqualTo(3);
        assertThat(byPhone.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Проверка многопоточного удаления Person")
    void delPersonsThreads() {
        //given
        SynchronizedPersonDatabase db = new SynchronizedPersonDatabase();
        //when
        db.add(new Person(1, "Ivan", "City-17", "8-800-555-35-35"));
        List<Thread> threads = new ArrayList<>() {{
            add(new Thread(() -> db.add(new Person(2, "Vitaly", "Garage", "8-999-666-14-01"))));
            add(new Thread(() -> db.delete(1)));
            add(new Thread(() -> db.add(new Person(3, "Petya", "City-17", "8-800-555-35-39"))));
            add(new Thread(() -> db.add(new Person(4, "Vanya", "City-17", "8-800-555-35-39"))));
        }};
        for (var thread : threads) {
            thread.start();
        }
        try {
            for (var thread : threads) {
                thread.join();
            }
        } catch (Exception ex) {
            LOGGER.info("Exception: " + ex.getMessage());
        }
        var byName = db.findByName("Ivan");
        assertThat(byName.size()).isEqualTo(0);
    }
}
