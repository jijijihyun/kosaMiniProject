package com.kosa.libaraySystem.model;


import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    private int authorNo;
    private String authorName;

    public int getAuthorNo() {
        return authorNo;
    }

    public void setAuthorNo(int authorNo) {
        this.authorNo = authorNo;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
