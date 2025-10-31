package ninja.oxente.cara_suja.controllers.users;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import ninja.oxente.cara_suja.validations.PostUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @PostMapping()
    public ResponseEntity<Map<String, String>> postUser(@Valid @RequestBody PostUserRequest requestBody) {
        Map<String, String> resp = new HashMap<>();
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }
}
