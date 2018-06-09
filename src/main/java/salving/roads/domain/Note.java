package salving.roads.domain;

import javax.persistence.*;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false, name = "id")
    private long id;

    @Column(nullable = false, name = "text")
    private String text;

    @ManyToOne(optional = false, targetEntity = ProblemPoint.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private ProblemPoint point;

    @ManyToOne(optional = false, targetEntity = User.class)
    @JoinColumn(name = "author_id")
    private User author;

    protected Note() {
    }

    public Note(String text, ProblemPoint point_id, User author) {
        this.text = text;
        this.point = point_id;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ProblemPoint getPoint() {
        return point;
    }

    public void setPoint(ProblemPoint point) {
        this.point = point;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
