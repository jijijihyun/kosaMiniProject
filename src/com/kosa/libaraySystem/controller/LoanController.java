package com.kosa.libaraySystem.controller;

import com.kosa.libaraySystem.model.Book;
import com.kosa.libaraySystem.model.BookGrouped;
import com.kosa.libaraySystem.model.User;
import com.kosa.libaraySystem.service.*;
import com.kosa.libaraySystem.service.impl.*;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.util.List;
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
            System.out.println("1. 대출 실행");
            System.out.println("2. 돌아가기");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // 개행 문자 소비

            if (choice == 1) {
                executeLoan(scanner);
            } else if (choice == 2) {
                break;
            } else {
                System.out.println("유효한 입력이 아닙니다.");
            }
        }
    }


    public void executeLoan(Scanner scanner) throws SQLException {
        System.out.print("입력 도서명: ");
        String bookTitle = scanner.nextLine();
        int stepNum;
        String targetBookTitle = null;
        //라이크로 유사 도서명 리스트 출력 -> bookService에서 제공해야겠지.
        List<BookGrouped> likebooks = bookService.getBookGroupedSearchTitle(bookTitle);
        if(likebooks.isEmpty()){
            System.out.println("유효한 입력이 아닙니다.");
            return;
        }
        else{
            System.out.println(" 다음 중 대출하고자 하는 도서명의 번호를 선택해주세요. ");
            showlikeBookList(likebooks);
            stepNum = scanner.nextInt();
            scanner.nextLine();  // 개행 문자 소비

            targetBookTitle = likebooks.get(stepNum-1).getBookTitle();
        }

        //번호 선택해서 리스트에서 어떤 도서명을 선택할지 확실하게 정한뒤
        //같은 도서명으로 리스트 출력 대출가능 정보까지 함께
        if(targetBookTitle == null){
            System.out.println("유효한 입력이 아닙니다.");
            return;
        }
        else{
            stepNum=0;
            List<Book> books = bookService.getBooksSearchTitle(targetBookTitle);
            if(books.isEmpty()){
                System.out.println("유효한 입력이 아닙니다.");
                return;
            }
            else{
                System.out.println("다음 중 대출하고자 하는 도서의 번호를 골라주세요. ");
                showRealBookList(books);
                stepNum = scanner.nextInt();
                scanner.nextLine();  // 개행 문자 소비
            }
        }

        //어떤 도서를 고를지 번호 선택해서 확실하게 정한다. -> 
        //대출 실행 ㄱㄱ

    }
    private void showlikeBookList(List<BookGrouped> bg){
        if(bg.isEmpty())
        {
            System.out.println("\n해당 책은 없습니다.");
        }
        else{
            int idx = 1;
            System.out.printf("\n%-5s %-40s  %-10s  %-15s  %-10s  %s\n", "Num","Title", "Author", "Category", "Publisher", "Count");
            //책정보가지고 출력하는 함수들 호출
            for(BookGrouped b: bg){
                System.out.printf("%-5d %-40s  %-10s  %-15s  %-10s  %3d\n",
                        idx++,
                        b.getBookTitle(),
                        formatString(b.getAuthorName(), 10),
                        formatString(b.getCategoryName(), 15),
                        formatString(b.getPublisherName(), 10),
                        b.getCnt()
                );
            }
        }
    }
    private String formatString(String str, int maxLength) {
        return str.length() > maxLength ? str.substring(0, maxLength - 3) + "..." : str;
    }
    private void showRealBookList(List<Book> books) throws SQLException {
        if(books.isEmpty())
        {
            System.out.println("\n해당 책은 없습니다.");
        }
        else{
            int idx = 1;
            System.out.printf("\n%-5s %-40s  %-10s  %-15s  %-10s  %s\n", "Num","Title", "Author", "Category", "Publisher", "Possible");
            //책정보가지고 출력하는 함수들 호출
            for(Book b: books){
                System.out.printf("%-5d %-40s  %-10s  %-15s  %-10s  %s\n",
                        idx++,
                        b.getTitle(),
                        formatString(authorService.getAuthorNameByNum(b.getAuthorNo()), 10),
                        formatString(categoryService.getCategoryNameByNum(b.getCategoryNo()), 15),
                        formatString(publisherService.getPublisherNameByNum(b.getPublisherNo()), 10),
                        b.getStatus()
                );
            }
        }
    }

}
