package br.com.feltex.desafioitau.infrastructure.adapter.controller;

import java.time.OffsetDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.feltex.desafioitau.api.EstatisticaApi;
import br.com.feltex.desafioitau.application.usecase.GetStatisticsUseCase;
import br.com.feltex.desafioitau.model.StatisticResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class StatisticsController implements EstatisticaApi {

    @Value("${statistics.max-interval-in-seconds:60}")
    private Integer maxIntervalInSeconds;

    private final GetStatisticsUseCase getStatisticsUseCase;

    public StatisticsController(final GetStatisticsUseCase getStatisticsUseCase) {
        this.getStatisticsUseCase = Objects.requireNonNull(getStatisticsUseCase, "usecase cannot be null");
    }

    @Override
    public ResponseEntity<StatisticResponse> getStatistics(Integer seconds) {
        if (seconds != null) {
            maxIntervalInSeconds = seconds;
        }
        final var startTime = System.nanoTime();
        log.info("Getting statistics");
        final var datetime = OffsetDateTime.now().minusSeconds(maxIntervalInSeconds);
        log.info("Datetime: {}", datetime);
        final var statistics = this.getStatisticsUseCase.getStatistics(datetime);
        log.info("Statistics: {}", statistics);
        final var endTime = System.nanoTime();
        log.info("Execution time: {} ms", (endTime - startTime) / 1000000);

        final var response = new StatisticResponse()
                .avg(statistics.avg())
                .count(statistics.count())
                .max(statistics.max())
                .min(statistics.min())
                .sum(statistics.sum());
        return ResponseEntity.ok(response);
    }

}
