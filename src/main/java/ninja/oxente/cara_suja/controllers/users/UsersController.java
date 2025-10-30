package ninja.oxente.cara_suja.controllers.users;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @PostMapping()
    public ResponseEntity<Map<String, String>> postUser() {
        Map<String, String> resp = new HashMap<>();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
