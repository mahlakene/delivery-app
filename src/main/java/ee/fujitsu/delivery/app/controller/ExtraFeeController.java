package ee.fujitsu.delivery.app.controller;

import ee.fujitsu.delivery.app.dto.NumericalExtraFeeDto;
import ee.fujitsu.delivery.app.dto.PhenomenonExtraFeeDto;
import ee.fujitsu.delivery.app.service.ExtraFeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/delivery/extra_fee")
public class ExtraFeeController {

    private final ExtraFeeService extraFeeService;

    // Air temperature

    /**
     * Retrieves all air temperature extra fees.
     *
     * @return List of air temperature extra fee DTOs
     */
    @GetMapping("air_temperature")
    public List<NumericalExtraFeeDto> getAllAirTemperatureExtraFees() {
        return extraFeeService.getAllAirTemperatureExtraFees();
    }

    /**
     * Registers a new air temperature extra fee.
     *
     * @param extraFeeDto Air temperature extra fee DTO to register
     */
    @PostMapping("air_temperature")
    public void registerAirTemperatureExtraFee(@RequestBody NumericalExtraFeeDto extraFeeDto) {
        extraFeeService.registerAirTemperatureExtraFee(extraFeeDto);
    }

    /**
     * Updates an existing air temperature extra fee.
     *
     * @param id         ID of the air temperature extra fee to update
     * @param extraFeeDto Updated air temperature extra fee DTO
     */
    @PutMapping("air_temperature/{id}")
    public void updateAirTemperatureExtraFee(@PathVariable Long id, @RequestBody NumericalExtraFeeDto extraFeeDto) {
        extraFeeService.updateAirTemperatureExtraFee(id, extraFeeDto);
    }

    /**
     * Deletes an existing air temperature extra fee.
     *
     * @param id ID of the air temperature extra fee to delete
     */
    @DeleteMapping("air_temperature/{id}")
    public void deleteAirTemperatureExtraFee(@PathVariable Long id) {
        extraFeeService.deleteAirTemperatureExtraFee(id);
    }

    // Wind speed

    /**
     * Retrieves all wind speed extra fees.
     *
     * @return List of wind speed extra fee DTOs
     */
    @GetMapping("wind_speed")
    public List<NumericalExtraFeeDto> getAllWindSpeedExtraFees() {
        return extraFeeService.getAllWindSpeedExtraFees();
    }

    /**
     * Registers a new wind speed extra fee.
     *
     * @param extraFeeDto Wind speed extra fee DTO to register
     */
    @PostMapping("wind_speed")
    public void registerWindSpeedExtraFee(@RequestBody NumericalExtraFeeDto extraFeeDto) {
        extraFeeService.registerWindSpeedExtraFee(extraFeeDto);
    }

    /**
     * Updates an existing wind speed extra fee.
     *
     * @param id         ID of the wind speed extra fee to update
     * @param extraFeeDto Updated wind speed extra fee DTO
     */
    @PutMapping("wind_speed/{id}")
    public void updateWindSpeedExtraFee(@PathVariable Long id, @RequestBody NumericalExtraFeeDto extraFeeDto) {
        extraFeeService.updateWindSpeedExtraFee(id, extraFeeDto);
    }

    /**
     * Deletes an existing wind speed extra fee.
     *
     * @param id ID of the wind speed extra fee to delete
     */
    @DeleteMapping("wind_speed/{id}")
    public void deleteWindSpeedExtraFee(@PathVariable Long id) {
        extraFeeService.deleteWindSpeedExtraFee(id);
    }

    // Weather phenomenon

    /**
     * Retrieves all weather phenomenon extra fees.
     *
     * @return List of weather phenomenon extra fee DTOs
     */
    @GetMapping("phenomenon")
    public List<PhenomenonExtraFeeDto> getAllWeatherPhenomenonExtraFees() {
        return extraFeeService.getAllPhenomenonExtraFees();
    }

    /**
     * Registers a new weather phenomenon extra fee.
     *
     * @param extraFeeDto Weather phenomenon extra fee DTO to register
     */
    @PostMapping("phenomenon")
    public void registerWeatherPhenomenonExtraFee(@RequestBody PhenomenonExtraFeeDto extraFeeDto) {
        extraFeeService.registerPhenomenonExtraFee(extraFeeDto);
    }

    /**
     * Updates an existing weather phenomenon extra fee.
     *
     * @param id         ID of the weather phenomenon extra fee to update
     * @param extraFeeDto Updated weather phenomenon extra fee DTO
     */
    @PutMapping("phenomenon/{id}")
    public void updateWeatherPhenomenonExtraFee(@PathVariable Long id, @RequestBody PhenomenonExtraFeeDto extraFeeDto) {
        extraFeeService.updatePhenomenonExtraFee(id, extraFeeDto);
    }

    /**
     * Deletes an existing weather phenomenon extra fee.
     *
     * @param id ID of the weather phenomenon extra fee to delete
     */
    @DeleteMapping("phenomenon/{id}")
    public void deleteWeatherPhenomenonExtraFee(@PathVariable Long id) {
        extraFeeService.deletePhenomenonFee(id);
    }
}
