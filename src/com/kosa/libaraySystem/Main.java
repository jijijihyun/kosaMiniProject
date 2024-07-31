package com.kosa.libaraySystem;

import com.kosa.libaraySystem.controller.UserController;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static UserController userController = new UserController();

    public static void main(String[] args) throws SQLException {
        while(true) {
            System.out.println("\n------ 변경점 ------");
            System.out.println("\n------ KOSA 도서 관리 시스템 ------");
            System.out.println("[1] 로그인");
            System.out.println("[2] 회원가입");
            System.out.println("[3] 종료");
            System.out.print(">> ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    userController.login();
                    break;
                case 2:
                    userController.register();
                    break;
                case 3:
                    System.out.println("프로그램을 종료합니다.");
                    scanner.close();
                    return;
                default:
                    System.out.println("잘못된 선택입니다.");
                    break;
            }
        }
    }
}