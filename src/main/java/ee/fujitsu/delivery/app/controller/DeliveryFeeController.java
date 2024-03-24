package ee.fujitsu.delivery.app.controller;

import ee.fujitsu.delivery.app.service.DeliveryFeeService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Controller for delivery fee calculation.
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/delivery/fee")
public class DeliveryFeeController {

    private final DeliveryFeeService deliveryFeeService;

    /**
     * Calculate the delivery fee based on city, vehicle and timestamp (optional).
     */
    @GetMapping("/city/{cityId}/vehicle/{vehicleId}")
    public BigDecimal calculateDeliveryFee(
            @PathVariable("cityId") Long cityId,
            @PathVariable("vehicleId") Long vehicleId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime dateTime) {
        return deliveryFeeService.calculateDeliveryFee(cityId, vehicleId, dateTime);
    }
}
