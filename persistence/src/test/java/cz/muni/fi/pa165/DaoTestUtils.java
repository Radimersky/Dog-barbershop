package cz.muni.fi.pa165;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class DaoTestUtils {

    public static Date getRandomDate() {
        long startMillis = Date.from(Instant.EPOCH).getTime();
        long endMillis = Date.from(Instant.now()).getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);

        return new Date(randomMillisSinceEpoch);
    }
}
