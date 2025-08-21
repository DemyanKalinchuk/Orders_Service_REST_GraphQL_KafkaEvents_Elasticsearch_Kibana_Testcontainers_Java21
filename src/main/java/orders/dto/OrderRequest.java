package orders.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    @Email @NotBlank
    private String customerEmail;
    @NotNull @DecimalMin(value = "0.01")
    private BigDecimal amount;
}
