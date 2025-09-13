package dev.welbyseely.parallel.primes.counter;

public class PrimeCounterSerial implements PrimeCounter {

  @Override
  public int countPrimes(final int n) {
    if (n < 2) {
      return 0;
    }
    int count = 1; // start off by counting 2
    for (int i = 3; i <= n; i += 2) {
      if (isPrime(i)) {
        count++;
      }
    }
    return count;
  }

  private boolean isPrime(int n) {
    if (n < 2) {
      return false;
    }
    //  6k+1 test https://en.wikipedia.org/wiki/Primality_test
    if (n == 3) {
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
