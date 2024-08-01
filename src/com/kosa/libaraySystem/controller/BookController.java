package com.kosa.libaraySystem.controller;

import com.kosa.libaraySystem.model.Author;
import com.kosa.libaraySystem.model.Book;
import com.kosa.libaraySystem.model.BookGrouped;
import com.kosa.libaraySystem.model.User;
import com.kosa.libaraySystem.service.*;
import com.kosa.libaraySystem.service.impl.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookController {
    private BookService bookService = new BookServiceImpl();
    private Scanner scanner = new Scanner(System.in);
    private AuthorService authorService = new AuthorServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();
    private PublisherService publisherService = new PublisherServiceImpl();

    // 도서 관리 메뉴
    public void manageBooks() {
        System.out.println("\n------     도서 관리메뉴     ------");
        System.out.println("[1] 도서 추가");
        System.out.println("[2] 도서 정보 수정");
        System.out.println("[3] 도서 삭제");
        System.out.println("[4] 도서 정보 조회");
        System.out.println("[5] 로그아웃");
        System.out.print(">> ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                // 도서 추가
                addBooks();
                break;
            case 2:
                // 도서 정보 수정
                updateBooks();
                break;
            case 3:
                // 도서 삭제
                deleteBooks();
                break;
            case 4:
                // 도서 정보 조회
                searchBooks();
                break;
            case 5:
                System.out.println("로그아웃 되었습니다.");
                return;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

    private void deleteBooks() {
        // 제목을 입력 받아 찾은 후 있으면  삭제
        //

        System.out.print("삭제할 도서의 제목을 입력하세요: ");
        String title = scanner.nextLine();
        bookService.deleteBookByTitle(title);
        System.out.println("도서가 삭제되었습니다.");
    }

    public void searchBooks() {/*
        System.out.print("조회할 도서의 제목을 입력하세요: ");
        String title = scanner.nextLine();
        Book book = bookService.searchBookByTitle(title);
        if (book != null) {
            System.out.println("도서 제목: " + book.getBookTitle());
            System.out.println("작가 번호: " + book.getBookAuthorNo());
            System.out.println("출판사 번호: " + book.getBookPublisherNo());
            System.out.println("카테고리 번호: " + book.getBookCategoryNo());
        } else {
            System.out.println("도서를 찾을 수 없습니다.");
        }
        */
    }

    public void updateBooks() {
        System.out.print("수정할 도서의 제목을 입력하세요: ");
        String oldTitle = scanner.nextLine();

        System.out.print("새로운 제목을 입력하세요: ");
        String newTitle = scanner.nextLine();

        System.out.print("새로운 작가를 입력하세요: ");
        String newAuthor = scanner.nextLine();
        int authorNo = bookService.authorSearch(newAuthor);
        if (authorNo == 0) {
            System.out.println("작가를 찾을 수 없습니다.");
            return;
        }
    }
    public void addBooks() {
        System.out.print("제목을 입력하세요 : ");
        String btitle = scanner.nextLine();

        System.out.print("작가를 입력 하세요 : ");
        String bauthor = scanner.nextLine();
            // bauthor 받고 작가 디비에서 검색 후 작가 번호를 리턴
        int authorNo1 = bookService.authorSearch(bauthor);
        if (authorNo1 == 0) {
            System.out.println("작가를 찾을 수 없습니다.");
            return;
        }

        System.out.print("출판사를 입력하세요 : ");
        String bpublisher = scanner.nextLine();
            // bpublisher 받고 출판사 디비에서 검색 후 출판사 번호를 리턴
        int publisherNo = bookService.publisherSearch(bpublisher);
        if (publisherNo == 0) {
            System.out.println("출판사를 찾을 수 없습니다.");
            return;
        }

        System.out.print("카테고리를 입력 하세요 : ");
        String bcategory = scanner.nextLine();
        // bcategory 받고 카테고리 디비에서 검색 후 카테고리 번호를 리턴
        int categoryNo = bookService.categorySearch(bcategory);
        if (categoryNo == 0) {
            System.out.println("카테고리를 찾을 수 없습니다.");
            return;
        }

            // db "INSERT INTO Books (title, authorNo, publisherNo, categoryNo) VALUES (?, ?, ?, ?);"
        bookService.addBook(btitle, authorNo1, publisherNo, categoryNo);
        System.out.println("도서가 추가되었습니다.");
    }

    /*
    ==================이하 유저 기능에 필요한 내용들=====================
     */
    public void userSearchBook(User user) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n1. 책명으로 검색\n2. 저자로 검색\n3. 출판사로 검색\n4. 카테고리로 검색\n5.검색종료");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n책명을 입력하세요");
                    String bookTitle = scanner.nextLine();

                    //책 리스트 받을 공간 생성 후 받아
                    List<BookGrouped> bookGroupeds = bookService.getBookGroupedSearchTitle(bookTitle);

                    if(bookGroupeds.isEmpty())
                    {
                        System.out.println("\n해당 책은 없습니다.");
                    }
                    else{
                        System.out.printf("\n%20s %20s %10s %10s %3s권","책명", "작가명", "카테고리명", "출판사명", "수량");
                        //책정보가지고 출력하는 함수들 호출
                        for(BookGrouped b: bookGroupeds){
                            System.out.printf("\n%20s %20s %10s %10s %3s권",
                                    b.getBookTitle(),
                                    b.getAuthorName(),
                                    b.getCategoryName(),
                                    b.getPublisherName(),
                                    b.getCnt()
                            );
                        }
                    }
                    break;

                case 5:
                    System.out.println("\n검색을 종료합니다.");
                    isRunning= false;
                    break;
                default:
                    System.out.println("\n유효하지 않은 입력입니다.");
                    break;
            }
        }
    }

}