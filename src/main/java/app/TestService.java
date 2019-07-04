package app;

import com.codahale.metrics.Timer;
import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TestService {

    @Autowired
    private MetricsConfig metricsConfig;


    @Timed(name = "metrics.example.testMethod", absolute = true)
    public void testMethod() {
        // Increment the counter
        metricsConfig.publishCounter.inc();
        Timer.Context ctx = metricsConfig.publishTimer.time();
        // Sleep for random amount of time
        int random = new Random().nextInt(100);
        try {
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ctx.stop();
        metricsConfig.publishMeter.mark();

    }
}
