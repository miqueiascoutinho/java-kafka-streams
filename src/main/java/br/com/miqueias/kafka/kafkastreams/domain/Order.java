package br.com.miqueias.kafka.kafkastreams.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {
    private Integer id;
    private String produto;
    private int quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
    private Usuario comprador;
    private Usuario vendedor;
}

