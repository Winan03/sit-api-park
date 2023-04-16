package com.parking.apiparking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.parking.apiparking.services.ParkingService;

import java.util.List;


@RestController
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllCars(){
        return ResponseEntity.ok(this.parkingService.getAllCars());
    }

    @GetMapping("/cars/{licensePlate}")
    public ResponseEntity<car>  getCarByLicensePlate(@PathVariable String licensePlate){
        Optional <Car> optionalCar = this.parkingService.getCarByLicensePlate(licensePlate);
        if(optionalCar.isPresent()){
            return ResponseEntity.ok(optionalCar.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getCarsByColor(@RequestParam (required = false)String color){
        if(color != null){
            List<Car> carsByColor = this.parkingService.getCarsByColor(color);
            return ResponseEntity.ok(carsByColor);
        }
        return ResponseEntity.ok(this.parkingService.getAllCars());
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> addCar(@RequestBody Car car){
        this.parkingService.addCar(car);
        return new ResponseEntity<>(car,HttpStatus.CREATED);
    }

    @DeleteMapping("/cars/{licensePlate}")
    public ResponseEntity<void> removeCarByLicensePlate(@PathVariable String licensePlate){
        boolean removed = this.parkingService.removeCarByLicensePlate(licensePlate);
        if(removed){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cars/{licensePlate}/entry")
    public ResponseEntity<void> registerEntry(@PathVariable String licensePlate){
        Car car = new Car(licensePlate,null);
        this.parkingService.parkCar(car);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/cars/{licensePlate}/exit")
    public ResponseEntity<Double> registerExit(@PathVariable String licensePlate){
        double fee=this.parkingService.calculateParkingFee(licensePlate);
        this.parkingService.unparkCar(licensePlate);
        return ResponseEntity.ok(fee);
    }
}
