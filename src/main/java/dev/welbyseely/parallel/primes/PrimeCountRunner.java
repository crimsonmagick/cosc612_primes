package dev.welbyseely.parallel.primes;


import dev.welbyseely.parallel.primes.counter.PrimeCounter;
import dev.welbyseely.parallel.primes.counter.PrimeCounterParallel;
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

    final int cores = Runtime.getRuntime().availableProcessors();

    final PrimeCounter parallel = new PrimeCounterParallel(cores);
    long startTime = System.nanoTime();
    final int parallelCount = parallel.countPrimes(n);
    System.out.printf("Parallel: Number of primes between 2 and %d=%d, time=%d ns%n", n,
        parallelCount, System.nanoTime() - startTime);

    final PrimeCounter serial = new PrimeCounterSerial();
    startTime = System.nanoTime();
    final int serialCount = serial.countPrimes(n);
    System.out.printf("Serial: Number of primes between 2 and %d=%d, time=%d ns%n", n, serialCount,
        System.nanoTime() - startTime);

  }

}