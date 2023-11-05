package edu.hw4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw4.Animal.Sex;
import static edu.hw4.Animal.Sex.F;
import static edu.hw4.Animal.Sex.M;
import static edu.hw4.Animal.Type.BIRD;
import static edu.hw4.Animal.Type.CAT;
import static edu.hw4.Animal.Type.DOG;
import static edu.hw4.Animal.Type.FISH;
import static edu.hw4.Animal.Type.SPIDER;
import static edu.hw4.AnimalStreams.compareByTypeSexName;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnimalsTest {
    public static final List<Animal> animals = new ArrayList<>() {{
        add(new Animal("Chucha", CAT, M, 13, 50, 10, true));
        add(new Animal("Bark", DOG, M, 4, 110, 40, true));
        add(new Animal("Little Kitty Kat", CAT, F, 1, 20, 1, false));
        add(new Animal("Woody Woodpecker Ahah", BIRD, M, 99, 30, 1, true));
        add(new Animal("Peter Parker", SPIDER, M, 20, 70, 5, true));
        add(new Animal("Adun", FISH, M, 10, 40, 15, false));
        add(new Animal("Nika", DOG, F, 3, 60, 25, false));
        add(new Animal("Dori", FISH, F, 1, 50, 6, false));
        add(new Animal("Timka", DOG, F, 9, 100, 50, true));
    }};

    @Test
    @DisplayName("Отсортировать животных по росту от самого маленького к самому большому -> List<Animal>")
    void sortByHeightTask1() {
        // given
        List<Animal> expectedList = animals;
        expectedList.sort(Comparator.comparing(Animal::height));

        // when
        List<Animal> actualList = AnimalStreams.task1(animals);
        // then
        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    @DisplayName("Отсортировать животных по весу от самого тяжелого к самому легкому, выбрать k первых -> List<Animal>")
    void sortByWeightReversedTask2() {
        // given
        int k = 5;
        List<Animal> sortedList = animals;
        sortedList.sort(Comparator.comparing(Animal::weight).reversed());
        List<Animal> expectedList = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            expectedList.add(sortedList.get(i));
        }
        // when
        List<Animal> actualList = AnimalStreams.task2(animals, k);
        // then
        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    @DisplayName("Сколько животных каждого вида -> Map<Animal.Type, Integer>")
    void countEachAnimalTask3() {
        // given
        Map<Animal.Type, Integer> expectedMap = new HashMap<>() {{
            put(BIRD, 1);
            put(FISH, 2);
            put(CAT, 2);
            put(DOG, 3);
            put(SPIDER, 1);
        }};
        // when

        Map<Animal.Type, Integer> actualMap = AnimalStreams.task3(animals);
        // then
        assertThat(actualMap).isEqualTo(expectedMap);
    }

    @Test
    @DisplayName("У какого животного самое длинное имя -> Animal")
    void longestNameTask4() {
        // given
        Animal expected = new Animal("Woody Woodpecker Ahah", BIRD, M, 99, 30, 1, true);
        // when

        Animal actual = AnimalStreams.task4(animals);
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Каких животных больше: самцов или самок -> Sex")
    void maxSexTask5() {
        // given
        Sex expected = M;
        // when
        Sex actual = AnimalStreams.task5(animals);
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Самое тяжелое животное каждого вида -> Map<Animal.Type, Animal>")
    void countEachAnimalTask6() {
        // given
        Map<Animal.Type, Animal> expectedMap = new HashMap<>() {{
            put(BIRD, new Animal("Woody Woodpecker Ahah", BIRD, M, 99, 30, 1, true));
            put(FISH, new Animal("Adun", FISH, M, 10, 40, 15, false));
            put(CAT, new Animal("Chucha", CAT, M, 13, 50, 10, true));
            put(DOG, new Animal("Timka", DOG, F, 9, 100, 50, true));
            put(SPIDER, new Animal("Peter Parker", SPIDER, M, 20, 70, 5, true));
        }};
        // when
        Map<Animal.Type, Animal> actualMap = AnimalStreams.task6(animals);
        // then
        assertThat(actualMap).isEqualTo(expectedMap);
    }

    @Test
    @DisplayName("K-е самое старое животное -> Animal")
    void oldestAnimalTask7() {
        // given
        Animal expected = new Animal("Timka", DOG, F, 9, 100, 50, true);
        // when
        Animal actual = AnimalStreams.task7(animals, 5);
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Самое тяжелое животное среди животных ниже k см -> Optional<Animal>")
    void heavyAnimalWithFilterTask8() {
        // given
        Animal expected = new Animal("Adun", FISH, M, 10, 40, 15, false);
        // when
        Animal actual = AnimalStreams.task8(animals, 50).orElseThrow();
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Самое тяжелое животное среди животных ниже k см -> Optional<Animal>: нет такого животного")
    void exceptionHeavyAnimalWithFilterTask8() {
        // given
        // when
        Optional<Animal> actualOpt = AnimalStreams.task8(animals, 1);
        // then
        assertFalse(actualOpt.isPresent());
    }

    @Test
    @DisplayName("Сколько в сумме лап у животных в списке -> Integer")
    void getSumOfPawsTask9() {
        // given
        Integer expectedSum = 0;
        for (var item : animals) {
            expectedSum += item.paws();
        }
        // when
        Integer actual = AnimalStreams.task9(animals);
        // then
        assertThat(actual).isEqualTo(expectedSum);
    }

    @Test
    @DisplayName("Список животных, возраст у которых не совпадает с количеством лап -> List<Animal>")
    void ageNotEqualsPawsTask10() {
        // given
        List<Animal> expectedList = new ArrayList<>() {{
            add(new Animal("Chucha", CAT, M, 13, 50, 10, true));
            add(new Animal("Woody Woodpecker Ahah", BIRD, M, 99, 30, 1, true));
        }};
        List<Animal> inputList = new ArrayList<>() {{
            add(new Animal("Chucha", CAT, M, 13, 50, 10, true));
            add(new Animal("Little Kitty Kat", CAT, F, 4, 20, 1, false));
            add(new Animal("Woody Woodpecker Ahah", BIRD, M, 99, 30, 1, true));
        }};
        // when
        List<Animal> actual = AnimalStreams.task10(inputList);
        // then
        assertThat(actual).isEqualTo(expectedList);
    }

    @Test
    @DisplayName(
        "Список животных, которые могут укусить (bites == true) и рост которых превышает 100 см -> List<Animal>")
    void bitingHighTask11() {
        // given
        List<Animal> expectedList = new ArrayList<>() {{
            add(new Animal("Bark", DOG, M, 4, 110, 40, true));
        }};
        // when
        List<Animal> actual = AnimalStreams.task11(animals);
        // then
        assertThat(actual).isEqualTo(expectedList);
    }

    @Test
    @DisplayName("Сколько в списке животных, вес которых превышает рост -> Integer")
    void weightOverHeightTask12() {
        // given
        Integer expected = 0;
        for (var item : animals) {
            if (item.weight() > item.height()) {
                expected++;
            }
        }
        // when
        Integer actual = AnimalStreams.task12(animals);
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Список животных, имена которых состоят из более чем двух слов -> List<Animal>")
    void manyWordsNameTask13() {
        // given
        List<Animal> expectedList = new ArrayList<>();
        for (var item : animals) {
            if (item.name().split(" ").length > 2) {
                expectedList.add(item);
            }
        }
        // when
        List<Animal> actualList = AnimalStreams.task13(animals);
        // then
        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    @DisplayName("Есть ли в списке собака ростом более k см -> Boolean")
    void bigDogTask14() {
        // given
        // when
        // then
        assertTrue(AnimalStreams.task14(animals, 100));
        assertFalse(AnimalStreams.task14(animals, 500));
    }

    @Test
    @DisplayName("Найти суммарный вес животных каждого вида, которым от k до l лет -> Map<Animal.Type, Integer>")
    void sumWeightTask15() {
        // given
        int k = 1;
        int l = 20;
        Map<Animal.Type, Integer> expectedMap = new HashMap<>();
        for (var item : animals) {
            if (item.age() >= k && item.age() <= l) {
                expectedMap.put(item.type(), expectedMap.getOrDefault(item.type(), 0) + item.weight());
            }
        }
        // when
        Map<Animal.Type, Integer> actualMap = AnimalStreams.task15(animals, k, l);
        // then
        assertThat(actualMap).isEqualTo(expectedMap);
    }

    @Test
    @DisplayName("Список животных, отсортированный по виду, затем по полу, затем по имени -> List<Animal>")
    void sumWeightTask16() {
        // given
        List<Animal> expectedList = animals;
        expectedList.sort(compareByTypeSexName);
        // when
        List<Animal> actualList = AnimalStreams.task16(animals);
        // then
        assertThat(actualList).isEqualTo(expectedList);
    }

    @Test
    @DisplayName(
        "Правда ли, что пауки кусаются чаще, чем собаки -> Boolean (если данных для ответа недостаточно, вернуть false) - собаки чаще")
    void spidersAndDogsDogsWinTask17() {
        // given
        Boolean expected = false;
        // when
        Boolean actual = AnimalStreams.task17(animals);
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName(
        "Правда ли, что пауки кусаются чаще, чем собаки -> Boolean (если данных для ответа недостаточно, вернуть false) - недостаточно данных")
    void spidersAndDogsNoDataTask17() {
        // given
        Boolean expected = false;
        List<Animal> inputList = new ArrayList<>() {{
            add(new Animal("Peter Parker", SPIDER, M, 20, 70, 5, true));
        }};
        // when
        Boolean actual = AnimalStreams.task17(inputList);
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName(
        "Правда ли, что пауки кусаются чаще, чем собаки -> Boolean (если данных для ответа недостаточно, вернуть false) - пауки чаще")
    void spidersAndDogsSpidersWinTask17() {
        // given
        Boolean expected = true;
        List<Animal> inputList = new ArrayList<>() {{
            add(new Animal("Peter Parker", SPIDER, M, 20, 70, 5, true));
            add(new Animal("Nika", DOG, F, 3, 60, 25, false));
        }};
        // when
        Boolean actual = AnimalStreams.task17(inputList);
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Найти самую тяжелую рыбку в 2-х или более списках -> Animal")
    void heaviestFishTask18() {
        // given
        List<Animal> inputListFirst = new ArrayList<>() {{
            add(new Animal("Nemo", FISH, M, 10, 40, 150, false));
            add(new Animal("GoldFish", FISH, M, 10, 40, 20, false));
        }};
        List<Animal> inputListSecond = new ArrayList<>() {{
            add(new Animal("Bulya", FISH, M, 10, 40, 70, false));
            add(new Animal("Jerry", CAT, M, 13, 50, 12, true));
        }};
        List<List<Animal>> input = new ArrayList<>() {{
            add(inputListFirst);
            add(animals);
            add(inputListSecond);
        }};
        Animal expected = new Animal("Nemo", FISH, M, 10, 40, 150, false);
        // when
        Animal actual = AnimalStreams.task18(input);
        // then
        assertThat(actual).isEqualTo(expected);
    }

}
