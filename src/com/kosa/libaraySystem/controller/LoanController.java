package com.kosa.libaraySystem.controller;

import com.kosa.libaraySystem.model.Book;
import com.kosa.libaraySystem.model.BookGrouped;
import com.kosa.libaraySystem.model.BookLoanInfo;
import com.kosa.libaraySystem.model.User;
import com.kosa.libaraySystem.service.*;
import com.kosa.libaraySystem.service.impl.*;
import com.kosa.libaraySystem.util.TupleKNY;


import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LoanController {
    //책테이블에 대한 로직
    BookService bookService = new BookServiceImpl();

    //대출테이블에 대한 로직
    LoanService loanService = new LoanServiceImpl();

    //작가 이름 가져와야해..
    AuthorService authorService = new AuthorServiceImpl();

    //카테고리명 가져와야해...
    CategoryService categoryService = new CategoryServiceImpl();

    //출판사명 가져와야해...
    PublisherService publisherService = new PublisherServiceImpl();


    public void startLoanProcess(User user) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.printf("\n------     %s ( %s ) 님의 대출 페이지     ------\n", user.getUserId(), user.getUsername());
            System.out.println("[1] 대출 실행");
            System.out.println("[2] 뒤로가기");
            System.out.print(">> ");
            int choice = user.safeGetIntInput();

            if (choice == 1) {
                executeLoan(scanner, user);
            } else if (choice == 2) {
                break;
            } else {
                System.out.println("🚫유효한 입력이 아닙니다.");
            }
        }
    }

    public void startReturnProcess(User user) throws SQLException{
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.printf("\n------     %s ( %s ) 님의 반납 페이지     ------\n", user.getUserId(), user.getUsername());
            System.out.println("[1] 반납 실행");
            System.out.println("[2] 뒤로가기");
            System.out.print(">> ");
            int choice = user.safeGetIntInput();

            if (choice == 1) {
                executeReturn(scanner, user);
            } else if (choice == 2) {
                break;
            } else {
                System.out.println("🚫유효한 입력이 아닙니다.");
            }
        }
    }

    private void executeReturn(Scanner scanner, User user) {
        //1. 내가 반납한 리스트를 반환하는 함수
        //  1-1.유저번호 + 반납일 없음 정보로 bookloans 테이블의 row 추출
        //  1-2. row의 bookNO를 기반으로 북리스트 쫙 뽑기
        System.out.println("------     도서 반납     ------");
        List<BookLoanInfo> loans;
        try{
            loans = loanService.readUserNotYetReturnDataList(user);
            if(loans.isEmpty()) throw new SQLException();
        }catch(SQLException e){
            System.out.println("📌빌린 도서가 없습니다.");
            return;
        }
        List<Book> books = new ArrayList<>();

        for(BookLoanInfo BLI : loans){
            try {
                books.add(bookService.readDataByBookNum(BLI.getBookNo()));
            } catch (SQLException e) {
                System.out.println("🚫유효한 입력이 아닙니다.");
                return;
            }
        }

        //2. 반환한 북 리스트 + loanInfo 리스트를 매개변수로 받아 인터페이스에 출력하는 함수
        //  도서번호/책제목/작가/카테고리/대출일/반납예정일
        try {
            showUserLoanList(books, loans, user);
        } catch (SQLException e) {
            System.out.println("🚫유효한 입력이 아닙니다.");
            return;
        }

        //3. 반납을 실행할 번호 입력받기
        // 3-1. 해당북넘버 참조하는 북론즈 테이블에 먼저 duedate 오늘 날짜 인서트
        // 3-2. 해당북넘버 북즈 테이블에 들어가서 status 업데이트

        //3
        System.out.println("반납할 도서의 번호(반납 안하고 돌아가기 : quit)");
        System.out.print(">> ");
        String userInput = null;
        int userChoice = 0;
        try{
            userInput = scanner.nextLine();
            if(userInput.equals("quit")) {
                System.out.println("\n📌반납페이지로 돌아갑니다.\n");
                return;
            }
            userChoice = Integer.parseInt(userInput);

        }catch(IllegalStateException | NoSuchElementException |NumberFormatException e){
            System.out.println("🚫유효한 입력이 아닙니다.");
            return;
        }

        //3-1
        try{
            Book b = bookService.readDataByBookNum(userChoice);
            loanService.updateReturnDateByBookNo(b.getBookNo(), user.getUserNo());
            loanService.updateStatusBookByBook(b, "대출가능");
            System.out.println("📌반납 완료");
        }catch(SQLException e){
            System.out.println("🚫유효한 입력이 아닙니다");
            return;
        }
    }

    private void showUserLoanList(List<Book> books, List<BookLoanInfo> loans, User user) throws SQLException {
        System.out.printf("\n------      %-10s( %s ) 님의 도서 대출 리스트     ------\n", user.getUserId(), user.getUsername());
        //  도서번호/책제목/작가/카테고리/대출일/반납기한
        System.out.printf("%-5s %-40s  %-10s  %-15s %-15s  %-10s  %-10s\n", "번호","책 제목", "작가", "대분류", "소분류", "대출일", "반납기한");
        for(int i =0; i< loans.size(); i++){
            TupleKNY<String,String> categoriesName =
                    categoryService.getHierarchyCategory(categoryService.getCategoryByCategoryNo(books.get(i).getCategoryNo()));
            String bigCateName = categoriesName.getKey();
            String smallCateName = categoriesName.getValue();

            LocalDate loanDatelocal = loans.get(i).getLoanDate().toLocalDate();
            Date dueDate = Date.valueOf(loanDatelocal.plusDays(7));
            System.out.printf("%-5d %-40s  %-10s  %-15s %-15s %-10s  %-10s\n",
                    loans.get(i).getBookNo(), books.get(i).getTitle(),
                    authorService.getAuthorNameByNum(books.get(i).getAuthorNo()),
                    bigCateName,
                    smallCateName,
                    loans.get(i).getLoanDate(), dueDate);
        }
    }


    private void executeLoan(Scanner scanner, User user) throws SQLException {
        System.out.print(" \uD83D\uDD0D 입력 도서명: ");
        String bookTitle = scanner.nextLine();
        int stepNum;
        String targetBookTitle = null;
        //라이크로 유사 도서명 리스트 출력 -> bookService에서 제공해야겠지.
        List<BookGrouped> likebooks = bookService.getBookGroupedSearchTitle(bookTitle);
        if(likebooks.isEmpty()){
            System.out.println("🚫유효한 입력이 아닙니다.");
            return;
        }
        else{
            System.out.println(" ✅ 다음 중 대출하고자 하는 도서명의 번호를 선택해주세요. ");
            showlikeBookList(likebooks);
            stepNum = scanner.nextInt();
            scanner.nextLine();  // 개행 문자 소비

            targetBookTitle = likebooks.get(stepNum-1).getBookTitle();
        }

        //번호 선택해서 리스트에서 어떤 도서명을 선택할지 확실하게 정한뒤
        //같은 도서명으로 리스트 출력 대출가능 정보까지 함께
        if(targetBookTitle == null){
            System.out.println("🚫유효한 입력이 아닙니다.");
            return;
        }
        else{
            stepNum=0;
            List<Book> books = bookService.getBooksSearchTitle(targetBookTitle);
            if(books.isEmpty()){
                System.out.println("🚫유효한 입력이 아닙니다.");
                return;
            }
            else{
                System.out.println(" ✅ 다음 중 대출하고자 하는 도서의 번호를 골라주세요. ");
                showRealBookList(books);
                stepNum = scanner.nextInt();
                scanner.nextLine();  // 개행 문자 소비
            }
            if(books.get(stepNum-1).getStatus().equals("대출중")){
                System.out.println("📌해당 도서는 대출이 불가합니다.");
            }
            else{
                loanService.createLoanRowByBook(books.get(stepNum-1), user);
                loanService.updateStatusBookByBook(books.get(stepNum-1), "대출중");
            }
        }
        //어떤 도서를 고를지 번호 선택해서 확실하게 정한다. -> 
        //대출 실행 ㄱㄱ

    }
    private void showlikeBookList(List<BookGrouped> bg) throws SQLException {
        if(bg.isEmpty())
        {
            System.out.println("\n📌해당 책은 없습니다.");
        }
        else{
            int idx = 1;
            System.out.printf("\n%-5s %-40s  %-10s  %-15s %-15s %-10s  %s\n", "번호","책명", "작가", "대분류","소분류", "출판사", "권수");
            //책정보가지고 출력하는 함수들 호출
            for(BookGrouped b: bg){
                TupleKNY<String,String> categoriesName =
                        categoryService.getHierarchyCategory(categoryService.getCategoryByName(b.getCategoryName()));
                String bigCateName = categoriesName.getKey();
                String smallCateName = categoriesName.getValue();
                System.out.printf("%-5d %-40s  %-10s  %-15s %-15s  %-10s  %3d\n",
                        idx++,
                        b.getBookTitle(),
                        formatString(b.getAuthorName(), 10),
                        formatString(bigCateName, 15),
                        formatString(smallCateName, 15),
                        formatString(b.getPublisherName(), 10),
                        b.getCnt()
                );
            }
        }
    }
    private String formatString(String str, int maxLength) {
        try {
            if(str.isEmpty() || str.isBlank() || str.equals(null))  return "-";
            if (str.length() > maxLength) return str.substring(0, maxLength - 3) + "...";
            return str;
        }catch(Exception e){
            e.printStackTrace();
        }
        return "-";
    }
    private void showRealBookList(List<Book> books) throws SQLException {
        if(books.isEmpty())
        {
            System.out.println("\n📌해당 책은 없습니다.");
        }
        else{
            int idx = 1;
            System.out.printf("\n%-5s %-40s  %-10s  %-15s %-15s %-10s  %s\n", "번호","책명", "작가", "대분류", "소분류", "출판사", "대출여부");
            //책정보가지고 출력하는 함수들 호출
            for(Book b: books){
                TupleKNY<String,String> categoriesName =
                        categoryService.getHierarchyCategory(categoryService.getCategoryByCategoryNo(b.getCategoryNo()));
                String bigCateName = categoriesName.getKey();
                String smallCateName = categoriesName.getValue();
                System.out.printf("%-5d %-40s  %-10s  %-15s %-15s  %-10s  %s\n",
                        idx++,
                        b.getTitle(),
                        formatString(authorService.getAuthorNameByNum(b.getAuthorNo()), 10),
                        formatString(bigCateName, 15),
                        formatString(smallCateName, 15),
                        formatString(publisherService.getPublisherNameByNum(b.getPublisherNo()), 10),
                        b.getStatus()
                );
            }
        }
    }

}
