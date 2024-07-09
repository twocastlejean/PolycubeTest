package kr.co.polycube.backendtest.controller;

import kr.co.polycube.backendtest.dto.lotto.LottoResponse;
import kr.co.polycube.backendtest.service.LottoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/lottos")
public class LottoController {
    private final LottoService lottoService;

    @PostMapping
    public ResponseEntity<LottoResponse> createLotto() {
        log.info("[LottoController.createLotto]");
        LottoResponse lottoResponse = lottoService.createLotto();

        return ResponseEntity.ok(lottoResponse);
    }
}
