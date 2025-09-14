package dev.welbyseely.parallel.primes.counter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeCounterParallel extends PrimeCounter {

  private record InclusiveRange(int start, int end) {

  }

  public PrimeCounterParallel(final int maxThreadCount) {
    this.maxThreadCount = Math.max(maxThreadCount, 1);
  }

  private final int maxThreadCount;

  @Override
  public int countPrimes(final int n) {
    if (n < 2) {
      return 0;
    }
    if (n == 2) {
      return 1;
    }

    final AtomicInteger count = new AtomicInteger(1);

    final int numbersToCount = n - 2;
    final int threadCount = Math.min(numbersToCount, maxThreadCount);
    final int delta = (numbersToCount + threadCount - 1) / threadCount;

    final List<Thread> threads = new ArrayList<>();
    for (int i = 0; i < threadCount; i++) {
      final int a = i * delta + 3;  // start at 3
      final int b = Math.min(a + delta - 1, n);
      final InclusiveRange inclusiveRange = new InclusiveRange(a, b);
      final Thread thread = new Thread(() -> count.addAndGet(countRange(inclusiveRange)));
      thread.start();
      threads.add(thread);
    }

    for (final Thread thread : threads) {
      joinThread(thread);
    }

    return count.get();
  }

  private int countRange(final InclusiveRange inclusiveRange) {
    int count = 0;
    int i = inclusiveRange.start % 2 == 0 ? inclusiveRange.start + 1 : inclusiveRange.start;
    while (i <= inclusiveRange.end) {
      if (isPrime(i)) {
        count++;
      }
      i += 2;
    }
    return count;
  }

  private void joinThread(final Thread toJoin) {
    try {
      toJoin.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }

}
