package com.kosa.libaraySystem.controller;

import java.util.Scanner;

public class CategoryController {

    /*
    관리자 코드
     */
    private Scanner scanner = new Scanner(System.in);

    public void manageCategories() {
        System.out.println("\n------    카태고리 관리메뉴     ------");
        System.out.println("[1] 카테고리 추가");
        System.out.println("[2] 카테고리 정보 수정");
        System.out.println("[3] 카테고리 삭제");
        System.out.println("[4] 카테고리 정보 조회");
        System.out.println("[5] 로그아웃");
        System.out.print(">> ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                // 카테고리 추가
                addCategories();
                break;
            case 2:
                // 카테고리 정보 수정
                updateCategories();
                break;
            case 3:
                // 카테고리 삭제
                deleteCategories();
                break;
            case 4:
                // 카테고리 정보 조회
                searchCategories();
                break;
            case 5:
                System.out.println("로그아웃 되었습니다.");
                return;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

    public void addCategories(){

    }
    public void updateCategories(){

    }
    public void deleteCategories(){

    }
    public void searchCategories(){

    }

    /*
    유저 코드
     */


}
