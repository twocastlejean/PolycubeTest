package kr.co.polycube.backendtest.controller;

import kr.co.polycube.backendtest.dto.user.PatchUserResponse;
import kr.co.polycube.backendtest.dto.user.PostUserResponse;
import kr.co.polycube.backendtest.dto.user.GetUserResponse;
import kr.co.polycube.backendtest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<PostUserResponse> createUser() {
        log.info("[UserController.createUser]");

        PostUserResponse postUserResponse = userService.createUser();

        return ResponseEntity.ok(postUserResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable Long id) {
        log.info("[UserController.getUser]");

        GetUserResponse getUserResponse = userService.findUserById(id);

        return ResponseEntity.ok(getUserResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatchUserResponse> modifyUser(@PathVariable Long id, @RequestParam String name) {
        log.info("[UserController.modifyUser]");

        PatchUserResponse patchUserResponse = userService.modifyUser(id, name);

        return ResponseEntity.ok(patchUserResponse);
    }
}
