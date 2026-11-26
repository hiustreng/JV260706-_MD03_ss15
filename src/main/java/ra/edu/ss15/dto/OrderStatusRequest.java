package ra.edu.ss15.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderStatusRequest {
    @NotBlank(message = "Trạng thái đơn hàng không được để trống")
    private String status;
}
