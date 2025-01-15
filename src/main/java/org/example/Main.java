package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        String filepath = "src/punkte.txt";
        List<Ereignis> ereignisse = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an uppercase letter to filter student names:");
        String letter = scanner.nextLine().trim();
        filterStudentsByLetter(ereignisse, letter);

    }

    public static void filterStudentsByLetter(List<Ereignis> ereignisse, String letter) {
        ereignisse.stream()
                .filter(record -> ereignisse[1].startsWith(letter))
                .map(record -> ereignisse[1])
                .distinct()
                .forEach(System.out::println);
    }

}