package engine;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank
    @Pattern(regexp = "\\w+@\\w+\\.\\w+")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(min = 5)
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }
}
