/**
 * ModifiableVariable - A Variable Concept for Runtime Modifications
 *
 * Copyright 2014-2022 Ruhr University Bochum, Paderborn University, Hackmanit GmbH
 *
 * Licensed under Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package de.rub.nds.modifiablevariable.util;

import java.security.Provider;
import java.security.SecureRandom;
import java.security.SecureRandomSpi;
import java.util.Random;

/**
 * 
 */
public class BadRandom extends SecureRandom {

    private Random random;

    public BadRandom() {
        random = new Random(0);
    }

    public BadRandom(Random random, byte[] seed) {
        this.random = random;
    }

    public BadRandom(Random random, SecureRandomSpi secureRandomSpi, Provider provider) {
        this.random = random;
    }

    @Override
    public byte[] generateSeed(int numBytes) {
        byte[] ray = new byte[numBytes];
        random.nextBytes(ray);
        return ray;
    }

    @Override
    public synchronized void nextBytes(byte[] bytes) {
        random.nextBytes(bytes);

    }

    @Override
    public void setSeed(long seed) {
        random = new Random(seed);
    }

    @Override
    public synchronized void setSeed(byte[] seed) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAlgorithm() {
        return "WARNING: We use a very insecure PRNG. THIS IS NOT A SECURE RANDOM OBJECT. USE FOR TESTING ONLY";
    }

    @Override
    public int nextInt() {
        return random.nextInt();
    }

    @Override
    public int nextInt(int n) {
        return random.nextInt(n);
    }

    @Override
    public long nextLong() {
        return random.nextLong();
    }

    @Override
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    @Override
    public float nextFloat() {
        return random.nextFloat();
    }

    @Override
    public double nextDouble() {
        return random.nextDouble();
    }

    @Override
    public synchronized double nextGaussian() {
        return random.nextGaussian();
    }

}
