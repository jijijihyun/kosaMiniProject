package com.kosa.libaraySystem.controller;

import java.util.Scanner;

public class AdminController {
    private Scanner scanner = new Scanner(System.in);

    public void showAdminMenu() {
        while(true) {
            System.out.println("\n------     관리자 메뉴     ------");
            System.out.println("[1] 도서 관리");
            System.out.println("[2] 카테고리 관리");
            System.out.println("[3] 작가 관리");
            System.out.println("[4] 출핀사 관리");
            System.out.println("[5] 로그아웃");
            System.out.print(">> ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            // 20240805 수정\
            // 김남영 메뉴 수정
            switch(choice) {
                case 1 :
                    new BookController().manageBooks();
                    break;
                case 2 :
                    new CategoryController().manageCategories();
                    break;
                case 3 :
                    new AuthorController().manageAuthor();
                    break;
                case 4 :
                    new PublisherController().managePublishers();
                    break;
                case 5 :
                    System.out.println("로그아웃 되었습니다.");
                    return;
                default :
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }
}
