package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/recipe")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/{id}")
    public Recipe getRecipeById(@PathVariable final int id) {
        return recipeRepository.findById(id).orElseThrow();
    }

    @PostMapping(path = "/new")
    public Map<String, Integer> addNewRecipe(@RequestBody @Valid Recipe recipe,
                                          final Principal principal) {
        final User user = userRepository.findByEmail(principal.getName());
        recipe.setUser(user);
        recipe = recipeRepository.save(recipe);
        return Map.of("id", recipe.getId());
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipeById(@PathVariable final int id,
                                 final Principal principal) {
        final User user = userRepository.findByEmail(principal.getName());
        final Recipe recipe = recipeRepository.findById(id).orElseThrow();
        if (recipe.getUser() != user) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        recipeRepository.deleteById(id);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeRecipe(@PathVariable final int id,
                             @RequestBody @Valid final Recipe newRecipe,
                             final Principal principal) {
        final User user = userRepository.findByEmail(principal.getName());
        final Recipe recipe = recipeRepository.findById(id).orElseThrow();
        if (recipe.getUser() != user) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        newRecipe.setId(id);  // replaces the old recipe because ids are unique
        newRecipe.setUser(user);
        recipeRepository.save(newRecipe);
    }

    @GetMapping(path = "/search", params = "category")
    public List<Recipe> searchByCategory(@RequestParam final String category) {
        return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    @GetMapping(path = "/search", params = "name")
    public List<Recipe> searchByNameContaining(@RequestParam final String name) {
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }
}
