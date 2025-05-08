package mju.nnews3.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mju.nnews3.execption.DuplicateKeywordException;
import mju.nnews3.execption.NotFoundKeywordException;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;

    @Column
    private String email;

    @Column(columnDefinition = "TEXT")  // JSON 대신 텍스트로 변경
    private String keyword;

    @Column
    private boolean alarm;

    @Column
    private boolean location;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private Integer score;

    public void updateKeywordList(String previousKeyword, String newKeyword) {
        List<String> updated = (keyword != null && !keyword.isEmpty()) ?
                new ArrayList<>(List.of(keyword.split(","))) : new ArrayList<>();

        if (previousKeyword != null) {
            updated.remove(previousKeyword);
        }

        if (newKeyword != null) {
            if (updated.contains(newKeyword)) {
                throw new DuplicateKeywordException();
            }
            updated.add(newKeyword);
        }

        this.keyword = updated.stream().collect(Collectors.joining(","));
    }

    public void changeLocationConsent(boolean location) {
        this.location = location;
    }

    public void changeAlarmConsent(boolean alarm) {
        this.alarm = alarm;
    }

    public int getSafeScore() {
        return score != null ? score : 0;
    }

    public void updateScore(Integer score) {
        this.score += score;
    }

    public void deleteKeyword(String toDelete) {
        if (toDelete == null || toDelete.isBlank()) {
            throw new NotFoundKeywordException();
        }

        if (this.keyword == null || this.keyword.isBlank()) {
            throw new NotFoundKeywordException();
        }

        List<String> keywordList = new ArrayList<>(List.of(this.keyword.split(",")));

        if (!keywordList.contains(toDelete)) {
            throw new NotFoundKeywordException();
        }

        keywordList.removeIf(k -> k.equals(toDelete));
        this.keyword = String.join(",", keywordList);
    }

    public void updateLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
