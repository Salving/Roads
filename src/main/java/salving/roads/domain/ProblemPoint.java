package salving.roads.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Points")
public class ProblemPoint implements Serializable {

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false, name = "id")
    private long id;

    @Column(nullable = false, name = "latitude")
    private double latitude;

    @Column(nullable = false, name = "longitude")
    private double longitude;

    @Column(nullable = false, name = "rating", columnDefinition = "long default '1'")
    private int rating ;

    @OneToMany(mappedBy = "point")
    private List<Note> notes;

    protected ProblemPoint() {
    }

    public ProblemPoint(double latitude, double longitude) {
        this(latitude, longitude, 0);
    }

    private ProblemPoint(double latitude, double longitude, int rating) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
