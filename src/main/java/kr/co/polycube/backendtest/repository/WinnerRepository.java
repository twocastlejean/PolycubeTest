package kr.co.polycube.backendtest.repository;

import kr.co.polycube.backendtest.domain.Winner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WinnerRepository extends JpaRepository<Winner, Long> {
}
