package cz.itnetwork.service;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {

    /**
     * Generate random LocalDate between two dates
     *
     * @param startDate - start Date
     * @param endDate   - end Date
     * @return generated LocalDate
     */
    public LocalDate generateRandomDate(LocalDate startDate, LocalDate endDate) {
        // Calculate the difference between start and end dates
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);

        // Convert the random epoch day back to a LocalDate
        return LocalDate.ofEpochDay(randomEpochDay);
    }

    /**
     * Generate random numbers (used for generating invoice numbers and so on)
     *
     * @param numberCount count of numbers to be generated
     * @return a String of generated numbers
     */
    public String generateRandomNumber(int numberCount) {
        StringBuilder stringOfRandomNumbers = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < numberCount; i++) {
            int randomNumber = random.nextInt(10);
            if (randomNumber == 0 && i == 0) { // first number cannot be 0
                randomNumber = 1;
            }
            stringOfRandomNumbers.append(randomNumber);
        }

        return stringOfRandomNumbers.toString();
    }
}
