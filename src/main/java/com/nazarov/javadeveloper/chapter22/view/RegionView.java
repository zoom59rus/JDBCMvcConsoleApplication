package com.nazarov.javadeveloper.chapter22.view;

import com.nazarov.javadeveloper.chapter22.controllers.RegionController;
import com.nazarov.javadeveloper.chapter22.entity.Region;

import java.util.Scanner;

public class RegionView {
    private final RegionController regionController;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public RegionView() {
        this.regionController = new RegionController();
    }

    public Region getRegion(String name) {
        return regionController.get(name);
    }

    public void update() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите запись для редактирования: ");
        String searchName = sc.nextLine();
        System.out.print("Запись для редактирования: ");
        Region search = getRegion(searchName);
        toString(search);
        System.out.print("Введите новое значение: ");
        String updateRegionName = sc.nextLine();
        System.out.print(ANSI_RED + "Существующая запись будет заменена на \"" +
                updateRegionName + "\"" +
                " (Y/N): " +
                ANSI_RESET);

        String input = sc.next();
        if (input.toLowerCase().equals("y")) {
            search.setName(updateRegionName);
            Region result = regionController.update(search);
            if (result != null) {
                System.out.println(ANSI_GREEN +
                        "Запись отредактирована на: " + toString(search) +
                        ANSI_RESET);

            }
        } else System.out.println("Редактирование отменено пользователем.");

        sc.close();
    }

    public Region save() {
        Region region = regionController.save(createRegionDialog());
        if (region.getId() != null) {
            System.out.println(ANSI_GREEN + "Регион: " + toString(region) + " сохранен." + ANSI_RESET);
        } else System.err.println("Не удалось сохранить.");

        return region;
    }

    public Region createRegionDialog() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите регион: ");
        String region = sc.nextLine();
        while (!matchRegion(region)) {
            System.err.print("Вы ошиблись в написании региона, попробуйте еще раз.");
            System.out.print("Введите регион: ");
            region = sc.nextLine();
        }
        return new Region(null, region);
    }

    public String toString(Region region) {
        String prepare = String.format("id:%d name:%s", region.getId(), region.getName());

        return ANSI_GREEN + prepare + ANSI_RESET;
    }

    private boolean matchRegion(String region) {
        if (region.matches("[A-я\\s]+")) {
            return true;
        }

        return false;
    }
}