package ninja.oxente.cara_suja.presentation.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import ninja.oxente.cara_suja.application.services.user.UserService;
import ninja.oxente.cara_suja.domains.exceptions.EntityNotFoundException;
import ninja.oxente.cara_suja.presentation.dto.user.RegisterUserRequest;
import ninja.oxente.cara_suja.presentation.dto.user.UpdateUserRequest;
import ninja.oxente.cara_suja.presentation.dto.user.UserList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "CRUD for users")
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

    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<List<UserList>> getUsers() {
        List<UserList> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<UserList> updateUser(@PathVariable String id,
        @Valid @RequestBody UpdateUserRequest requestBody)
        throws EntityNotFoundException {
        UserList updatedUser = this.userService.updateUser(id, requestBody);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
