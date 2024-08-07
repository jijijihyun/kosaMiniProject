package com.kosa.libaraySystem.controller;


import com.kosa.libaraySystem.model.Author;
import com.kosa.libaraySystem.model.Publisher;
import com.kosa.libaraySystem.service.BookService;
import com.kosa.libaraySystem.service.PublisherService;
import com.kosa.libaraySystem.service.impl.BookServiceImpl;
import com.kosa.libaraySystem.service.impl.PublisherServiceImpl;

import java.util.List;
import java.util.Scanner;

public class PublisherController {
    private BookService bookService = new BookServiceImpl();
    private PublisherService publisherService = new PublisherServiceImpl();
    private Scanner scanner = new Scanner(System.in);

    public void managePublishers() {
        while (true) {
            System.out.println("출판사 관리");
            System.out.println("1. 출판사 추가");
            System.out.println("2. 출판사 정보 수정");
            System.out.println("3. 출판사 정보 삭제");
            System.out.println("4. 출판사 정보 조회");
            System.out.println("5. 나가기");

            int pick =setInteger();


            switch (pick) {
                case 1:
                    publisherInsert();
                    break;
                case 2:
                    publisherUpdate();
                    break;
                case 3:
                    publisherDelete();
                    break;
                case 4:
                    publisherSelect();
                    break;
                case 5:
                    System.out.println("관리자 매뉴로 돌아갑니다");
                    return;

                default:
                    System.out.println("잘못된 선택입니다.");

            }
        }
    }


    private void publisherInsert() {

        System.out.println("추가 하실 출판사");
        String pname = setStr();

        if(pname == null){
            return;
        }


        List<Publisher> list = publisherService.findPublisherByAll();
        for (int i = 0; i < list.size(); i++) {
            if (pname.equals(list.get(i).getName())) {
                System.out.println("이미 존재 하는 이름 입니다");
                return;
            }
        }
        publisherService.addPublisherName(pname);
        System.out.println("추가를 완료 하였습니다.");

    }

    private void publisherUpdate() {

        System.out.println("수정 하실 출판사 이름을 입력해 주세요");

        String pname = setStr();
        List<Publisher> list =publisherService.findPublisherByName(pname);
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                System.out.print(list.get(i).getPublisherNo() + "\t");
                System.out.println(list.get(i).getName());
            }
        } else {
            // 없으면 작가 없다고 알리고 종료
            System.out.println("검색 하신 출판사는 찾지 못하였습니다");
            return;
        }
        // 번호 입력 했을떄 정보가 없으면 메소드 종료
        System.out.print("수정하실 출판사 번호를 입력 바랍니다.");
        int pno = setInteger();

        String check = bookService.reversePublisherSearch(pno);
        if (check == null) {
            System.out.println("검색 하신 출판사는 찾지 못하였습니다");
            return;
        }
        System.out.println("수정하실 출판사의 새로운 이름을 입력해 주세요");

        String pnewname = setStr();
        List<Publisher> list2 = publisherService.findPublisherByAll();
        for (int i = 0; i < list2.size(); i++) {
            if (pnewname.equals(list2.get(i).getName())) {
                System.out.println("이미 존재 하는 이름 입니다");
                return;
            }
        }
        publisherService.updateByPublisherName(pno, pnewname);
        System.out.println("수정이 완료 되엇습니다");


    }

    private void publisherDelete() {
        System.out.println("조회 하실 출판사 이름을 입력해 주세요");

        String pname = setStr();
        List<Publisher> list =  publisherService.findPublisherByName(pname);
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                System.out.print(list.get(i).getPublisherNo()+"\t");
                System.out.println(list.get(i).getName());
            }
        } else {
            System.out.println("검색 하신 출판사는 찾지 못하였습니다");
            return;
        }

        // 번호로 저자 삭제
        // 삭제 실패 했을 떄 안내 문구 추가
        System.out.println("삭제하실 출판사 번호을 입력해 주세요");
        int pno = setInteger();
        String check = bookService.reversePublisherSearch(pno);
        if (check == null) {
            System.out.println("검색 하신 출판사는 찾지 못하였습니다");
            return;
        }

        publisherService.deletByPublisher(pno);
        System.out.println("삭제를 완료 했습니다,");

    }

    private void publisherSelect() {

       List<Publisher>  list=  publisherService.findPublisherByAll();
       for(int i=0; i < list.size(); i++){
           System.out.print(list.get(i).getPublisherNo()+"\t");
           System.out.println(list.get(i).getName());
       }
    }



    private String setStr(){
        try {
            String aname = scanner.nextLine();
            if(aname.isEmpty()){
                System.out.println("올바른 값을 입력 바랍니다.");
                return null;
            }
            return aname;
        }catch (Exception e){
            System.out.println("올바른 값을 입력 바랍니다.");
        }
        return null;
    }



    // 정수를 입력해 달라고 루프걸기
    private int setInteger(){
        try {
            String ano = scanner.nextLine();
            if(ano.isEmpty()){
                System.out.println("올바른 값을 입력 바랍니다.");
                return 0;
            }
            Integer a = Integer.parseInt(ano);
            return a;
        }catch (Exception e){
            System.out.println("올바른 값을 입력 바랍니다.");
            return 0;
        }
    }


}
