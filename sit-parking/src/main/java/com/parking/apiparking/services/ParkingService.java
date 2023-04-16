package com.parking.apiparking.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@service
public class ParkingService {

    //creacion de una variable de instancia parkingLot que almacena objetos de tipo car en una lista
    private List<Car> parkingLot;
    private static final double HOURLY_RATE= 2.5;

    //Creacion de un constructor haciendo referencia a la variable de instancia parkingLot como un nuevo
    //objeto ArrayList vacio que se utiliza para almacenar objetos Car en la clase ParkingService
    public ParkingService(){
        this.parkingLot = new ArrayList<>();
    }

    //El método devuelve una lista de todos los objetos Car en la lista parkingLot
    public List<Car> getAllCars (){
        return this.parkingLot;
    }

    //Metodo para buscar un objeto Car en la lista parkingLot de la clase por medio de su placa,
    //devolviendo un objeto Optional<Car> que contiene el objeto Car correspondiente o un objeto
    // Optional vacío si no se encontró ninguna coincidencia
    public Optional<Car> getCarByLicensePlate(String licensePlate){
        return this.parkingLot.stream().filter(car -> car.getLicensePlate().equals(licensePlate)).findFirst();
    }

    //el método se utiliza para buscar todos los objetos Car en la lista parkingLot de la clase
    // por medio de su color, devolviendo una lista de objetos Car que coinciden con el color
    // especificado en el parámetro color
    public List<Car> getCarByColor(String color){
        return this.parkingLot.stream().filter(car -> car.getColor().equalsIgnoreCase(color)).collect(Collectors.toList());
    }

    //El método se utiliza para agregar un objeto Car a la lista parkingLot de la
    // clase, utilizando el método add() de la lista.
    public void addCar (Car car){
        this.parkingLot.add(car);
    }

    //el método se utiliza para buscar y eliminar un objeto Car en la lista parkingLot de la clase por medio
    // de su número de placa devolviendo un valor booleano que indica si se eliminó algún objeto Car de la lista
    public boolean removeCarByLicensePlate (String licensePlate){
        return this.parkingLot.removeIf(car -> car.getLicensePlate().equals(licensePlate));
    }

    //el método se utiliza para agregar un objeto Car a la lista parkingLot de la clase y establecer
    // su hora de entrada en el estacionamiento mediante el uso del método setEntryTime() del objeto Car
    public void parkCar(Car car){
        car.setEntryTime(LocalDateTime.now());
        addCar(car);
    }

    //el metodo lo que hace es que si un automóvil con el número de placa especificado está estacionado
    // en el estacionamiento lo eliminara, caso controrio el metodo no tendra ningun efecto sobre el
    //estacionamiento
    public void unparkCar(String licensePlate){
        removeCarByLicensePlate(licensePlate);
    }

    //Este metodo busca el automóvil con el número de placa proporcionado. Si se encuentra, se calcula
    // el tiempo que ha pasado desde la hora de entrada del automóvil hasta ahora y se multiplica
    // por la tarifa por hora para obtener la tarifa de estacionamiento, en caso contrario se devuelve o
    //o no cobra tarifa
    public double calculateParkingFee (String licensePlate){
        Optional<Car> optionalCar= getCarByLicensePlate(licensePlate);
        if(optionalCar.isPresent()){
            Car car= optionalCar.get();
            LocalDateTime entryTime = car.getEntryTime();
            if(entryTime != null) {
                long hoursParked = ChoroUnit.HOURS.between(entryTime, LocalDateTime.now());
                return hoursParked * HOURLY_RATE;
            }
        }
            return 0;
    }
}
