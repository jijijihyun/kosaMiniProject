package com.kosa.libaraySystem.model;

import java.util.Scanner;

public class User {
    private int userNo;
    private String userId;
    private String password;
    private String username;
    private String email;
    private int roleNo;

    public User(int userNo, String userId, String password, String username, String email, int roleNo) {
        this.userNo = userNo;
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.email = email;
        this.roleNo = roleNo;
    }

    public User(String userId, String password, String username, String email, int roleNo) {
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.email = email;
        this.roleNo = roleNo;
    }

    public User() {

    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleNo() {
        return roleNo;
    }

    public void setRoleNo(int roleNo) {
        this.roleNo = roleNo;
    }

    //숫자 입력 안전 장치
    public int safeGetIntInput(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String input = scanner.nextLine();
                int number = Integer.parseInt(input);
                return number;  // 입력 받은 숫자 반환
            } catch (NumberFormatException e) {
                System.out.println("제대로된 숫자를 입력해주세요");
            }
        }
    }
}
