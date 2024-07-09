package kr.co.polycube.backendtest.service;

import kr.co.polycube.backendtest.domain.User;
import kr.co.polycube.backendtest.dto.user.GetUserResponse;
import kr.co.polycube.backendtest.dto.user.PatchUserResponse;
import kr.co.polycube.backendtest.dto.user.PostUserResponse;
import kr.co.polycube.backendtest.exception.UserNotFoundException;
import kr.co.polycube.backendtest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * user 생성
     */
    public PostUserResponse createUser() {
        log.info("[UserService.createUser]");

        User user = new User();
        userRepository.save(user);

        Long userId = user.getId();

        return new PostUserResponse(userId);
    }

    /**
     * user 조회
     */
    @Transactional(readOnly = true)
    public GetUserResponse findUserById(Long id) {
        log.info("[UserService.findUserById]");

        User findUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("no user found with id: " + id));

        return new GetUserResponse(findUser.getId(), findUser.getName());
    }

    /**
     * user 수정
     */
    public PatchUserResponse modifyUser(Long id, String name) {
        log.info("[UserService.modifyUser]");

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("no user found with id: " + id));
        user.setName(name);
        userRepository.save(user);

        return new PatchUserResponse(user.getId(), user.getName());
    }
}
