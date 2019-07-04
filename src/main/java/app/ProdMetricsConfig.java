package app;

import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Configuration
@EnableMetrics
public class ProdMetricsConfig extends MetricsConfig {

    private static final String GRAPHITE_PREFIX =
            "collectd/graphite-monitoring-example/production";


    @Override
    protected void configureReporters() {
        configureReporters(GRAPHITE_PREFIX);
    }

    @PostConstruct()
    public void init() {
        configureReporters();
    }
}
