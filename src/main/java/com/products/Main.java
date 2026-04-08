package com.products;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Основной класс приложения с консольным интерфейсом
 */
public class Main {
    private static List<Product> products = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Система управления продуктами ===");
        
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readIntInput("Выберите пункт меню: ", 1, 5);
            
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    deleteProduct();
                    break;
                case 3:
                    displayAllProducts();
                    break;
                case 4:
                    compareProducts();
                    break;
                case 5:
                    running = false;
                    System.out.println("Программа завершена. До свидания!");
                    break;
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- Меню ---");
        System.out.println("1. Добавить новый элемент");
        System.out.println("2. Удалить элемент по индексу");
        System.out.println("3. Вывод всех элементов");
        System.out.println("4. Сравнение двух элементов на равенство");
        System.out.println("5. Завершение работы");
    }

    private static void addProduct() {
        System.out.println("\n--- Добавление продукта ---");
        System.out.println("Выберите тип продукта:");
        System.out.println("1. Фрукт");
        System.out.println("2. Молочная продукция");
        System.out.println("3. Мясо");
        
        int typeChoice = readIntInput("Ваш выбор: ", 1, 3);
        
        String name = readStringInput("Введите название: ");
        double price = readDoubleInput("Введите цену: ", 0, 1000000);
        
        Product product = null;
        
        switch (typeChoice) {
            case 1: // Фрукт
                String origin = readStringInput("Введите страну происхождения: ");
                double weight = readDoubleInput("Введите вес (г): ", 0, 10000);
                product = new Fruit(name, price, origin, weight);
                break;
            case 2: // Молочная продукция
                int fatContent = readIntInput("Введите процент жирности: ", 0, 100);
                String expirationDate = readStringInput("Введите срок годности: ");
                product = new DairyProduct(name, price, fatContent, expirationDate);
                break;
            case 3: // Мясо
                String meatType = readStringInput("Введите тип мяса: ");
                double meatWeight = readDoubleInput("Введите вес (г): ", 0, 50000);
                product = new Meat(name, price, meatType, meatWeight);
                break;
        }
        
        if (product != null) {
            products.add(product);
            System.out.println("Продукт успешно добавлен!");
        }
    }

    private static void deleteProduct() {
        if (products.isEmpty()) {
            System.out.println("Список продуктов пуст.");
            return;
        }
        
        int index = readIntInput("Введите индекс элемента для удаления (0-" + (products.size() - 1) + "): ", 
                                  0, products.size() - 1);
        
        Product removed = products.remove(index);
        System.out.println("Удален продукт: " + removed);
    }

    private static void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("Список продуктов пуст.");
            return;
        }
        
        System.out.println("\n--- Список продуктов ---");
        for (int i = 0; i < products.size(); i++) {
            System.out.println(i + ": " + products.get(i));
        }
    }

    private static void compareProducts() {
        if (products.size() < 2) {
            System.out.println("Недостаточно элементов для сравнения (минимум 2).");
            return;
        }
        
        int index1 = readIntInput("Введите индекс первого элемента (0-" + (products.size() - 1) + "): ", 
                                   0, products.size() - 1);
        int index2 = readIntInput("Введите индекс второго элемента (0-" + (products.size() - 1) + "): ", 
                                   0, products.size() - 1);
        
        Product p1 = products.get(index1);
        Product p2 = products.get(index2);
        
        System.out.println("Элемент 1: " + p1);
        System.out.println("Элемент 2: " + p2);
        
        if (p1.equals(p2)) {
            System.out.println("Элементы равны!");
        } else {
            System.out.println("Элементы не равны.");
        }
    }

    private static int readIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Число должно быть в диапазоне от " + min + " до " + max);
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите корректное число!");
                scanner.nextLine(); // consume invalid input
            }
        }
    }

    private static double readDoubleInput(String prompt, double min, double max) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = scanner.nextDouble();
                scanner.nextLine(); // consume newline
                
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Число должно быть в диапазоне от " + min + " до " + max);
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите корректное число!");
                scanner.nextLine(); // consume invalid input
            }
        }
    }

    private static String readStringInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        while (input.trim().isEmpty()) {
            System.out.println("Строка не может быть пустой!");
            System.out.print(prompt);
            input = scanner.nextLine();
        }
        return input.trim();
    }
}
