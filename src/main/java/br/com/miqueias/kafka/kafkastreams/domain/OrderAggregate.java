package br.com.miqueias.kafka.kafkastreams.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderAggregate {
    private Integer quantidade;
    private String key;
    private BigDecimal total;
    private Boolean isJanelaTempo;
}
