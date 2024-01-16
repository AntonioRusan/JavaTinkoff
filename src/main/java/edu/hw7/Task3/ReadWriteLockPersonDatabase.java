package edu.hw7.Task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockPersonDatabase implements PersonDatabase {
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
    public final Map<Integer, Person> idToPerson;
    public final Map<String, List<Person>> nameToPerson;
    public final Map<String, List<Person>> addressToPerson;
    public final Map<String, List<Person>> phoneToPerson;

    public ReadWriteLockPersonDatabase() {
        idToPerson = new HashMap<>();
        nameToPerson = new HashMap<>();
        addressToPerson = new HashMap<>();
        phoneToPerson = new HashMap<>();
    }

    @Override
    public void add(Person person) {
        lock.writeLock().lock();
        try {
            idToPerson.put(person.id(), person);
            nameToPerson.computeIfAbsent(person.name(), k -> new ArrayList<>()).add(person);
            addressToPerson.computeIfAbsent(person.address(), k -> new ArrayList<>()).add(person);
            phoneToPerson.computeIfAbsent(person.phoneNumber(), k -> new ArrayList<>()).add(person);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void delete(int id) {
        lock.writeLock().lock();
        try {
            Person curPerson = idToPerson.getOrDefault(id, null);
            if (curPerson != null) {
                deleteFromMap(nameToPerson, curPerson.name(), id);
                deleteFromMap(addressToPerson, curPerson.address(), id);
                deleteFromMap(phoneToPerson, curPerson.phoneNumber(), id);
                idToPerson.remove(id);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void deleteFromMap(Map<String, List<Person>> modifiedMap, String key, Integer id) {
        var getList = modifiedMap.getOrDefault(key, new ArrayList<>());
        getList.removeIf(item -> item.id() == id);
        modifiedMap.put(key, getList);
    }

    @Override
    public List<Person> findByName(String name) {
        lock.readLock().lock();
        try {
            return nameToPerson.getOrDefault(name, new ArrayList<>());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        lock.readLock().lock();
        try {
            return addressToPerson.getOrDefault(address, new ArrayList<>());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        lock.readLock().lock();
        try {
            return phoneToPerson.getOrDefault(phone, new ArrayList<>());
        } finally {
            lock.readLock().unlock();
        }
    }
}
