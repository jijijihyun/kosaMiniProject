package com.kosa.libaraySystem.controller;

import java.util.Scanner;

public class PublisherController {
    private Scanner sc = new Scanner(System.in);
    public void managePublishers() {
        while (true) {
            System.out.println("저자 관리");
            System.out.println("1. 저자 추가");
            System.out.println("2. 저자 정보 수정");
            System.out.println("3. 저자 정보 삭제");
            System.out.println("4. 저자 정보 조회");
            System.out.println("5. 나가기");

            int pick = sc.nextInt();


            switch (pick) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:
                    System.out.println("관리자 매뉴로 돌아갑니다");
                    return;

                default:
                    System.out.println("잘못된 선택입니다.");

            }

        }

    }


}
