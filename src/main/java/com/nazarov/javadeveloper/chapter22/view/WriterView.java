package com.nazarov.javadeveloper.chapter22.view;

import com.nazarov.javadeveloper.chapter22.controllers.UserController;
import com.nazarov.javadeveloper.chapter22.controllers.WriterController;
import com.nazarov.javadeveloper.chapter22.entity.Post;
import com.nazarov.javadeveloper.chapter22.entity.Region;
import com.nazarov.javadeveloper.chapter22.entity.Writer;
import com.nazarov.javadeveloper.chapter22.entity.dtos.WriterDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriterView {
    private final WriterController writerController;
    private final PostView postView;
    private final RegionView regionView;
    private final UserController userController;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public WriterView() {
        this.writerController = new WriterController();
        this.postView = new PostView();
        this.regionView = new RegionView();
        this.userController = new UserController();
    }

    public void createUserDialog() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Введите имя пользователя: ");
        String firstName = matchName();

        System.out.print("Введите фамилию пользователя: ");
        String lastName = matchName();

        Region region = regionView.createRegionDialog();
        List<Post> posts = postView.createPostDialog(null);
        WriterDto writer = new WriterDto(null, firstName, lastName, posts, region);

        System.out.println("Создан пользователь:");
        printWriter(writer);
        System.out.print(ANSI_RED + "Сохранить пользователя (Y/N)?: " + ANSI_RESET);

        String i = sc.nextLine();
        if ("y".equals(i.toLowerCase())) {
            writer = userController.save(writer);
            if (writer != null) {
                System.out.println(ANSI_GREEN + "Пользователь сохранен." + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Пользователь не сохранен." + ANSI_RESET);
            }
        }
        sleep();
    }

    public void searchUserDialog(int select) {
        switch (select) {
            case 1: {
                System.out.print("Введите имя пользователя: ");
                String firstName = matchName();
                Writer find = writerController.getByFirstName(firstName);
                System.out.println(find);
                break;
            }
            case 2: {
                System.out.print("Введите имя пользователя: ");
                String lastName = matchName();
                Writer find = writerController.getByLastName(lastName);
                System.out.println(find);
                break;
            }
            case 3: {
                System.out.println("Введите имя и фамилию пользователя: ");
                System.out.print("Введите имя: ");
                String firstName = matchName();
                System.out.print("Введите фамилию: ");
                String lastName = matchName();
                System.out.println(writerController.getByFirstName(firstName));
                System.out.println(writerController.getByFirstName(lastName));
                break;
            }
            case 4: {
                System.out.print("Введите публикацию пользователя: ");
                String content = matchName();
                Post post = postView.get(content);
                System.out.println(writerController.get(post.getId()));
                break;
            }
            case 5: {
                System.out.print("Введите регион пользователя: ");
                String region = matchName();
                Region find = regionView.getRegion(region);
                System.out.println(writerController.getByRegion(find.getId()));
                break;
            }
        }
    }

    public void removeUserDialog() {
        System.out.print("Введите id пользователя для удаления: ");
        Scanner sc = new Scanner(System.in);
        Long id = sc.nextLong();
        WriterDto writer = userController.get(id);
        printWriter(writer);
        System.out.print(ANSI_RED + "Пользователь будет удален, подтвердите (Y/N): " + ANSI_RESET);
        String input = sc.next();
        if ("y".equals(input.toLowerCase())) {
            userController.remove(id);
            if (writerController.get(writer.getId()) == null) {
                System.out.println(ANSI_GREEN + "Пользователь удален." + ANSI_RESET);
            } else System.err.println("Невозможно удалить пользователя \"" +
                    writer.getFirstName() + writer.getLastName() +
                    "\"" + ", или пользователь не существует.");
        }
    }
//
    public void updateUserDialog() {
        System.out.print("Введите id пользователя для редактирования: ");
        Scanner sc = new Scanner(System.in);
        Long id = sc.nextLong();

        WriterDto writer = userController.get(id);
        if (writer == null) {
            System.out.println(ANSI_GREEN + "Пользователь c id: " + id + " не найден." + ANSI_RESET);
            return;
        }

        System.out.println("Редактируемый пользователь:");
        printWriter(writer);

        System.out.println("Ведите новые записи или нажмите Enter для пропуска:");
        System.out.print("Введите имя: ");
        String input = sc.nextLine();
        if (!input.equals("")) {
            writer.setFirstName(input);
        }
        System.out.print("Введите фамилию: ");
        input = sc.nextLine();
        if (!input.equals("")) {
            writer.setLastName(input);
        }
        System.out.print("Введите регион: ");
        input = sc.nextLine();
        Region region = new Region(input);

        System.out.println("Отредактируйте публикации, или нажмите Enter для пропуска: ");
        List<Post> postList = new ArrayList<>();

        for (Post post : postView.getAll(writer.getId())) {
            System.out.println(ANSI_GREEN + post.getContent() + ANSI_RESET);
            System.out.print("Новая запись: ");
            String content = sc.nextLine();
            if (!content.equals("")) {
                post.setContent(content);
                postList.add(post);
            } else postList.add(post);

        }
        printWriter(writer);
        System.out.print(ANSI_RED + "Сохранить изменения пользователя? Y/N: " + ANSI_RESET);
        String s = sc.next();
        if ("y".equals(s.toLowerCase())) {
            writer.setRegion(region);
            writer.setPosts(postList);
            userController.update(writer);
            System.out.println(ANSI_GREEN + "Пользователь сохранен." + ANSI_RESET);
        } else System.out.println("Обновление отменено пользователем.");
    }

    private String matchName() {
        Scanner sc = new Scanner(System.in);
        String firstName = sc.nextLine();
        while (!firstName.matches("[A-zА-я]+")) {
            System.err.println("Вы ошиблись в написании имени, попробуйте еще раз.");
            System.out.print("Введите имя: ");
            firstName = sc.nextLine();
        }

        return firstName;
    }

    private void printWriter(WriterDto writer) {
        StringBuilder sb = new StringBuilder();
        sb
                .append("id: " + "\"" + writer.getId() + "\"" + "\n")
                .append("Имя: " + "\"" + writer.getFirstName() + "\"" + "\n")
                .append("Фамилия: " + "\"" + writer.getLastName() + "\"" + "\n")
                .append("Регион: " + "\"" + writer.getRegion().getName() + "\"" + "\n")
                .append("Публикации: \n")
                .append(postView.toString(writer.getPosts()));

        System.out.println(ANSI_GREEN + sb.toString() + ANSI_RESET);
    }

    private void sleep() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Для продолжения нажмите любую клавишу...");
        sc.nextLine();
    }
}