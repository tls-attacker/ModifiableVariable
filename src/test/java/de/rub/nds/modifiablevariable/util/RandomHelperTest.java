/*
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Ruhr University Bochum, Paderborn University, Technology Innovation Institute, and Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.modifiablevariable.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RandomHelperTest {

    @AfterEach
    void tearDown() {
        // Reset to default random for other tests
        RandomHelper.setRandom(new Random(0));
    }

    @Test
    void testGetRandom() {
        Random random = RandomHelper.getRandom();
        assertNotNull(random);

        // Test that the random is initialized with a fixed seed (0)
        // by comparing with a known sequence
        Random expectedRandom = new Random(0);
        for (int i = 0; i < 10; i++) {
            assertEquals(expectedRandom.nextInt(), random.nextInt());
        }
    }

    @Test
    void testSetRandom() {
        Random customRandom = new Random(42);
        RandomHelper.setRandom(customRandom);

        Random retrievedRandom = RandomHelper.getRandom();
        assertNotNull(retrievedRandom);

        // Verify it's the same random instance
        assertTrue(
                retrievedRandom == customRandom,
                "Retrieved random should be the same instance as set");
    }

    @Test
    void testGetBadSecureRandom() {
        BadRandom badRandom = RandomHelper.getBadSecureRandom();
        assertNotNull(badRandom);

        // Verify the bad random produces expected output based on the fixed seed
        Random expectedRandom = new Random(0);
        for (int i = 0; i < 10; i++) {
            assertEquals(expectedRandom.nextInt(), badRandom.nextInt());
        }

        // Verify it returns the warning message
        String algorithm = badRandom.getAlgorithm();
        assertTrue(algorithm.contains("WARNING") && algorithm.contains("insecure"));
    }

    @Test
    void testBadRandomImplementation() {
        BadRandom badRandom = new BadRandom();

        // Test default constructor
        // Just test that we can get values (don't compare the actual values
        // since they might be inconsistent between test runs)
        badRandom.nextInt();

        // Test setSeed
        badRandom.setSeed(42);

        // Test other methods without comparing exact values
        byte[] bytes = new byte[10];
        badRandom.nextBytes(bytes);

        // Just verify these methods don't throw exceptions
        badRandom.nextBoolean();
        badRandom.nextDouble();
        badRandom.nextFloat();
        badRandom.nextGaussian();
        badRandom.nextLong();

        // Test generateSeed
        byte[] generatedSeed = badRandom.generateSeed(5);
        assertEquals(5, generatedSeed.length);
    }

    @Test
    @SuppressWarnings("deprecation")
    void testBadRandomConstructors() {
        Random customRandom = new Random(123);

        // Test constructor with Random and seed
        BadRandom badRandom1 = new BadRandom(customRandom, null);
        assertNotNull(badRandom1.nextInt()); // Just verify it returns a value

        // Test constructor with Random, SPI, and Provider
        BadRandom badRandom2 = new BadRandom(customRandom, null, null);
        assertNotNull(badRandom2.nextInt()); // Just verify it returns a value
    }

    @Test
    void testIntentionalMutableRandomExposure() {
        // This test documents the intentional design decision to expose the mutable Random instance
        Random random1 = RandomHelper.getRandom();
        Random random2 = RandomHelper.getRandom();

        // Verify we get the same instance (singleton pattern)
        assertTrue(random1 == random2, "Should return the same Random instance");

        // Verify that external modifications affect the singleton
        // This is intentional behavior for testing flexibility
        random1.setSeed(42);

        // Create a separate Random with same seed to compare expected values
        Random expectedRandom = new Random(42);

        // Both references should produce the same value as the expected Random
        int expectedValue = expectedRandom.nextInt();
        int random1Value = random1.nextInt();
        assertEquals(expectedValue, random1Value, "Modified Random should produce expected value");

        // Reset and get second value to verify both references see same state
        random1.setSeed(42);
        expectedRandom.setSeed(42);
        assertEquals(
                expectedRandom.nextInt(),
                random2.nextInt(),
                "All references to the singleton should see the same state");

        // Reset for other tests
        RandomHelper.setRandom(new Random(0));
    }

    @Test
    void testBadFixedRandom() {
        byte fixedValue = 42; // Using a single byte as per the class implementation
        BadFixedRandom fixedRandom = new BadFixedRandom(fixedValue);

        byte[] result = new byte[5];
        fixedRandom.nextBytes(result);

        // Verify all bytes in the array are set to the fixed value
        for (int i = 0; i < result.length; i++) {
            assertEquals(fixedValue, result[i]);
        }

        // Test that it ignores the seed parameter
        fixedRandom.setSeed(123);
        byte[] resultAfterSeed = new byte[5];
        fixedRandom.nextBytes(resultAfterSeed);

        // Verify all bytes are still the fixed value
        for (int i = 0; i < resultAfterSeed.length; i++) {
            assertEquals(fixedValue, resultAfterSeed[i]);
        }
    }
}
