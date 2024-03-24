package ee.fujitsu.delivery.app.controller;

import ee.fujitsu.delivery.app.dto.BaseFeeDto;
import ee.fujitsu.delivery.app.service.BaseFeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/delivery/base_fee")
public class BaseFeeController {

    private final BaseFeeService baseFeeService;

    @GetMapping
    public List<BaseFeeDto> getAllBaseFees() {
        return baseFeeService.getAllBaseFees();
    }

    @PostMapping
    public void registerBaseFee(@RequestBody BaseFeeDto baseFeeDto) {
        baseFeeService.registerBaseFee(baseFeeDto);
    }

    @PutMapping("{id}")
    public void updateBaseFee(@PathVariable Long id, @RequestBody BaseFeeDto baseFeeDto) {
        baseFeeService.updateBaseFee(id, baseFeeDto);
    }

    @DeleteMapping("{id}")
    public void deleteBaseFee(@PathVariable Long id) {
        baseFeeService.deleteBaseFee(id);
    }
}
