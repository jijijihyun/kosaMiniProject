package com.kosa.libaraySystem.controller;

import com.kosa.libaraySystem.model.*;
import com.kosa.libaraySystem.service.*;
import com.kosa.libaraySystem.service.impl.*;
import com.kosa.libaraySystem.util.TupleKNY;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookController {
    private Scanner scanner = new Scanner(System.in);
    private BookService bookService = new BookServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();

    // 도서 관리 메뉴
    public void manageBooks() throws SQLException {
        while (true) {
            System.out.println("\n------     도서 관리     ------");
            System.out.println("[1] 도서 조회");
            System.out.println("[2] 도서 추가");
            System.out.println("[3] 도서 수정");
            System.out.println("[4] 도서 삭제");
            System.out.println("[5] 뒤로가기");
            System.out.print(">> ");

            int choice = setInteger();

            switch (choice) {
                case 1:
                    // 도서 정보 조회
                    searchBooks();
                    break;
                case 2:
                    // 도서 추가
                    addBooks();
                    break;
                case 3:
                    // 도서 정보 수정
                    updateBooks();
                    break;
                case 4:
                    // 도서 삭제
                    deleteBooks();
                    break;
                case 5:
                    System.out.println("📌이전 메뉴로 이동합니다.");
                    return;
                default:
                    System.out.println("🚫잘못된 선택입니다.");
            }
        }
    }

    private void deleteBooks() {
        if (searchBooks() == false) {
            return;
        }

        // 책 번호 입력 하면 해당 북넘버 도서 삭제
        System.out.println("\n------     도서 삭제     ------");
        System.out.println("🔎      삭제할 도서 번호      🔎");
        System.out.print(">> ");
        int bookNo = setInteger();
        // 20240804 추가
        //  도서 번호가 없는 경우 deleteBooks 종료
        int bno = bookService.bNoSearch(bookNo);
        if (bno == 0) {
            System.out.println("🚫도서 번호를 찾을 수 없습니다.");
            return;
        }

        bookService.deleteBookByTitle(bookNo);
        System.out.println("\n📌도서가 삭제되었습니다.");
    }

    private boolean searchBooks() {
        System.out.println("\n------     도서 검색     ------");
        System.out.println("🔎      검색할 도서 제목      🔎");
        System.out.print(">> ");
        String title = setStr();

        List<Book> books = bookService.searchBookByTitle(title);

        // 리턴 받은 book.getBookAuthorNo() 번호로 작가 이름 리턴
        if(books.isEmpty()) {
            System.out.println("🚫검색 결과가 없습니다.");
            return false;
        } else {
            printTableHeader();
            for(Book book : books) {
                int baNo = book.getAuthorNo();
                int bpNo = book.getPublisherNo();
                int bcno = book.getCategoryNo();

                int bno = book.getBookNo();
                String btitle = book.getTitle();
                String a = bookService.reverseAuthorSearch(baNo);
                String p = bookService.reversePublisherSearch(bpNo);
                String c = bookService.reverseCategorySearch(bcno);
                String bstatus = book.getStatus();


                printBookDetails(bno, btitle, a, p, c, bstatus);
            }
            printTableFooter();
        }
        return true;
    }

    private void printTableHeader() {
        System.out.printf("\n+---------+-------------------------------+--------+----------+-----------+---------+%n");
        System.out.printf("| 도서 번호 | 제목                        | 저자     | 출판사     | 카테고리     | 대출여부  |%n");
        System.out.printf("+---------+-------------------------------+--------+----------+-----------+---------+%n");
    }

    private void printBookDetails(int bno, String btitle, String a, String p, String c,String bstatus) {
        System.out.printf("| %-7d | %-25s | %-7s | %-7s | %-7s | %-7s |%n",
                bno, btitle, a, p, c, bstatus);
    }

    private void printTableFooter() {
        System.out.printf("+---------+-------------------------------+--------+----------+-----------+---------+%n");
    }

    private void updateBooks() {
        // searchBooks(); 안될 경우 바로 종료
        if (searchBooks() == false) {
            return;
        }

        //20240802 변경점
        // 변경할 도서의 번호를 입력 받으면 해당  번호의 도서를 새로 입력 받은 제목, 작가,출반사, 카태고리로 변경한다
        //만약 입력한 작가 ,출판사,카태고리가 없는 정보면 없다고 알리고 종료
        System.out.println("\n------     도서 수정     ------");
        System.out.println("🔎      수정할 도서 번호      🔎");
        System.out.print(">> ");
        int bookno = setInteger();

        // 20240804 추가
        //  도서 번호가 없는 경우 updateBooks 종료
        int bno = bookService.bNoSearch(bookno);
        if (bno == 0) {
            System.out.println("🚫도서 번호를 찾을 수 없습니다.");
            return;
        }

        System.out.println("새로운 제목");
        System.out.print(">> ");
        String newTitle = setStr();
        if(newTitle == null){
            return;
        }

        System.out.println("새로운 저자");
        System.out.print(">> ");
        String bauthor = setStr();
        // bauthor 받고 작가 디비에서 검색 후 작가 번호를 리턴
        int authorNo1 = bookService.authorSearch(bauthor);
        if (authorNo1 == 0) {
            System.out.println("🚫작가를 찾을 수 없습니다.");
            return;
        }

        System.out.println("새로운 출판사");
        System.out.print(">> ");
        String bpublisher = setStr();
        // bpublisher 받고 출판사 디비에서 검색 후 출판사 번호를 리턴
        int publisherNo = bookService.publisherSearch(bpublisher);
        if (publisherNo == 0) {
            System.out.println("🚫출판사를 찾을 수 없습니다.");
            return;
        }

        System.out.println("새로운 카테고리");
        System.out.print(">> ");
        String newbcategory = setStr();
        // bcategory 받고 카테고리 디비에서 검색 후 카테고리 번호를 리턴
        int categoryNo = bookService.categorySearch(newbcategory);
        if (categoryNo == 0) {
            System.out.println("🚫카테고리를 찾을 수 없습니다.");
            return;
        }

        bookService.updateBook(newTitle, authorNo1, publisherNo, categoryNo, bookno);
        System.out.println("\n📌도서 정보가 수정되었습니다.");

    }
    public void addBooks() {
        System.out.println("\n------     도서 추가     ------");
        System.out.println("🔎      추가할 도서 정보      🔎");

        System.out.println("도서 제목");
        System.out.print(">> ");
        String btitle = setStr();

        if(btitle == null){
            return;
        }

        System.out.println("저자명");
        System.out.print(">> ");
        String bauthor = setStr();
        // bauthor 받고 작가 디비에서 검색 후 작가 번호를 리턴
        int authorNo1 = bookService.authorSearch(bauthor);
        if (authorNo1 == 0) {
            System.out.println("🚫작가를 찾을 수 없습니다.");
            return;
        }

        System.out.println("출판사명");
        System.out.print(">> ");
        String bpublisher = setStr();
            // bpublisher 받고 출판사 디비에서 검색 후 출판사 번호를 리턴
        int publisherNo = bookService.publisherSearch(bpublisher);
        if (publisherNo == 0) {
            System.out.println("🚫출판사를 찾을 수 없습니다.");
            return;
        }

        System.out.println("카테고리명");
        System.out.print(">> ");
        String bcategory = setStr();
        // bcategory 받고 카테고리 디비에서 검색 후 카테고리 번호를 리턴
        int categoryNo = bookService.categorySearch(bcategory);
        if (categoryNo == 0) {
            System.out.println("🚫카테고리를 찾을 수 없습니다.");
            return;
        }

        System.out.println("도서 권 수");
        System.out.print(">> ");
        int books = setInteger();

        for(int i=0; i<books ; i++){
            bookService.addBook(btitle, authorNo1, publisherNo, categoryNo);
        }

        System.out.println("\n📌도서가 추가되었습니다.");
    }

    private String setStr(){
        try {
            String aname = scanner.nextLine();
            if(aname.isEmpty()){
                System.out.println("\n🚫올바른 값을 입력 바랍니다.");
                return null;
            }
            return aname;
        }catch (Exception e){
            System.out.println("\n🚫올바른 값을 입력 바랍니다.");
        }
        return null;
    }

    // 정수를 입력해 달라고 루프걸기
    private int setInteger(){
        try {
            String ano = scanner.nextLine();
            if(ano.isEmpty()){
                System.out.println("\n🚫올바른 값을 입력 바랍니다.");
                return 0;
            }
            Integer a = Integer.parseInt(ano);
            return a;
        }catch (Exception e){
            System.out.println("\n🚫올바른 값을 입력 바랍니다.");
            return 0;
        }
    }

    /*

     */
    /*
    ==================이하 유저 기능에 필요한 내용들=====================
     */
    public void userSearchBook(User user) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {

            System.out.println("\n------     도서 검색     ------");
            System.out.println("🔎      검색할 도서 제목      🔎");
            System.out.println("[1] 도서명으로 검색");
            System.out.println("[2] 저자로 검색");
            System.out.println("[3] 출판사로 검색");
            System.out.println("[4] 검색종료");
            System.out.print(">> ");
            int choice = safelyGetIntInput();
            try{
                switch (choice) {
                    case 1:
                        System.out.println("\n도서명 검색");
                        System.out.print(">> ");
                        String bookTitle = scanner.nextLine();

                        //책 리스트 받을 공간 생성 후 받아
                        List<BookGrouped> bookGroupeds = bookService.getBookGroupedSearchTitle(bookTitle);
                        showBookListUser(bookGroupeds);
                        break;
                    case 2:
                        System.out.println("\n저자 검색");
                        System.out.print(">> ");
                        String authorName = scanner.nextLine();

                        //책 리스트 받을 공간 생성 후 받아
                        List<BookGrouped> bookGroupedByAuthor = bookService.getBookGroupedSearchByAuthorName(authorName);
                        showBookListUser(bookGroupedByAuthor);
                        break;
                    case 3:
                        System.out.println("\n출판사 검색");
                        System.out.print(">> ");
                        String pubName = scanner.nextLine();

                        //책 리스트 받을 공간 생성 후 받아
                        List<BookGrouped> bookGroupedByPub = bookService.getBookGroupedSearchByPublisherName(pubName);
                        showBookListUser(bookGroupedByPub);
                        break;
                    case 4:
                        System.out.println("\n📌검색을 종료합니다.");
                        isRunning= false;
                        break;
                    default:
                        System.out.println("\n🚫유효하지 않은 입력입니다.");
                        break;
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    //문자열 길이 포맷팅
    private String formatString(String str, int maxLength) {
        return str.length() > maxLength ? str.substring(0, maxLength - 3) + "..." : str;
    }

    //단순 반복코드 도서 출력부로직만 따로
    private void showBookListUser(List<BookGrouped> bg) throws SQLException {
        if(bg.isEmpty())
        {
            System.out.println("\n🚫해당 책은 없습니다.");
        } else{
            System.out.printf("\n%-40s  %-10s  %-15s %-15s %-10s  %s\n","책명", "작가", "대분류", "소분류", "출판사", "권수");

            //책정보가지고 출력하는 함수들 호출
            for(BookGrouped b: bg){
                TupleKNY<String,String> categoriesName =
                categoryService.getHierarchyCategory(categoryService.getCategoryByName(b.getCategoryName()));
                String bigCateName = categoriesName.getKey();
                String smallCateName = categoriesName.getValue();
                System.out.printf("%-40s  %-10s  %-15s %-15s %-10s  %3d\n",
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

    //일단 카테고리명
    public void searchBooksByCategory() throws SQLException {
        System.out.print(" ✍\uFE0F 조회할 도서의 카테고리명을 입력하세요: ");

        String categoryName = scanner.nextLine();

        Category category = categoryService.getCategoryByName(categoryName);
        if (category != null) {
            displayListByCategoryNum(category.getCategoryNo());
        } else {
            System.out.println("🚫카테고리를 찾을 수 없습니다.");
        }
    }

    //서브 카테고리 때문에
    private void displayListByCategoryNum(int categoryNo) {
        List<Category> subCategories = categoryService.getSubCategoriesByParentNum(categoryNo);

        if (!subCategories.isEmpty()) {
            System.out.println(" ✍\uFE0F 하위 카테고리의 번호를 선택하세요:");
            //하위 카테고리 리스트 출력
            for (int i = 0; i < subCategories.size(); i++) {
                System.out.println((i + 1) + ". " + subCategories.get(i).getName());
            }
            int choice = safelyGetIntInput();
            scanner.nextLine();  // 개행 문자 소비


            if (choice > 0 && choice <= subCategories.size()) {
                displayListByCategoryNum(subCategories.get(choice - 1).getCategoryNo());
            } else {
                System.out.println("🚫잘못된 선택입니다.");
            }
        } else {
            try{
                List<BookGrouped> books = bookService.getBookGroupedSearchByCategoryNum(categoryNo);
                showBookListUser(books);
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    //숫자 입력 안전 장치
    private int safelyGetIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                int number = Integer.parseInt(input);
                return number;  // 입력 받은 숫자 반환
            } catch (NumberFormatException e) {
                System.out.println("🚫숫자를 입력해주세요.");
            }
        }
    }
}