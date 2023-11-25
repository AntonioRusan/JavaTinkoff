package edu.hw7.Task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SynchronizedPersonDatabase implements PersonDatabase {
    public final Map<Integer, Person> idToPerson;
    public final Map<String, List<Person>> nameToPerson;
    public final Map<String, List<Person>> addressToPerson;
    public final Map<String, List<Person>> phoneToPerson;

    public SynchronizedPersonDatabase() {
        idToPerson = new HashMap<>();
        nameToPerson = new HashMap<>();
        addressToPerson = new HashMap<>();
        phoneToPerson = new HashMap<>();
    }

    @Override
    public synchronized void add(Person person) {
        idToPerson.put(person.id(), person);
        nameToPerson.computeIfAbsent(person.name(), k -> new ArrayList<>()).add(person);
        addressToPerson.computeIfAbsent(person.address(), k -> new ArrayList<>()).add(person);
        phoneToPerson.computeIfAbsent(person.phoneNumber(), k -> new ArrayList<>()).add(person);
    }

    @Override
    public synchronized void delete(int id) {
        Person curPerson = idToPerson.getOrDefault(id, null);
        if (curPerson != null) {
            deleteFromMap(nameToPerson, curPerson.name(), id);
            deleteFromMap(addressToPerson, curPerson.address(), id);
            deleteFromMap(phoneToPerson, curPerson.phoneNumber(), id);
            idToPerson.remove(id);
        }
    }

    private synchronized void deleteFromMap(Map<String, List<Person>> modifiedMap, String key, Integer id) {
        var getList = modifiedMap.getOrDefault(key, new ArrayList<>());
        getList.removeIf(item -> item.id() == id);
        modifiedMap.put(key, getList);
    }

    @Override
    public synchronized List<Person> findByName(String name) {
        return nameToPerson.getOrDefault(name, new ArrayList<>());
    }

    @Override
    public synchronized List<Person> findByAddress(String address) {
        return addressToPerson.getOrDefault(address, new ArrayList<>());
    }

    @Override
    public synchronized List<Person> findByPhone(String phone) {
        return phoneToPerson.getOrDefault(phone, new ArrayList<>());
    }
}
