package recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    @Type(type = "text")  // otherwise the column is varchar(255) which might be too small
    private String description;

    @NotNull
    @NotBlank
    private String category;

    @NotNull
    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    @OrderColumn
    private String[] ingredients;

    @NotNull
    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "directions", joinColumns = @JoinColumn(name = "recipe_id"))
    @OrderColumn
    private String[] directions;

    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore  // don't return the user
    private User user;
}
