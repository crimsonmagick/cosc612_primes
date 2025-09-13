package dev.welbyseely.parallel.primes;


import dev.welbyseely.parallel.primes.counter.PrimeCounterSerial;

public class PrimeCountRunner {

  public static void main(final String[] args) {

    if (args.length < 1) {
      System.err.println("Usage: PrimeCountRunner <number>");
      System.exit(1);
    }

    final int n;
    try {
      n = Integer.parseInt(args[0]);
    } catch (final NumberFormatException e) {
      System.err.println("Input must be a valid number. Usage: PrimeCountRunner <number>");
      System.exit(1);
      return;
    }

    final PrimeCounterSerial primeCounterSerial = new PrimeCounterSerial();
    final int count = primeCounterSerial.countPrimes(n);
    System.out.printf("Number of primes between 2 and %d: %d\n", n, count);

  }

}