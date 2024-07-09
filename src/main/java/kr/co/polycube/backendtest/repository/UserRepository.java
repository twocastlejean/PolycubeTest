package kr.co.polycube.backendtest.repository;

import kr.co.polycube.backendtest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
