package mju.nnews3.domain.news.core;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mju.nnews3.domain.view.core.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "news")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(name = "publisher", length = 100)
    private String publisher;

    @Column(name = "img_url", length = 500)
    private String imgUrl;

    @Column(name = "link", length = 500)
    private String link;

    @Column(name = "category", length = 45)
    private String category;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "view")
    private Long view;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<View> views = new ArrayList<>();

    public void updateView() {
        if (this.view == null) {
            this.view = 0L;
        } else {
            this.view += 1;
        }
    }
}
