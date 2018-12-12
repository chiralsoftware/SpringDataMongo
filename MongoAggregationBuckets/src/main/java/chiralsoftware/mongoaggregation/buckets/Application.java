package chiralsoftware.mongoaggregation.buckets;

import java.time.Instant;
import static java.time.LocalDate.now;
import static java.time.ZoneOffset.UTC;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.HashMap;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoOperations;
import static org.springframework.data.mongodb.core.aggregation.AccumulatorOperators.Sum.sumOf;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.bucket;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import static org.springframework.data.mongodb.core.aggregation.ArrayOperators.Size.lengthOfArray;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        LOG.info("Starting the application");
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void run(String... args) throws Exception {

        LOG.info("Demonstrating using bucket operations with Spring Data");

        final Instant now = now().atStartOfDay(UTC).toInstant();

        final Aggregation aggregation = newAggregation(bucket("date").
                withBoundaries(now.minus(10, DAYS), now.minus(9, DAYS),
                        now.minus(8, DAYS), now.minus(7, DAYS), now.minus(6, DAYS),
                        now.minus(5, DAYS), now.minus(4, DAYS), now.minus(3, DAYS),
                        now.minus(2, DAYS), now.minus(1, DAYS), now.minus(0, DAYS)).
                withDefaultBucket("defaultBucket").
                andOutput(sumOf(lengthOfArray("lbls"))).as("count"));

        final AggregationResults<HashMap> ar = mongoOperations.aggregate(aggregation, "imageCapture", HashMap.class);
        
        LOG.info("Here is the result: " + ar);
        LOG.info("And the list: " + ar.getMappedResults());
    }

}
