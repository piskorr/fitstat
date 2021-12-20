package pl.polsl.fitstat.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.polsl.fitstat.dtos.UserDTO;
import pl.polsl.fitstat.services.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/profile")
    public ResponseEntity<?> getUsersProfile() {
        return ResponseEntity.status(HttpStatus.OK).body(new UserDTO(userService.getCurrentUser()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsersAndMap());
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody  ResponseEntity<?> changeCurrentUsersPassword(@RequestParam MultiValueMap<String, String> paramMap) throws Exception {
        if(paramMap == null && paramMap.get("password") == null) {
            throw new IllegalArgumentException("Password not provided");
        }
        userService.changeCurrentUsersPassword(paramMap);
        return ResponseEntity.status(HttpStatus.OK).body("Action completed!");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDTO));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        userService.logoutCurrentUser();
        return ResponseEntity.status(HttpStatus.OK).body("Action completed!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/users/{id}")
    public ResponseEntity<?> assignAdminRoleToUser(@PathVariable long id){
        userService.assignAdminRole(id);
        return ResponseEntity.status(HttpStatus.OK).body("Action completed!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(id));
    }


}
