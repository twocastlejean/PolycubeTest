package kr.co.polycube.backendtest.repository;

import kr.co.polycube.backendtest.domain.Lotto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LottoRepository extends JpaRepository<Lotto, Long> {
}
