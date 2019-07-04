package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

@Component
public class TickEverySecond extends TimerTask {

    @Autowired
    private MetricsConfig metricsConfig;

    @Override
    public void run() {
        metricsConfig.secondTimer.mark();
    }
}
