package pl.kartven.javaprobackend.token;

import lombok.*;
import pl.kartven.javaprobackend.user.User;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(unique = true)
    public String value;
    public boolean revoked;
    public boolean expired;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

    public Token(String value, User user) {
        this.value = value;
        this.user = user;
        this.revoked = this.expired = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return revoked == token.revoked && expired == token.expired && Objects.equals(id, token.id) && Objects.equals(value, token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, revoked, expired);
    }
}
