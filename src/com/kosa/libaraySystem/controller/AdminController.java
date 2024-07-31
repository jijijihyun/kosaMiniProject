package com.kosa.libaraySystem.controller;

import java.util.Scanner;

public class AdminController {
    private Scanner scanner = new Scanner(System.in);

    public void showAdminMenu() {
        while(true) {
            System.out.println("\n------     관리자 메뉴     ------");
            System.out.println("[1] 사용자 관리");
            System.out.println("[2] 도서 관리");
            System.out.println("[3] 카테고리 관리");
            System.out.println("[4] 로그아웃");
            System.out.print(">> ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1 :
//                    new UserController().manageUsers();
                    break;
                case 2 :
//                    new BookController().manageBooks();
                    break;
                case 3 :
//                    new CategoryController().manageCategories();
                    break;
                case 4 :
                    System.out.println("로그아웃 되었습니다.");
                    return;
                default :
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }
}
