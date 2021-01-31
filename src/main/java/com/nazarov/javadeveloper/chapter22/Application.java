package com.nazarov.javadeveloper.chapter22;

import com.nazarov.javadeveloper.chapter22.view.UserMenu;

public class Application {
    public static void main(String[] args) {
        UserMenu userMenu = UserMenu.getInstance();
        userMenu.showMainMenu();
    }
}