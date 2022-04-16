package app;

import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/")
    @Timed(name = "metrics.example.testController", absolute = true)
    public ResponseEntity<String> testMethod() {
         testService.testMethod();
         return new ResponseEntity<>(HttpStatus.OK);
    }
}
