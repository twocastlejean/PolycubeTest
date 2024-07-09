package kr.co.polycube.backendtest.service;

import kr.co.polycube.backendtest.domain.Lotto;
import kr.co.polycube.backendtest.domain.Winner;
import kr.co.polycube.backendtest.repository.LottoRepository;
import kr.co.polycube.backendtest.repository.WinnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class LottoBatchService {
    private final LottoRepository lottoRepository;
    private final WinnerRepository winnerRepository;

    private static final int LOTTO_SIZE = 6;
    private static final int MAX_NUMBER = 45;

    public void checkWinners() {
        log.info("[LottoBatchService.checkWinners]");
        List<Integer> winningNumbers = getWinningNumbers();

        List<Lotto> lottos = lottoRepository.findAll();

        for (Lotto lotto : lottos) {
            int matchCount = 0;
            for (Integer number : lotto.getNumbers()) {
                if (winningNumbers.contains(number)) {
                    matchCount++;
                }
            }
            int rank = getRank(matchCount);

            if (rank > 0) {
                Winner winner = new Winner();
                winnerRepository.save(winner);
            }
        }
    }

    private int getRank(int matchCount) {
        return switch (matchCount) {
            case 6 -> 1;
            case 5 -> 2;
            case 4 -> 3;
            case 3 -> 4;
            case 2 -> 5;
            default -> 0;
        };
    }

    private static List<Integer> getWinningNumbers() {
        Set<Integer> numbersSet = new HashSet<>();
        while (numbersSet.size() < LOTTO_SIZE) {
            int number = (int) (Math.random() * MAX_NUMBER) + 1;
            numbersSet.add(number);
        }

        return new ArrayList<>(numbersSet);
    }
}
