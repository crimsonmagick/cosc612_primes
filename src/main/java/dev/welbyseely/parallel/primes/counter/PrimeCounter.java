package dev.welbyseely.parallel.primes.counter;

public interface PrimeCounter {

    /**
     * Counts the number of primes between 2 and n
     * @param n the upper bound, inclusive
     * @return the number of primes
     */
    int countPrimes(int n);
}
