package br.com.feltex.desafioitau.domain.model;

import java.math.BigDecimal;

public record Statistics(Long count, BigDecimal sum, BigDecimal avg, BigDecimal min, BigDecimal max) {

}
