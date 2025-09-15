package dev.welbyseely.parallel.primes.csv;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class MetricWriter {

  private final PrintWriter printWriter;

  public MetricWriter(final String path) {
    try {
      Files.createDirectories(Paths.get(path));
      DateTimeFormatter formatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")
          .withZone(ZoneOffset.UTC);
      final String iso8601 = formatter.format(Instant.now());
      final String fileName = "prime-metrics-" + iso8601 + ".csv";
      printWriter = new PrintWriter(Files.newBufferedWriter(Paths.get(path, fileName)));
      printWriter.println("p,n,prime_count,duration_ns");
      printWriter.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void write(final int p, final int n, final int primeCount, final long duration_ns) {
    System.out.printf("p=%d, n=%d, count=%d, duration_ns=%d ns%n", p, n,
        primeCount, duration_ns);
    printWriter.println(String.format("%d,%d,%d,%d", p, n, primeCount, duration_ns));
    printWriter.flush();
  }

}
