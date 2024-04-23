package com.example;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class HeadphonesDeno {
    
    public static void main(String[] args) throws IOException {
        System.out.print("\033[H\033[2J");
        ArrayList<Headphones> headphones = readFile("Headphones.txt");
        System.out.println("Первоначальный список наушников: "+headphones.toString().replace("[", "").replace("]", ""));

        List<Headphones> sortedByModel = headphones.stream()
            .sorted(Comparator.comparing(Headphones::getModel))
            .collect(Collectors.toList());
        System.out.println("\nСортировка по названию модели: "+sortedByModel.toString().replace("[", "").replace("]", ""));
        
        List<Headphones> sortedByPrice = headphones.stream()
            .sorted(Comparator.comparing(Headphones::getPrice))
            .collect(Collectors.toList());
            System.out.println("\nСортировка по цене: "+sortedByPrice.toString().replace("[", "").replace("]", ""));         

        Optional<Headphones> headphonesWithLowestScore=headphones.stream()
            .sorted(Comparator.comparing(Headphones::getScore))
            .findFirst();
        System.out.println("\nНаушники с наименьшей оценкой: "+headphonesWithLowestScore.get());

        List<Headphones> anyExceptBlack = headphones.stream()
            .filter(x -> !x.getColor().equals("черный"))
            .collect(Collectors.toList());
        System.out.println("\nНе черные наушники: "+anyExceptBlack.toString().replace("[", "").replace("]", ""));

        OptionalDouble averageScore = headphones.stream()
            .mapToDouble(Headphones::getScore)
            .average();
        System.out.printf("\nСредняя оценка всех наушников: %.2f\n",averageScore.getAsDouble());

        long numberOfHeadphonesMoreExpensiveThan1000 = headphones.stream()
            .filter(x->x.getPrice()>1000)
            .count();
        System.out.println("\nКоличество наушников дороже 1000: "+numberOfHeadphonesMoreExpensiveThan1000);    

        boolean allScoresAreGreaterThan4 =headphones.stream()
            .allMatch(x->x.getScore()>4);
        System.out.println("\nУ всех наушников оценка больше 4: "+allScoresAreGreaterThan4);

        boolean thereAreQumoBrandHeadphones = headphones.stream()
            .anyMatch(x->x.getBrand().equals("Qumo"));
        System.out.println("\nВ списке есть наушники бренда Qumo: "+thereAreQumoBrandHeadphones);

        Optional<Headphones> cheapestHeadphones = headphones.stream()
            .collect(minBy(comparing(Headphones::getPrice)));
        System.out.println("\nСамые дешевые наушники: "+cheapestHeadphones.get());

        Map <Boolean,List<Headphones>> headphonesDividedByScore = headphones.stream()
            .collect(partitioningBy(x->x.getScore()>=4.5));
        System.out.println("\nСписок наушников с оценкой выше 4.5: "+headphonesDividedByScore.get(true).toString().replace("[", "").replace("]", ""));
        System.out.println("\nСписок наушников с оценкой ниже 4.5: "+headphonesDividedByScore.get(false).toString().replace("[", "").replace("]", ""));
        
        Map<String,Long> headphonesDividedByBrand = headphones.stream()
            .collect(groupingBy(Headphones::getBrand,counting()));
            System.out.println("\nКоличество наушников каждого бренда:");
        for(var m:headphonesDividedByBrand.entrySet())
        {
            System.out.println(m.getKey()+" "+m.getValue());
        }
        
        Map<String,Double> headphonesDividedByBrand1=headphones.stream()
        .collect(groupingBy(Headphones::getBrand,averagingInt(Headphones::getPrice)));
        System.out.println("\nСредняя цена наушников каждого бренда: ");
        for(var m:headphonesDividedByBrand1.entrySet())
        {
            System.out.printf(m.getKey()+" %.2f\n",m.getValue());
        }

        String logitechHeadphones=headphones.stream()
            .filter(x->x.getBrand().equals("Logitech"))
            .map(Headphones::getModel)
            .collect(joining(", ","\nМодели наушников бренда Logitech: ",""));
        System.out.println(logitechHeadphones);
    }

    public static ArrayList<Headphones> readFile(String pathname) throws FileNotFoundException, IOException
    {
        ArrayList<Headphones> headphones= new ArrayList<>();
        File file =new File(pathname);
        if(!file.exists())
        {
            System.err.printf("File %s doesn't exist\n", file.getPath());
        }
        else
        {
            try (var reader = new BufferedReader(new FileReader(file)))
            {
                String stringToSplit = new String();
                stringToSplit=reader.readLine();
                stringToSplit=reader.readLine();
                while (stringToSplit!=null) {
                    String[] arr = stringToSplit.split("\t");
                    headphones.add(new Headphones(Integer.parseInt(arr[0]), arr[1], arr[2], arr[3], Integer.parseInt(arr[4]), Double.parseDouble(arr[5].replace(",","."))));
                    stringToSplit=reader.readLine();
                }}}
        return headphones;
    }}
