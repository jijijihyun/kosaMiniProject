package com.kosa.libaraySystem.controller;

import com.kosa.libaraySystem.model.Author;
import com.kosa.libaraySystem.service.AuthorService;
import com.kosa.libaraySystem.service.BookService;
import com.kosa.libaraySystem.service.impl.AuthorServiceImpl;
import com.kosa.libaraySystem.service.impl.BookServiceImpl;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class AuthorController {
    private Scanner scanner = new Scanner(System.in);
    private AuthorService authorService = new AuthorServiceImpl();
    private BookService bookService = new BookServiceImpl();

    public void manageAuthor() {
        while (true) {
            System.out.println("저자 관리");
            System.out.println("1. 저자 추가");
            System.out.println("2. 저자 정보 수정");
            System.out.println("3. 저자 정보 삭제");
            System.out.println("4. 저자 정보 조회");
            System.out.println("5. 나가기");

            // 정수를 입력해 달라고 루프 걸기

            int pick =  setInteger();





            switch (pick) {
                case 1:
                    authorInsert();
                    break;
                case 2:
                    authorUpdate();
                    break;
                case 3:
                    authorDelete();
                    break;
                case 4:
                    authorSelect();
                    break;
                case 5:
                    System.out.println("관리자 매뉴로 돌아갑니다");
                    return;

                default:
                    System.out.println("잘못된 선택입니다.");

            }

        }
    }


    // 저자 추가
    public void authorInsert() {
        

        System.out.println("추가 하실 저자 이름을 입력해 주세요");

//        String aname = scanner.next();
        String aname = setStr();

        if(aname == null){
            return;
        }


        List<Author> list = authorService.findAuthorByAll();
        for (int i = 0; i < list.size(); i++) {
            if (aname.equals(list.get(i).getAuthorName())) {
                System.out.println("이미 존재 하는 이름 입니다");
                return;
            }
        }
        authorService.addAuthorName(aname);
        System.out.println("추가를 완료 하였습니다.");
        
    }

    // 저자 정보 수정
    public void authorUpdate() {


        System.out.println("수정 하실 저자 이름을 입력해 주세요");
        // 있는 이름 인지 확인

//        String aname = scanner.next();
        String aname = setStr();



        List<Author> list = authorService.findAuthorByName(aname);
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                System.out.print(list.get(i).getAuthorNo() + "\t");
                System.out.println(list.get(i).getAuthorName());
            }
        } else {
            // 없으면 작가 없다고 알리고 종료
            System.out.println("검색 하신 저자는 찾지 못하였습니다");
            return;
        }
        // 번호 입력 했을떄 정보가 없으면 메소드 종료
        System.out.println("수정하실 저자 번호을 입력해 주세요");
//        int ano = scanner.nextInt();
       // int setAno = setInteger();

        int ano = setInteger();




        String check = bookService.reverseAuthorSearch(ano);
        if (check == null) {
            System.out.println("검색 하신 저자는 찾지 못하였습니다");
            return;
        }
        System.out.println("수정하실 저자의 새로운 이름을 입력해 주세요");
//        String anewname = scanner.next();
        String anewname = setStr();



        List<Author> list2 = authorService.findAuthorByAll();
        for (int i = 0; i < list2.size(); i++) {
            if (anewname.equals(list2.get(i).getAuthorName())) {
                System.out.println("이미 존재 하는 이름 입니다");
                return;
            }
        }
        authorService.updateByAuthorName(ano, anewname);
        System.out.println("수정이 완료 되엇습니다");

    }


    // 저자 정보 삭제
    public void authorDelete() {
        // 이름 정보 조회
        System.out.println("조회 하실 저자 이름을 입력해 주세요");
//        String aname = scanner.next();
        String aname = setStr();



        List<Author> list = authorService.findAuthorByName(aname);
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                System.out.print(list.get(i).getAuthorNo() + "\t");
                System.out.println(list.get(i).getAuthorName());
            }
        } else {
            System.out.println("검색 하신 저자는 찾지 못하였습니다");
            return;
        }

        // 번호로 저자 삭제
        // 삭제 실패 했을 떄 안내 문구 추가
        System.out.println("삭제하실 저자 번호을 입력해 주세요");
//        int ano = scanner.nextInt();
        int ano = setInteger();
        String check = bookService.reverseAuthorSearch(ano);
        if (check == null) {
            System.out.println("검색 하신 저자는 찾지 못하였습니다");
            return;
        }

        authorService.deletByAuthor(ano);
        System.out.println("삭제를 완료 했습니다,");
    }


    // 저자 정보 조회
    public void authorSelect() {
        System.out.println("정보 조회 하실 저자 이름을 입력해 주세요");
        List<Author> list = authorService.findAuthorByAll();
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i).getAuthorNo()+"\t");
            System.out.println(list.get(i).getAuthorName());
        }

    }


    // 문자 스캔 검증
    private String setStr(){
        try {
            String aname = scanner.nextLine();
            if(aname.isEmpty()){
                System.out.println("올바른 값을 입력 바랍니다.1");
                return null;
            }
            return aname;
        }catch (Exception e){
            System.out.println("올바른 값을 입력 바랍니다.2");
        }
        return null;
    }


    // 숫자 스캔 검증
    private int setInteger(){
        try {

            String ano = scanner.nextLine();
            if(ano.isEmpty()){
                System.out.println("올바른 값을 입력 바랍니다.5");
                return 0;
            }
            Integer a = Integer.parseInt(ano);
            return a;
        }catch (Exception e){
            System.out.println("올바른 값을 입력 바랍니다.6");
            return 0;
        }
    }


}
