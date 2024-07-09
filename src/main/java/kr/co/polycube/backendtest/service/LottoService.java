package kr.co.polycube.backendtest.service;

import kr.co.polycube.backendtest.domain.Lotto;
import kr.co.polycube.backendtest.dto.lotto.LottoResponse;
import kr.co.polycube.backendtest.repository.LottoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class LottoService {
    private final LottoRepository lottoRepository;
    private static final int LOTTO_SIZE = 6;
    private static final int MAX_NUMBER = 45;

    public LottoResponse createLotto() {
        log.info("[LottoService.createLotto]");
        List<Integer> numbersList = getLottoNumbers();

        Lotto lotto = new Lotto(numbersList);
        lottoRepository.save(lotto);

        return new LottoResponse(numbersList);
    }



    private static List<Integer> getLottoNumbers() {
        Set<Integer> numbersSet = new HashSet<>();
        while (numbersSet.size() < LOTTO_SIZE) {
            int number = (int) (Math.random() * MAX_NUMBER) + 1;
            numbersSet.add(number);
        }

        return new ArrayList<>(numbersSet);
    }
}
