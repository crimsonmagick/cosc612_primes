package dev.welbyseely.parallel.primes;


import dev.welbyseely.parallel.primes.counter.PrimeCounter;
import dev.welbyseely.parallel.primes.counter.PrimeCounterParallel;
import dev.welbyseely.parallel.primes.csv.MetricWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;

public class PrimeCountRunner {

  private static final int SAMPLE_COUNT = 10;

  public static void main(final String[] args) {

    if (args.length < 2) {
      System.err.println("Usage: PrimeCountRunner <number> <logger_path>");
      System.exit(1);
    }

    final int max_n;
    try {
      max_n = Integer.parseInt(args[0]);
    } catch (final NumberFormatException e) {
      System.err.println("Input must be a valid number. Usage: PrimeCountRunner <number>");
      System.exit(1);
      return;
    }

    final String loggerPath = args[1];
    final MetricWriter writer = new MetricWriter(loggerPath);

    final int coreDivisor = 4;

    final int processorCount = Runtime.getRuntime().availableProcessors();
    final int p_delta = processorCount / coreDivisor;

    final int[] threads = IntStream.iterate(1, i -> i + p_delta)
      .limit(coreDivisor)
      .toArray();
    threads[threads.length - 1] = processorCount;

    for (final int p : threads) {
      final PrimeCounter parallel = new PrimeCounterParallel(p);
      parallel.countPrimes(2000);

      for (int n = 2; n <= max_n; n *= 2) {
        int primeCount = 0;
        final Collection<Long> durations = new ArrayList<>();

        for (int j = 0; j < SAMPLE_COUNT; j++) {
          final long startTime = System.nanoTime();
          primeCount = parallel.countPrimes(n);
          final long durationNs = System.nanoTime() - startTime;
          durations.add(durationNs);
        }

        final double averageDurationNs = durations.stream()
          .mapToLong(Long::longValue)
          .average()
          .orElseThrow(() -> new RuntimeException("Durations should not be empty"));

        writer.write(p, n, primeCount, (long) averageDurationNs);
      }
    }

  }

}