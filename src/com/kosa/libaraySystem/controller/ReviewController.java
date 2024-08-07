package com.kosa.libaraySystem.controller;

import com.kosa.libaraySystem.model.BookGrouped;
import com.kosa.libaraySystem.model.Review;
import com.kosa.libaraySystem.model.User;
import com.kosa.libaraySystem.service.BookService;
import com.kosa.libaraySystem.service.ReviewService;
import com.kosa.libaraySystem.service.UserService;
import com.kosa.libaraySystem.service.impl.BookServiceImpl;
import com.kosa.libaraySystem.service.impl.ReviewServiceImpl;
import com.kosa.libaraySystem.service.impl.UserServiceImpl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

/*
리뷰 컨트롤러 플로우

리뷰작성  - 돌아가기
리뷰콜


 */
public class ReviewController {
    private ReviewService reviewService = new ReviewServiceImpl();
    private BookService bookService  = new BookServiceImpl();
    private UserService userService = new UserServiceImpl();

    public void reviewControl(User user)throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.printf("\n=== \uD83E\uDEB6 리뷰 ===\n");
            System.out.println("\n[1] 리뷰 작성\n[2] 리뷰 조회\n[3] 돌아 가기");
            int choice = user.safeGetIntInput();


            switch (choice) {
                case 1:
                    System.out.println("\n=== ✍\uFE0F 리뷰 작성 페이지 ===");
                    System.out.println("\n ✍\uFE0F 리뷰를 입력할 도서명을 입력하세요");
                    String bookTitle = scanner.nextLine();
                    int userChoice1=0;
                    List<BookGrouped> books1 = new ArrayList<>();
                    //그룹화된 도서 리스트를 받아온다. 타이틀 like
                    books1 = bookService.getBookGroupedSearchTitle(bookTitle);
                    if (!books1.isEmpty()) {//들어왔으면

                        //도서리스트를 쫙 뿌려주고, 사용자 입력을 받아서 사용자 입력이 숫자라면
                        // 해당 그룹도서에 해당하는 리뷰에 접근 리뷰 리스트를 뿌릴거다.
                        //만약 숫자가 아니라면 try catch 에러 메시지 뿌리고 반복문 진입
                        System.out.printf("\n+-----+----------------------------------------+----------+---------------+---------------+----------+-----+%n");
                        System.out.printf("|%-5s|%-40s|%-10s|%-15s|%-15s|%-10s|%-5s|\n", "번호","책명", "작가", "대분류","소분류", "출판사", "권수");
                        for(int i=1; i<=books1.size(); i++){
                            System.out.printf("|%-5d", i);
                            books1.get(i-1).showBookGroupedInfoUser();
                        }
                        System.out.printf("+-----+----------------------------------------+----------+---------------+---------------+----------+-----+%n");
                    System.out.println("\n ✍\uFE0F 리뷰를 등록하고 싶은 책의 번호를 입력하세요");
                    try {
                        userChoice1 = user.safeGetIntInput();

                        if(userChoice1<1 && userChoice1>books1.size()+1) throw new Exception();
                    }catch(Exception e){
                        System.out.println(" ⚠\uFE0F 올바른 숫자를 입력하지 않으셨네요..");
                        break;
                    }
                    if(books1.get(userChoice1 -1).equals(null)){
                        System.out.println(userChoice1 + " 는 유효한 숫자가 아니라고 하네요");
                        break;
                    }//북 정보가 유효한 북을 골랐다면
                    else{
                        StringBuilder reviewBuilder = new StringBuilder();
                        System.out.println(" ✍\uFE0F 리뷰를 입력해주세요. [등록 방법: 등록 입력]");
                        while (true) {
                            String input = scanner.nextLine();
                            if ("등록".equals(input)) {
                                break; // '등록' 입력 시 반복문 탈출
                            }
                            reviewBuilder.append(input).append("\n"); // 입력된 리뷰를 StringBuilder에 추가
                        }
                        LocalDate today = LocalDate.now();
                        Date sqlDate = Date.valueOf(today);
                        try{
                            reviewService.setReview(user,
                                    bookService.getBookSearchByTitle(books1.get(userChoice1 -1).getBookTitle()).getBookNo(),
                                    reviewBuilder.toString(),sqlDate);//여기에 실제 리뷰내용을 데이터베이스에 접근하는 내용
                        }catch(SQLException e){
                            System.out.println(" ⚠\uFE0F 리뷰가 제대로 등록되지 않았습니다. 다시 확인해보세요.");
                            break;
                        }

                        }
                    } else {//도서 리스트가 없다면
                        System.out.println("\n ⚠\uFE0F 유효한 책이 없습니다.");
                    }
                    break;
                case 2:
                    System.out.println("\n===\uD83D\uDD0D 리뷰 조회 페이지 ===");
                    System.out.println("\n ✍\uFE0F 리뷰를 검색할 도서명을 입력하세요");
                    String bookTitle2 = scanner.nextLine();
                    int userChoice=0;
                    List<BookGrouped> books = new ArrayList<>();
                    List<Review> reviews = new ArrayList<>();
                    //그룹화된 도서 리스트를 받아온다. 타이틀 like
                    books = bookService.getBookGroupedSearchTitle(bookTitle2);
                    if (!books.isEmpty()) {//들어왔으면

                        //도서리스트를 쫙 뿌려주고, 사용자 입력을 받아서 사용자 입력이 숫자라면
                        // 해당 그룹도서에 해당하는 리뷰에 접근 리뷰 리스트를 뿌릴거다.
                        //만약 숫자가 아니라면 try catch 에러 메시지 뿌리고 반복문 진입
                        System.out.printf("\n+-----+----------------------------------------+----------+---------------+---------------+----------+-----+%n");
                        System.out.printf("|%-5s|%-40s|%-10s|%-15s|%-15s|%-10s|%-5s|\n", "번호","책명", "작가", "대분류","소분류", "출판사", "권수");
                        for(int i=1; i<=books.size(); i++){
                            System.out.printf("|%-5d", i);
                            books.get(i-1).showBookGroupedInfoUser();
                        }
                        System.out.printf("+-----+----------------------------------------+----------+---------------+---------------+----------+-----+%n");

                        System.out.println("\n ✍\uFE0F 리뷰를 확인하고 싶은 책의 번호를 입력하세요");
                        try {
                            userChoice = user.safeGetIntInput();
                            if(userChoice<1 && userChoice>books.size()+1) throw new Exception();
                        }catch(Exception e){
                            System.out.println("⚠\uFE0F 올바른 숫자를 입력하지 않으셨네요..");
                            break;
                        }
                        if(books.get(userChoice -1).equals(null)){
                            System.out.println(userChoice + " 는 유효한 숫자가 아니라고 하네요");
                            break;
                        }//북 정보가 유효한 북을 골랐다면
                        else{
                            reviews = reviewService.getReviewsByBookTitle(books.get(userChoice-1).getBookTitle());
                        }
                        try{
                            showReviewConsole(reviews, books.get(userChoice-1).getBookTitle());
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    } else {//도서 리스트가 없다면
                        System.out.println("\n ⚠\uFE0F 유효한 책이 없습니다.");
                    }
                    break;
                case 3:
                    System.out.println("\n \uD83D\uDD19 유저 메인으로 돌아갑니다.");
                    isRunning= false;
                    break;
                default:
                    System.out.println("\n ⚠\uFE0F 유효하지 않은 입력입니다.");
                    break;
            }
        }
    }

    public void writeReview() {
    }
    private void showReviewConsole(List<Review> reviews, String bookTitle)throws Exception{
        System.out.printf("\n \uD83E\uDEB6 ====%s 의 리뷰 리스트 ====\n", bookTitle);

        for(Review r : reviews){
            System.out.printf("\n+---------------+-------------------------+-------------------------+-------------------------+%n");
            System.out.printf("|%-10s:%4s|%-10s:%14s|%-10s:%14s|%-10s:%14s|\n",
                    "리뷰번호", r.getReviewNo(),
                    "유저ID", userService.getUserInstanceDataByUserNo(r.getUserNo()).getUserId(),
                    "유저이름", userService.getUserInstanceDataByUserNo(r.getUserNo()).getUsername(),
                    "등록일자", r.getReviewDate());
            System.out.printf("+---------------+-------------------------+-------------------------+-------------------------+%n");
            System.out.println("+-------------------------------리뷰내용-------------------------------+");
            System.out.println(addLineBreaks(r.getReviewText(), 70));
            System.out.println("+----------------------------------------------------------------------+");
        }
    }
    public String addLineBreaks(String text, int lineLength) {
        StringBuilder result = new StringBuilder();
        int length = text.length();
        int start = 0;

        while (start < length) {
            int end = Math.min(length, start + lineLength);

            // 줄바꿈 위치가 단어 중간이라면 마지막 공백 뒤로 이동
            if (end < length && text.charAt(end) != ' ') {
                int lastSpace = text.lastIndexOf(' ', end);
                if (lastSpace > start) {
                    end = lastSpace;
                }
            }

            //땜빵 추가
            result.append("|");
            // 해당 줄을 결과에 추가
            result.append(text, start, end);
            result.append("|");
            result.append(System.lineSeparator());
            start = end + 1; // 다음 줄의 시작 지점으로 이동 (공백 한 칸 넘어감)
        }

        return result.toString();
    }
}
