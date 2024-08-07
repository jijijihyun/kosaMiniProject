package com.kosa.libaraySystem.controller;

import com.kosa.libaraySystem.model.Category;
import com.kosa.libaraySystem.service.CategoryService;
import com.kosa.libaraySystem.service.impl.CategoryServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CategoryController {
    private Scanner scanner = new Scanner(System.in);
    private CategoryService categoryService = new CategoryServiceImpl();

    public void manageCategories() {
        while(true) {
            try {
                System.out.println("\n------     카테고리 관리     ------");
                System.out.println("[1] 카테고리 검색");
                System.out.println("[2] 카테고리 추가");
                System.out.println("[3] 카테고리 수정");
                System.out.println("[4] 카테고리 삭제");
                System.out.println("[5] 뒤로가기");
                System.out.print(">> ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch(choice) {
                    case 1 :
                        searchCategories();
                        break;
                    case 2 :
                        addCategory();
                        break;
                    case 3 :
                        updateCategory();
                        break;
                    case 4 :
                        deleteCategory();
                        break;
                    case 5 :
                        System.out.println("📌이전 메뉴로 이동합니다.");
                        return;
                    default :
                        System.out.println("🚫잘못된 선택입니다.");
                }
            } catch(SQLException e) {
                System.out.println("📌오류가 발생했습니다.");
                System.out.println("\t🚫" + e.getMessage());
            }

        }
    }

    private void searchCategories() throws SQLException {
        List<Category> categories = categoryService.getCategoryHierarchy();

        if(categories.isEmpty()) {
            System.out.println("\n🚫카테고리가 없습니다.");
        } else {
            printTableHeader();
            for (Category category : categories) {
                printCategoryDetails(category);
            }
            printTableFooter();
        }
    }

    private void printTableHeader() {
        System.out.printf("\n+---------+-------------------------------+-----------------+---------------------------------------------+%n");
        System.out.printf("| 번호     | 이름                           | 상위 카테고리       | 경로                                        |%n");
        System.out.printf("+---------+-------------------------------+-----------------+---------------------------------------------+%n");
    }

    private void printCategoryDetails(Category category) {
        String parentName = category.getParentNo() != null ? String.valueOf(category.getParentNo()) : "-";
        System.out.printf("| %-7d | %-28s | %-15s | %-42s |%n",
                category.getCategoryNo(),
                category.getName(),
                parentName,
                category.getPath()
        );
    }

    private void printTableFooter() {
        System.out.printf("+---------+-------------------------------+-----------------+---------------------------------------------+%n");
    }

    private void addCategory() throws SQLException {
        searchCategories();

        while(true) {
            try {
                System.out.println("\n------     카테고리 추가     ------");
                System.out.println("🔎      추가할 카테고리 정보      🔎");

                System.out.println("상위 카테고리(없으면 Enter)");
                System.out.print(">> ");
                String parentName = scanner.nextLine();
                System.out.println("하위 카테고리");
                System.out.print(">> ");
                String name = scanner.nextLine();

                categoryService.addCategory(parentName.isEmpty() ? null : parentName, name);
                System.out.println("\n📌카테고리가 추가되었습니다.");
                break;
            } catch(IllegalArgumentException | SQLException e) {
                System.out.println("📌카테고리 추가 중 오류가 발생했습니다.");
                System.out.println("\t🚫" + e.getMessage());
            }

        }
    }

    private void updateCategory() throws SQLException {
        searchCategories();

        while(true) {
            try {
                System.out.println("\n------     카테고리 수정     ------");
                System.out.println("🔎      수정할 카테고리 정보      🔎");

                System.out.println("수정할 카테고리 번호");
                System.out.print(">> ");
                String input = scanner.nextLine();

                Integer categoryNo = null;
                try {
                    categoryNo = Integer.parseInt(input);
                } catch(NumberFormatException e) {
                    System.out.println("\n🚫유효하지 않는 입력입니다. 숫자로 다시 입력하세요.");
                    continue;
                }

                Category currentCategory = categoryService.getCategoryByNo(categoryNo);

                if(currentCategory == null) {
                    System.out.println("\n🚫해당 카테고리는 존재하지 않습니다.");
                    continue;
                } else {
                    System.out.println("\n📌현재 상위 카테고리 : " + (currentCategory.getCategoryNo() != 0 ? categoryService.getCategoryNameByNo(currentCategory.getParentNo()) : "없음"));
                }

                System.out.println("새로운 상위 카테고리명(유지 시 Enter)");
                System.out.print(">> ");
                String newParentName = scanner.nextLine();

                System.out.println("새로운 카테고리명(현재 카테고리명 : " + currentCategory.getName() + ")");
                System.out.print(">> ");
                String newName = scanner.nextLine();

                categoryService.updateCategory(categoryNo, newParentName.isEmpty() ? null : newParentName, newName);
                System.out.println("\n📌카테고리 수정이 완료되었습니다.");
                break;
            } catch(NullPointerException | IllegalArgumentException | SQLException e) {
                System.out.println("📌카테고리 수정 중 오류가 발생했습니다.");
                System.out.println("\t🚫" + e.getMessage());
            }
        }
    }

    private void deleteCategory() throws SQLException {
        searchCategories();

        while(true) {
            try {
                System.out.println("\n------     카테고리 삭제     ------");
                System.out.println("🔎      삭제할 카테고리 정보      🔎");

                System.out.println("삭제할 카테고리 번호");
                System.out.print(">> ");
                String input = scanner.nextLine();

                Integer categoryNo = null;
                try {
                    categoryNo = Integer.parseInt(input);
                } catch(NumberFormatException e) {
                    System.out.println("\n🚫유효하지 않는 입력입니다. 숫자로 다시 입력하세요.");
                    continue;
                }

                if(categoryNo == null || !categoryService.isCategoryValid(categoryNo)) {
                    System.out.println("\n🚫해당 카테고리는 존재하지 않습니다.");
                    continue;
                }

                categoryService.deleteCategory(categoryNo);
                System.out.println("\n📌카테고리 삭제가 완료되었습니다.");
                break;
            } catch(IllegalArgumentException | SQLException e) {
                System.out.println("📌카테고리 삭제 중 오류가 발생했습니다.");
                System.out.println("\t🚫" + e.getMessage());
            }
        }
    }
}
