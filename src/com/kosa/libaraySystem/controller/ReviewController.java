package com.kosa.libaraySystem.controller;

import com.kosa.libaraySystem.model.User;
import com.kosa.libaraySystem.service.ReviewService;
import com.kosa.libaraySystem.service.impl.ReviewServiceImpl;

import java.sql.SQLException;
import java.util.Scanner;

/*
리뷰 컨트롤러 플로우

리뷰작성  - 돌아가기
리뷰콜


 */
public class ReviewController {
    private ReviewService reviewService = new ReviewServiceImpl();


    public void reviewControl(User user)throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n1. 리뷰 작성\n2. 돌아 가기");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n리뷰를 입력할 도서명을 입력하세요");
                    String bookTitle = scanner.nextLine();

                    if (reviewService.canSetReview(bookTitle)) {//임시
                        StringBuilder reviewBuilder = new StringBuilder();
                        System.out.println("리뷰를 입력해주세요. [등록 방법: 등록 입력]");
                        while (true) {
                            String input = scanner.nextLine();
                            if ("등록".equals(input)) {
                                break; // '등록' 입력 시 반복문 탈출
                            }
                            reviewBuilder.append(input).append("\n"); // 입력된 리뷰를 StringBuilder에 추가
                        }
                        reviewService.setReview(user, bookTitle, reviewBuilder.toString());//여기에 실제 리뷰내용을 데이터베이스에 접근하는 내용

                    } else {
                        System.out.println("\n유효한 책이 아닙니다.");
                    }
                    break;
                case 2:
                    System.out.println("\n유저 메인으로 돌아갑니다.");
                    isRunning= false;
                    break;
                default:
                    System.out.println("\n유효하지 않은 입력입니다.");
                    break;
            }
        }
    }

    public void writeReview() {
    }
}
