package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Headphones {
    private int id;
    private String model;
    private String brand;
    private String color;
    private int price;
    private double score;
    @Override
    public String toString() {
        return "\nНомер: " + id + ", модель: " + model + ", бренд: " + brand + ", цвет: " + color + ", цена: "
                + price + ", рейтинг: " + score ;
    }
    
}