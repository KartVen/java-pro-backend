package pl.kartven.javaprobackend.range;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "types")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Value value;
    public enum Value {
        LECTURE,
        LABORATORY,
        TEST,
        LINKS,
    }
}
