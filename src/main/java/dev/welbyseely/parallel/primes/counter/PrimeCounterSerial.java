package dev.welbyseely.parallel.primes.counter;

public class PrimeCounterSerial extends PrimeCounter {

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

}
