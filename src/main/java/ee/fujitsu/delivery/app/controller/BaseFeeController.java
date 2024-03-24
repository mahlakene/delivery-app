package ee.fujitsu.delivery.app.controller;

import ee.fujitsu.delivery.app.dto.BaseFeeDto;
import ee.fujitsu.delivery.app.service.BaseFeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing base fees.
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/delivery/base_fee")
public class BaseFeeController {

    private final BaseFeeService baseFeeService;

    /**
     * Retrieves all base fees.
     *
     * @return a list of BaseFeeDto objects representing all base fees
     */
    @GetMapping
    public List<BaseFeeDto> getAllBaseFees() {
        return baseFeeService.getAllBaseFees();
    }

    /**
     * Registers a new base fee.
     *
     * @param baseFeeDto the BaseFeeDto object representing the base fee to be registered
     */
    @PostMapping
    public void registerBaseFee(@RequestBody BaseFeeDto baseFeeDto) {
        baseFeeService.registerBaseFee(baseFeeDto);
    }

    /**
     * Updates an existing base fee.
     *
     * @param id         the ID of the base fee to be updated
     * @param baseFeeDto the BaseFeeDto object representing the updated base fee data
     */
    @PutMapping("{id}")
    public void updateBaseFee(@PathVariable Long id, @RequestBody BaseFeeDto baseFeeDto) {
        baseFeeService.updateBaseFee(id, baseFeeDto);
    }

    /**
     * Deletes an existing base fee.
     *
     * @param id the ID of the base fee to be deleted
     */
    @DeleteMapping("{id}")
    public void deleteBaseFee(@PathVariable Long id) {
        baseFeeService.deleteBaseFee(id);
    }
}
