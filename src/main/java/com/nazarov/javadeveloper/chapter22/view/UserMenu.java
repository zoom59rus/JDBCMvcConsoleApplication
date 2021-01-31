package com.nazarov.javadeveloper.chapter22.view;

import com.nazarov.javadeveloper.chapter22.entity.Writer;

import java.util.Scanner;

public final class UserMenu {
    private static UserMenu INSTANCE;
    private final WriterView writerView;

    private UserMenu() {
        this.writerView = new WriterView();
    }

    public static UserMenu getInstance() {
        if (INSTANCE == null) {
            synchronized (UserMenu.class) {
                if(INSTANCE == null){
                    INSTANCE = new UserMenu();
                }
            }
        }

        return INSTANCE;
    }

    public void showMainMenu() {
        System.out.println("Выберите действие:");
        System.out.println();
        System.out.println("[1] Добавить пользователя.");
        System.out.println("[2] Найти пользователя.");
        System.out.println("[3] Изменить пользователя.");
        System.out.println("[4] Удалить пользователя.");
        System.out.println("[0] Выход.");

        switch (matchMenuNumber()) {
            case 1: {
                createUserMenu();
            }
            case 2: {
                searchUserMenu();
            }
            case 3: {
                updateUserMenu();
            }
            case 4: {
                removeUserMenu();
            }
            case 0: {
                System.exit(0);
            }
            default: {
                System.out.println("Выбран не существующий пункт меню.");
                showMainMenu();
            }
        }
    }

    public void createUserMenu() {
        System.out.println("Меню создания пользователя.");
        writerView.createUserDialog();
        showMainMenu();
    }

    public void searchUserMenu() {
        System.out.println("Меню поиска пользователя. Выберите критерий поиска:");
        System.out.println("[1] Поиск по имени.");
        System.out.println("[2] Поиск по фамилии.");
        System.out.println("[3] Поиск по имени и фамилии.");
        System.out.println("[4] Поиск по контенту.");
        System.out.println("[5] Поиск по региону.");
        System.out.println("[6] Выйти в главное меню.");
        System.out.println("[0] Выход.");

        int select = matchMenuNumber();
        switch (select) {
            case 1: {
                writerView.searchUserDialog(1);
                sleep();
                searchUserMenu();
            }
            case 2: {
                writerView.searchUserDialog(2);
                sleep();
                searchUserMenu();
            }
            case 3: {
                writerView.searchUserDialog(3);
                sleep();
                searchUserMenu();
            }
            case 4: {
                writerView.searchUserDialog(4);
                sleep();
                searchUserMenu();
            }
            case 5: {
                writerView.searchUserDialog(5);
                sleep();
                searchUserMenu();
            }
            case 6: {
                showMainMenu();
            }
            case 0: {
                System.exit(0);
            }
            default: {
                System.out.println("Выбран не существующий пункт меню.");
                searchUserMenu();
            }
        }

        showMainMenu();
    }

    public void removeUserMenu(){
        System.out.println("Меню удаления пользователя:");
        writerView.removeUserDialog();
        showMainMenu();
    }

    public void updateUserMenu(){
        System.out.println("Меню редактирования пользователя:");
        writerView.updateUserDialog();
        showMainMenu();
    }


    private int matchMenuNumber() {
        int select = -1;

        Scanner sc = new Scanner(System.in);
        System.out.print("Выберите пункт меню: ");
        String input = sc.nextLine();

        while (!input.matches("[0-9]")) {
            System.out.println("Ошибка ввода, попробуйте еще раз.");
            System.out.print("Выберите пункт меню: ");
            input = sc.nextLine();
        }

        try {
            select = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка!!! Не могу спарсить число!");
        }

        return select;
    }

    private void sleep() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Для продолжения нажмите любую клавишу...");
        sc.nextLine();
    }
}