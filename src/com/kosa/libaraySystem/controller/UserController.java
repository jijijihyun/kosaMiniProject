package com.kosa.libaraySystem.controller;

import com.kosa.libaraySystem.model.User;
import com.kosa.libaraySystem.service.UserService;
import com.kosa.libaraySystem.service.impl.UserServiceImpl;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserController {
    private UserService userService = new UserServiceImpl();
    private Scanner scanner = new Scanner(System.in);

    public void login() throws SQLException {
        System.out.print("ID : ");
        String userId = scanner.nextLine();
        System.out.print("비밀번호 : ");
        String password = scanner.nextLine();

        User user = userService.login(userId, password);
        if(user != null) {
            System.out.println("로그인 성공!!!");

            if(user.getRoleNo() == 1) {
                // 관리자 메뉴
                new AdminController().showAdminMenu();
            } else {
                // 사용자 메뉴
                new UserController().showUserMenu();
            }
        } else {
            System.out.println("로그인 실패!!! 사용자 ID 또는 비밀번호가 잘못되었습니다.");
        }
    }

    public void showUserMenu() {
        while(true) {
            System.out.println("\n------     사용자 메뉴     ------");
            System.out.println("[1] 도서 검색");
            System.out.println("[2] 대출 기록 조회");
            System.out.println("[3] 리뷰 작성");
            System.out.println("[4] 도서 대출");
            System.out.println("[5] 도서 반납");
            System.out.println("[6] 로그아웃");
            System.out.print(">> ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1 :
//                    new BookController().searchBooks();
                    break;
                case 2 :
//                    new LoanController().viewLoanRecords();
                    break;
                case 3 :
//                    new ReviewController().writeReview();
                    break;
                case 4 :

                    break;
                case 5 :

                    break;
                case 6 :
                    System.out.println("로그아웃 되었습니다.");
                    return;
                default :
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }

    public void register() throws SQLException {
        System.out.println("사용자 ID (영문자와 숫자만 입력, 5 ~ 20자)");
        System.out.print(">> ");
        String userId = scanner.nextLine();
        if(!isValidUserId(userId)) {
            System.out.println("유효하지 않는 사용자 ID입니다.");
            return;
        }

        System.out.println("비밀번호 (영문자와 숫자, 특수문자 포함, 8 ~ 20자)");
        System.out.print(">> ");
        String password = scanner.nextLine();
        if(!isValidPassword(password)) {
            System.out.println("유효하지 않는 비밀번호입니다.");
            return;
        }

        System.out.println("이름");
        System.out.print(">> ");
        String username = scanner.nextLine();

        System.out.println("이메일");
        System.out.print(">> ");
        String email = scanner.nextLine();
        if(!isValidEmail(email)) {
            System.out.println("유효하지 않는 이메일 형식입니다.");
            return;
        }

        System.out.println("역할 (1: 관리자, 2: 사용자)");
        System.out.print(">> ");
        int roleNo = scanner.nextInt();
        scanner.nextLine();

        User user = new User(userId, password, username, email, roleNo);

        try {
            userService.register(user);
            System.out.println("회원가입 성공!!!");
        } catch(Exception e) {
            System.out.println("회원가입 실패!!!");
        }
    }

    // 사용자 ID 유효성 검사(정규 표현식 사용)
    private boolean isValidUserId(String userId) {
        return Pattern.matches("^[a-zA-Z0-9]{5,20}$", userId);
    }

    // 비밀번호 유효성 검사(정규 표현식 사용)
    private boolean isValidPassword(String password) {
        return Pattern.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", password);
    }

    // 이메일 유효성 검사(정규 표현식 사용)
    private boolean isValidEmail(String email) {
        return Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email);
    }
}
