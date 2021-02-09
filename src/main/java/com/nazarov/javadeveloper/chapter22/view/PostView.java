package com.nazarov.javadeveloper.chapter22.view;

import com.nazarov.javadeveloper.chapter22.controllers.PostController;
import com.nazarov.javadeveloper.chapter22.entity.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PostView {
    private final PostController postController;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public PostView() {
        this.postController = new PostController();
    }

    public Post get(Post post) {
        return postController.get(post);
    }

    public Post get(String content) {
        return postController.get(content);
    }

    public List<Post> getAll(Long writerId){
        return postController.getAll(writerId);
    }

    public void update() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите контент для редактирования: ");
        String searchPost = sc.nextLine();
        System.out.print("Запись для редактирования: ");
        Post find = get(searchPost);
        System.out.print("Введите новое значение: ");
        String updatePostContent = sc.nextLine();
        System.out.print(ANSI_RED + "Существующая запись будет заменена на \"" +
                updatePostContent + "\"" +
                " (Y/N): " +
                ANSI_RESET);

        String input = sc.next();
        if (input.toLowerCase().equals("y")) {
            find.setContent(updatePostContent);
            Post result = postController.update(find);
            if (result != null) {
                System.out.println(ANSI_GREEN +
                        "Запись отредактирована на: " + result +
                        ANSI_RESET);
            }
        } else System.out.println("Редактирование отменено пользователем.");

        sc.close();
    }

    public void save() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите id создателя.");
        Long writersId = sc.nextLong();
        System.out.print("Введите публикацию: ");
        List<Post> posts = createPostDialog(writersId);
        posts = posts.stream()
                .peek(postController::save)
                .collect(Collectors.toList());

        System.out.println(ANSI_GREEN + "Контент: " + toString(posts) + " сохранен." + ANSI_RESET);
        sc.close();
    }

    public List<Post> createPostDialog(Long writerId) {
        Scanner sc = new Scanner(System.in);
        List<Post> posts = new ArrayList<>();

        System.out.println("Введите публикацию(и), или Enter для завершения ввода: ");
        System.out.print("Введите публикацию №" + (posts.size() + 1) + ": ");
        String content;
        while (!(content = sc.nextLine()).equals("")) {
            posts.add(new Post(writerId, content));
            System.out.print("Введите публикацию №" + (posts.size() + 1) + ": ");
        }

        return posts;
    }

    public String toString(List<Post> posts) {
        AtomicInteger count = new AtomicInteger(1);
        StringBuilder sb = new StringBuilder();
        posts.forEach(p -> {
            sb.append("\t\t" + count.getAndIncrement() + ". ");
            sb.append("w_id:" + p.getWritersId() + " ");
            sb.append("content:" + p.getContent() + " ");
            sb.append("create:" + p.getCreated() + " ");
            sb.append("update:" + p.getUpdated() + " " + "\n");
        });

        return sb.toString();
    }

    public void print(List<String> postList) {
        AtomicInteger count = new AtomicInteger(1);
        postList.forEach(p -> System.out.println(ANSI_GREEN + "\t\t" + count.getAndIncrement() + p + "\n" + ANSI_RESET));
    }
}
