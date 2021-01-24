package app;

import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import org.springframework.context.annotation.Configuration;


import javax.annotation.PostConstruct;

@Configuration
@EnableMetrics
public class ProdMetricsConfig extends MetricsConfig {

    @PostConstruct()
    public void init() {
        configureReporters();
    }
}
