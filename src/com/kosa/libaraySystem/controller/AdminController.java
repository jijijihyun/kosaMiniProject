package com.kosa.libaraySystem.controller;

import java.sql.SQLException;
import java.util.Scanner;

public class AdminController {
    private Scanner scanner = new Scanner(System.in);

    public void showAdminMenu() throws SQLException {
        while(true) {
            System.out.println("\n------     관리자 메뉴     ------");
            System.out.println("[1] 도서 관리");
            System.out.println("[2] 저자 관리");
            System.out.println("[3] 출판사 관리");
            System.out.println("[4] 카테고리 관리");
            System.out.println("[5] 로그아웃");
            System.out.print(">> ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            // 20240807 수정
            // 김지현 메뉴 수정
            switch(choice) {
                case 1 :
                    new BookController().manageBooks();
                    break;
                case 2 :
                    new AuthorController().manageAuthor();
                    break;
                case 3 :
                    new PublisherController().managePublishers();
                    break;
                case 4 :
                    new CategoryController().manageCategories();
                    break;
                case 5 :
                    System.out.println("\n📌로그아웃 되었습니다.");
                    return;
                default :
                    System.out.println("\n🚫잘못된 선택입니다.");
            }
        }
    }
}
