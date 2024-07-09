package kr.co.polycube.backendtest.dto.lotto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LottoResponse {
    private final List<Integer> numbers;
}
