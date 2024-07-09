package kr.co.polycube.backendtest.config;

import kr.co.polycube.backendtest.config.batch.BatchConfig;
import kr.co.polycube.backendtest.domain.Lotto;
import kr.co.polycube.backendtest.domain.Winner;
import kr.co.polycube.backendtest.repository.LottoRepository;
import kr.co.polycube.backendtest.repository.WinnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBatchTest
@SpringBootTest
@SpringJUnitConfig({ BatchConfig.class, BatchTestConfig.class })
public class LottoBatchTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private LottoRepository lottoRepository;

    @Autowired
    private WinnerRepository winnerRepository;

    @Autowired
    private Job lottoJob;

    @BeforeEach
    public void setUp() {
        lottoRepository.deleteAll();
        winnerRepository.deleteAll();
        generateLottoData();
    }

    private void generateLottoData() {
        for (int i = 0; i < 10; i++) {
            lottoRepository.save(new Lotto(getLottoNumbers()));
        }
    }

    private static List<Integer> getLottoNumbers() {
        Set<Integer> numbersSet = new HashSet<>();
        while (numbersSet.size() < 6) {
            int number = (int) (Math.random() * 45) + 1;
            numbersSet.add(number);
        }

        return new ArrayList<>(numbersSet);
    }

    @Test
    public void lottoBatchTest() throws Exception {
        //given
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

        //when
        JobExecution jobExecution = jobLauncherTestUtils.getJobLauncher().run(lottoJob, jobParametersBuilder.toJobParameters());

        //then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);

        List<Winner> winners = winnerRepository.findAll();
        assertThat(winners).isNotEmpty();
    }
}