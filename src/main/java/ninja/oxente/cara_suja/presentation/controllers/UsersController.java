package ninja.oxente.cara_suja.presentation.controllers;

import jakarta.validation.Valid;
import ninja.oxente.cara_suja.application.services.user.UserService;
import ninja.oxente.cara_suja.presentation.dto.user.RegisterUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<String> postUser(
        @Valid @RequestBody RegisterUserRequest requestBody) {
        String newId = this.userService.createNewUser(requestBody);
        return new ResponseEntity<>(newId, HttpStatus.CREATED);
    }
}
