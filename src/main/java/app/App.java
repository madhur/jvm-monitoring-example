package app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@ComponentScan
@Slf4j
public class App {

    @Autowired
    private TimerTask timerTask;

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(App.class, args);
    }

    @PostConstruct
    public void run() {
        // And From your main() method or any other method
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }
}
