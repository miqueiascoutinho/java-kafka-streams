package br.com.miqueias.kafka.kafkastreams.domain;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.MathContext;

@Data
public class OrderCalculate {
    private Integer quantidade = 0;
    private BigDecimal total = new BigDecimal(0);

    public OrderCalculate adicionar(BigDecimal total) {
        this.total = this.total.add(total, MathContext.DECIMAL32);
        ++quantidade;
        return this;
    }
}
