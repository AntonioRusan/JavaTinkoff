package edu.hw4;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import static edu.hw4.Animal.Sex.F;
import static edu.hw4.Animal.Sex.M;

public class AnimalStreams {
    private AnimalStreams() {
    }

    private static final int BIG_HEIGHT = 100;

    public static List<Animal> task1(List<Animal> animals) {
        return animals.stream()
            .sorted(Comparator.comparing(Animal::height)).collect(Collectors.toList());
    }

    public static List<Animal> task2(List<Animal> animals, int k) {
        return animals.stream()
            .sorted(Comparator.comparing(Animal::weight).reversed()).limit(k)
            .collect(Collectors.toList());
    }

    public static Map<Animal.Type, Integer> task3(List<Animal> animals) {
        //return animals.stream().collect(Collectors.groupingBy(Animal::type, Collectors.counting()));
        return animals.stream()
            .collect(Collectors.groupingBy(Animal::type, Collectors.reducing(0, x -> 1, Integer::sum)));
    }

    public static Animal task4(List<Animal> animals) {
        return animals.stream()
            .max(Comparator.comparing((animal -> animal.name().length())))
            .orElseThrow();
    }

    public static Animal.Sex task5(List<Animal> animals) {
        Map<Animal.Sex, Long> sexToCount =
            animals.stream()
                .collect(Collectors.groupingBy(Animal::sex, Collectors.counting()));
        return (sexToCount.get(M) > sexToCount.get(F)) ? M : F;
    }

    public static Map<Animal.Type, Animal> task6(List<Animal> animals) {
        return animals.stream()
            .collect(Collectors.groupingBy(Animal::type, Collectors.maxBy(Comparator.comparing(Animal::weight))))
            .entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, keyValue -> keyValue.getValue().orElseThrow()));
    }

    public static Animal task7(List<Animal> animals, int k) {
        return animals.stream()
            .sorted(Comparator.comparing(Animal::age))
            .skip(animals.size() - k).findFirst()
            .orElseThrow();
    }

    public static Optional<Animal> task8(List<Animal> animals, int k) {
        return animals.stream()
            .filter(animal -> animal.height() < k)
            .max(Comparator.comparing(Animal::weight));
    }

    public static Integer task9(List<Animal> animals) {
        return animals.stream()
            .map(Animal::paws)
            .reduce(0, Integer::sum);
    }

    public static List<Animal> task10(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> animal.age() != animal.paws())
            .collect(Collectors.toList());
    }

    public static List<Animal> task11(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> (animal.bites()) && (animal.height() > BIG_HEIGHT))
            .collect(Collectors.toList());
    }

    public static Integer task12(List<Animal> animals) {
        return Long.valueOf(
            animals.stream()
                .filter(animal -> animal.weight() > animal.height())
                .count()
        ).intValue();
    }

    public static List<Animal> task13(List<Animal> animals) {
        return animals.stream()
            .filter(animal -> animal.name().split(" ").length > 2)
            .collect(Collectors.toList());
    }

    public static Boolean task14(List<Animal> animals, int k) {
        return animals.stream()
            .anyMatch(animal -> animal.type() == Animal.Type.DOG && animal.height() > k);
    }

    public static Map<Animal.Type, Integer> task15(List<Animal> animals, int k, int l) {
        return animals.stream()
            .filter(animal -> animal.age() >= k && animal.age() <= l)
            .collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(Animal::weight)));
    }

    public static Comparator<Animal> compareByTypeSexName = Comparator
        .comparing(Animal::type)
        .thenComparing(Animal::sex)
        .thenComparing(Animal::name);

    public static List<Animal> task16(List<Animal> animals) {
        return animals.stream()
            .sorted(compareByTypeSexName)
            .collect(Collectors.toList());
    }

    public static Boolean task17(List<Animal> animals) {
        var typeAndBiteCount = animals.stream()
            .collect(Collectors.groupingBy(Animal::type, Collectors.filtering(
                Animal::bites,
                Collectors.counting()
            )));
        if (typeAndBiteCount.containsKey(Animal.Type.SPIDER) && typeAndBiteCount.containsKey(Animal.Type.DOG)) {
            return typeAndBiteCount.get(Animal.Type.SPIDER) > typeAndBiteCount.get(Animal.Type.DOG);
        } else {
            return false;
        }
    }

    public static Animal task18(List<List<Animal>> animalLists) {
        return animalLists.stream()
            .map(animalList -> animalList.stream()
                .filter(animal -> animal.type() == Animal.Type.FISH)
                .max(Comparator.comparing(Animal::weight))).flatMap(Optional::stream)
            .max(Comparator.comparing(Animal::weight)).orElseThrow();

    }

    public static Map<String, Set<ValidationError>> task19(List<Animal> animalList) {
        return animalList.stream()
            .filter(animal -> !animal.validateAnimal().isEmpty())
            .collect(Collectors.toMap(Animal::name, Animal::validateAnimal));
    }

    public static Map<String, String> task20(List<Animal> animalList) {
        return animalList.stream()
            .filter(animal -> !animal.validateAnimal().isEmpty())
            .collect(Collectors.toMap(Animal::name, Animal::validateAnimal))
            .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry ->
                entry.getValue().stream().sorted(Comparator.comparing(ValidationError::fieldName))
                    .map(error -> error.fieldName() + ": " + error.errorType().toString())
                    .collect(Collectors.joining("; "))));
    }
}


