package kz.alibek.solva.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "test")
public class CurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_currency_code")
    @JsonProperty("1. From_Currency Code")
    private String fromCurrency;

    @Column(name = "to_currency_code")
    @JsonProperty("3. To_Currency Code")
    private String toCurrency;

    @Column(name = "exchange_rate")
    @JsonProperty("5. Exchange Rate")
    private String exchangeRate;

}
