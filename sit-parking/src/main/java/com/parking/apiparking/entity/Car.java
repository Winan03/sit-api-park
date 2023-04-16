package com.parking.apiparking.entity;

import java.time.LocalDateTime;

public class Car {

    //creacion de los atributos de la clase Car , en el cual la placa y el color son String y
    //la hora de entrada es de tipo LocalDateTime que me reporta una fecha y hora sin zona horaria
    private String licensePlate ;
    private String color;
    private LocalDateTime entryTime;

    //creacion de un constructor que permite crear un objeto Car con un número de placa y un color específico
    public Car(String licensePlate, String color) {
        this.licensePlate = licensePlate;
        this.color = color;
    }

    //Creacion de los metodos get y set para acceder a los campos privados de la clase Car desde fuera de la clase
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }


}
