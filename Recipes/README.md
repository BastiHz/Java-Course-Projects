## Recipes

A Spring Boot application that provides a REST API for a database containing recipes.

### Usage

The server listens on `http://localhost:8080`.

You need to register a user to interact with the database.
All users can read all recipes, but only the user who added a recipe 
can modify or delete it. The authentication method used is basic authentication.

Bold values in the paths are placeholders and must be replaced by the appropriate strings before sending the requests.

| Action | Method | Path | Message Body | Response |
| --- | --- | --- | --- | --- |
| Register a user | POST | /api/register | JSON with email and password, see below | no | 200 OK |
| Add a recipe | POST | /api/recipe/new | recipe JSON, see below | JSON with the recipe ID |
| View a recipe | GET | /api/recipe/**id** |  | the recipe JSON |
| Modify a recipe | PUT | /api/recipe/**id** | the new recipe JSON | 204 No Content |
| Delete a recipe | DELETE | /api/recipe/**id** |  | 204 No Content |
| List all recipes | GET | /api/recipe/all |  | JSON array with recipes |
| List all of your recipes | GET | /api/recipe/my-recipes |  | JSON array with recipes |
| List all recipes from a category | GET | /api/recipe/search?category=**category** |  | JSON array with recipes |
| List all recipes with names containing a string | GET | /api/recipe/search?name=**name** |  | JSON array with recipes |

This is an example message body for registering a user. The email must contain "@" and "." and the password must be at least 8 characters long.
```
{
    "email": "foo@example.com",
    "password": "abcdefgh"
}
```
This is an example message body for a recipe. Name, category and description are strings. Ingredients and directions are string arrays.
```
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
```

The H2 console is available via a webbrowser at `http://localhost:8080/h2`. There you can view and modify the database directly. 
To login set JDBC URL to `jdbc:h2:file:./recipes_db` and User Name to `user`. No password.
