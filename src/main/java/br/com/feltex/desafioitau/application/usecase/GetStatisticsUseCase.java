package br.com.feltex.desafioitau.application.usecase;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import br.com.feltex.desafioitau.domain.model.Transaction;
import br.com.feltex.desafioitau.domain.port.TransactionRepository;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GetStatisticsUseCase {

    private final TransactionRepository transactionRepository;

    public GetStatisticsUseCase(final TransactionRepository transactionRepository) {
        this.transactionRepository = Objects.requireNonNull(transactionRepository,
                "TransactionRepository could not be null");
    }

    public Output getStatistics(final OffsetDateTime datetime) {
        final var transactions = transactionRepository.findAllWithCreatedAtAfter(datetime);
        return Output.from(transactions);
    }

    public record Output(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, Long count) {

        public static Output from(List<Transaction> transactions) {
            // collect values into an array
            final var values = transactions.stream().map(Transaction::value).toArray(BigDecimal[]::new);
            // create a double stream from the values
            final var doubleStream = Arrays.stream(values).mapToDouble(BigDecimal::doubleValue);
            // create a summary statistics object from the double stream
            final var summaryStatistics = doubleStream.summaryStatistics();

            double max = summaryStatistics.getMax() == Double.NEGATIVE_INFINITY ? 0 : summaryStatistics.getMax();
            double min = summaryStatistics.getMin() == Double.POSITIVE_INFINITY ? 0 : summaryStatistics.getMin();

            return new Output(
                    BigDecimal.valueOf(summaryStatistics.getSum()),
                    BigDecimal.valueOf(summaryStatistics.getAverage()),
                    BigDecimal.valueOf(max),
                    BigDecimal.valueOf(min),
                    summaryStatistics.getCount());
        }
    }

}
