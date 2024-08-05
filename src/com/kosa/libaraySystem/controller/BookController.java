package com.kosa.libaraySystem.controller;

import com.kosa.libaraySystem.model.*;
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
        // 제목을 입력 받아서 책 재목이 같은 책이 있으면  정보를 전부 출력 후 트루 반환 같은 책이 없으면 펄스 반환. 반환값은 b에 저장
        boolean b = searchBooks();
        // b가 진실이면 계속 진행하고 b가 거짓이면  현재 메소드를 바로 종료
        if (b == false) {
            return;
        }

        // 책 번호 입력 하면 해당 북넘버 도서 삭제
        System.out.print("삭제할 도서의 번호을 입력하세요: ");
        int bookNo = scanner.nextInt();
        // 20240804 추가
        //  도서 번호가 없는 경우 deleteBooks 종료
        int bno = bookService.bNoSearch(bookNo);
        if (bno == 0) {
            System.out.println("도서 번호를 찾을 수 없습니다.");
            return;
        }
        scanner.nextLine();
        bookService.deleteBookByTitle(bookNo);
        System.out.println("도서가 삭제되었습니다.");
    }

    private boolean searchBooks() {
        System.out.print("조회할 도서의 제목을 입력하세요: ");
        String title = scanner.nextLine();
        List<Book> book = bookService.searchBookByTitle(title);
        // 리턴 받은 book.getBookAuthorNo() 번호로 작가 이름 리턴
        if (!book.isEmpty()) {
            System.out.print("도서 번호");
            System.out.print("\t도서 제목 ");
            System.out.print("\t작가  ");
            System.out.print("\t출판사 ");
            System.out.print("\t카테고리 ");
            System.out.println();

            for (int i = 0; book.size() > i; i++) {

                int baNo = book.get(i).getBookAuthorNo();
                int bpNo = book.get(i).getBookPublisherNo();
                int bcno = book.get(i).getBookCategoryNo();


                String a = bookService.reverseAuthorSearch(baNo);
                String p = bookService.reversePublisherSearch(bpNo);
                String c = bookService.reverseCategorySearch(bcno);

                System.out.print("" + book.get(i).getBookNo());
                System.out.print("\t" + book.get(i).getBookTitle());
                System.out.print("\t" + bookService.reverseAuthorSearch(baNo));
                System.out.print("\t" + p);
                System.out.print("\t" + bookService.reverseCategorySearch(bcno));
                System.out.println();
            }

        } else {
            System.out.println("도서를 찾을 수 없습니다.");
            return false;
        }
        return true;

    }

    private void updateBooks() {


        boolean b = searchBooks();
        // searchBooks(); 안될 경우 바로 종료
        if (b == false) {
            return;
        }

        //20240802 변경점
        // 변경할 도서의 번호를 입력 받으면 해당  번호의 도서를 새로 입력 받은 제목, 작가,출반사, 카태고리로 변경한다
        //만약 입력한 작가 ,출판사,카태고리가 없는 정보면 없다고 알리고 종료
        System.out.print("수정할 도서의 번호를: ");
        int bookno = scanner.nextInt();

        // 20240804 추가
        //  도서 번호가 없는 경우 updateBooks 종료
        int bno = bookService.bNoSearch(bookno);
        if (bno == 0) {
            System.out.println("도서 번호를 찾을 수 없습니다.");
            return;
        }

        scanner.nextLine();
        System.out.print("새로운 제목을 입력하세요: ");
        String newTitle = scanner.nextLine();

        //
        System.out.print("새로운 작가를 입력 하세요 : ");
        String bauthor = scanner.nextLine();
        // bauthor 받고 작가 디비에서 검색 후 작가 번호를 리턴
        int authorNo1 = bookService.authorSearch(bauthor);
        if (authorNo1 == 0) {
            System.out.println("작가를 찾을 수 없습니다.");
            return;
        }

        System.out.print("새로운 출판사를 입력하세요 : ");
        String bpublisher = scanner.nextLine();
        // bpublisher 받고 출판사 디비에서 검색 후 출판사 번호를 리턴
        int publisherNo = bookService.publisherSearch(bpublisher);
        if (publisherNo == 0) {
            System.out.println("출판사를 찾을 수 없습니다.");
            return;
        }

        System.out.print("새로운 카테고리를 입력 하세요 : ");
        String newbcategory = scanner.nextLine();
        // bcategory 받고 카테고리 디비에서 검색 후 카테고리 번호를 리턴
        int categoryNo = bookService.categorySearch(newbcategory);
        if (categoryNo == 0) {
            System.out.println("카테고리를 찾을 수 없습니다.");
            return;
        }

        bookService.updateBook(newTitle, authorNo1, publisherNo, categoryNo, bookno);
        System.out.println("도서 제목이 변경되었습니다.");

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
                    showBookListUser(bookGroupeds);
                    break;
                case 2:
                    System.out.println("\n저자를 입력하세요");
                    String authorName = scanner.nextLine();

                    //책 리스트 받을 공간 생성 후 받아
                    List<BookGrouped> bookGroupedByAuthor = bookService.getBookGroupedSearchByAuthorName(authorName);
                    showBookListUser(bookGroupedByAuthor);
                    break;
                case 3:
                    System.out.println("\n출판사를 입력하세요");
                    String pubName = scanner.nextLine();

                    //책 리스트 받을 공간 생성 후 받아
                    List<BookGrouped> bookGroupedByPub = bookService.getBookGroupedSearchByPublisherName(pubName);
                    showBookListUser(bookGroupedByPub);

                    break;

                case 4:
                    System.out.println("\n**상위 카테고리면 하위 카테고리명을 출력하고");
                    System.out.println("\n**하위 카테고리면 책 리스트를 출력합니다.");

                    searchBooksByCategory();
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
    //문자열 길이 포맷팅
    private String formatString(String str, int maxLength) {
        return str.length() > maxLength ? str.substring(0, maxLength - 3) + "..." : str;
    }

    //단순 반복코드 도서 출력부로직만 따로
    private void showBookListUser(List<BookGrouped> bg){
        if(bg.isEmpty())
        {
            System.out.println("\n해당 책은 없습니다.");
        }
        else{
            System.out.printf("\n%-40s  %-10s  %-15s  %-10s  %s\n","Title", "Author", "Category", "Publisher", "Count");
            //책정보가지고 출력하는 함수들 호출
            for(BookGrouped b: bg){
                System.out.printf("%-40s  %-10s  %-15s  %-10s  %3d\n",
                        b.getBookTitle(),
                        formatString(b.getAuthorName(), 10),
                        formatString(b.getCategoryName(), 15),
                        formatString(b.getPublisherName(), 10),
                        b.getCnt()
                );
            }
        }
    }

    //일단 카테고리명
    public void searchBooksByCategory() {
        System.out.print("조회할 도서의 카테고리명을 입력하세요: ");
        String categoryName = scanner.nextLine();

        Category category = categoryService.getCategoryByName(categoryName);
        if (category != null) {
            displayListByCategoryNum(category.getCategoryNo());
        } else {
            System.out.println("카테고리를 찾을 수 없습니다.");
        }
    }

    //서브 카테고리 때문에
    private void displayListByCategoryNum(int categoryNo) {
        List<Category> subCategories = categoryService.getSubCategoriesByParentNum(categoryNo);

        if (!subCategories.isEmpty()) {
            System.out.println("하위 카테고리의 번호를 선택하세요:");
            //하위 카테고리 리스트 출력
            for (int i = 0; i < subCategories.size(); i++) {
                System.out.println((i + 1) + ". " + subCategories.get(i).getName());
            }
            int choice = scanner.nextInt();
            scanner.nextLine();  // 개행 문자 소비


            if (choice > 0 && choice <= subCategories.size()) {
                displayListByCategoryNum(subCategories.get(choice - 1).getCategoryNo());
            } else {
                System.out.println("잘못된 선택입니다.");
            }
        } else {
            List<BookGrouped> books = bookService.getBookGroupedSearchByCategoryNum(categoryNo);
            showBookListUser(books);
        }
    }
}