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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class RandomHelperTest {

    @AfterEach
    public void tearDown() {
        // Reset to default random for other tests
        RandomHelper.setRandom(new Random(0));
    }

    @Test
    public void testGetRandom() {
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
    public void testSetRandom() {
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
    public void testGetBadSecureRandom() {
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
    public void testBadRandomImplementation() {
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
    public void testBadRandomConstructors() {
        Random customRandom = new Random(123);

        // Test constructor with Random and seed
        BadRandom badRandom1 = new BadRandom(customRandom, null);
        assertNotNull(badRandom1.nextInt()); // Just verify it returns a value

        // Test constructor with Random, SPI, and Provider
        BadRandom badRandom2 = new BadRandom(customRandom, null, null);
        assertNotNull(badRandom2.nextInt()); // Just verify it returns a value
    }

    @Test
    public void testBadFixedRandom() {
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
