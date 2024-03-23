package ee.fujitsu.delivery.app.controller;

import ee.fujitsu.delivery.app.service.DeliveryFeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("api/delivery/fee")
public class DeliveryFeeController {

    private final DeliveryFeeService deliveryFeeService;

    @GetMapping("/city/{cityId}/vehicle/{vehicleId}")
    public BigDecimal calculateDeliveryFee(@PathVariable("cityId") Long cityId,
                                           @PathVariable("vehicleId") Long vehicleId,
                                           @RequestParam(value = "dateTime", required = false) LocalDateTime dateTime) {
        return deliveryFeeService.calculateDeliveryFee(cityId, vehicleId, dateTime);
    }
}
