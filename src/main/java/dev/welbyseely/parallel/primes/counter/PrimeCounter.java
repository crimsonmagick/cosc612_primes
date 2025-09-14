package dev.welbyseely.parallel.primes.counter;

public abstract class PrimeCounter {

  /**
   * Counts the number of primes between 2 and n
   *
   * @param n the upper bound, inclusive
   * @return the number of primes
   */
  abstract public int countPrimes(int n);

  protected boolean isPrime(int n) {
    if (n < 2) {
      return false;
    }
    //  6k+1 test https://en.wikipedia.org/wiki/Primality_test
    if (n == 3 || n == 2) {
      return true;
    }
    if (n % 3 == 0) {
      return false;
    }
    for (int i = 5; i <= n / i; i += 6) {
      if (n % i == 0 || n % (i + 2) == 0) {
        return false;
      }
    }
    return true;
  }
}
