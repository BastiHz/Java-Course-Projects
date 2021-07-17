## Guess the Animal

A guessing game where the computer tries to guess which animal you are 
thinking of via a series of yes/no questions. If it fails to give the correct 
answer, it asks for the name of the new animal and a fact to differentiate 
it from others. The data is stored in a binary tree and saved to a json, xml 
or yaml file.
In this project I also practiced internationalization by providing an english
and an esperanto locale. I don't speak esperanto. It's just what the course 
provided and it's easy to implement. Check it out by running 
`./gradlew run -Duser.language=eo`.

The knowledge tree is saved in animals.json (or .xml or .yaml). The data 
is saved when you exit the game via the main menu.


### Example

Run it with `./gradlew run`. It serializes to json by default but you can use xml 
with `./gradlew run --args="-type xml"` or yaml with `./gradlew run --args="-type yaml"`.

If the database file is empty or does not exist, the program starts by asking you for 
your favorite animal which it uses to create the first tree node.  
Lines beginning with `>` are user input.
```
Good afternoon!

I want to learn about animals.
Which animal do you like most?
> cat
Marvelous! I've learned so much about animals!

Welcome to the animals expert system!

What do you want to do:
1. Play the guessing game
2. List of all animals
3. Search for an animal
4. Calculate statistics
5. Print the Knowledge Tree
0. Exit
> 1

You think of an animal, and I guess it.
Press enter when you're ready.

Is it a cat?
> no
I give up. What animal do you have in mind?
> bird
Specify a fact that distinguishes a cat from a bird.
The sentence should be of the format: 'It can/has/is ...'.
> it can fly
Is the statement correct for a bird?
> yes
I learned the following facts about animals:
 - The cat can't fly.
 - The bird can fly.
Great! I've learned so much about animals!

Would you like to play again?
> yes

You think of an animal, and I guess it.
Press enter when you're ready.

Can it fly?
> yes
Is it a bird?
> no
I give up. What animal do you have in mind?
> bat
Specify a fact that distinguishes a bird from a bat.
The sentence should be of the format: 'It can/has/is ...'.
> it is a mammal
Is the statement correct for a bat?
> yes
I learned the following facts about animals:
 - The bird isn't a mammal.
 - The bat is a mammal.
Awesome! I've learned so much about animals!

One more game?
> yes

You think of an animal, and I guess it.
Press enter when you're ready.

Can it fly?
> no
Is it a cat?
> no
I give up. What animal do you have in mind?
> snake
Specify a fact that distinguishes a cat from a snake.
The sentence should be of the format: 'It can/has/is ...'.
> it has legs
Is the statement correct for a snake?
> no
I learned the following facts about animals:
 - The cat has legs.
 - The snake doesn't have legs.
Marvelous! I've learned so much about animals!

One more game?
> yes

You think of an animal, and I guess it.
Press enter when you're ready.

Can it fly?
> n
Does it have legs?
> y
Is it a cat?
> y
It's great that I got it right!

Want to play again?
> no

What do you want to do:
1. Play the guessing game
2. List of all animals
3. Search for an animal
4. Calculate statistics
5. Print the Knowledge Tree
0. Exit
> 2
Here are the animals I know:
 - bat
 - bird
 - cat
 - snake

What do you want to do:
1. Play the guessing game
2. List of all animals
3. Search for an animal
4. Calculate statistics
5. Print the Knowledge Tree
0. Exit
> 5

 └ It can fly.
  ├ Is it a mammal?
  │├ a bat
  │└ a bird
  └ Does it have legs?
   ├ a cat
   └ a snake

What do you want to do:
1. Play the guessing game
2. List of all animals
3. Search for an animal
4. Calculate statistics
5. Print the Knowledge Tree
0. Exit
> 4
The Knowledge Tree stats
- root node                    It can fly.
- total number of nodes        7
- total number of animals      4
- total number of statements   3
- height of the tree           2
- minimum depth                2
- average depth                2.0

What do you want to do:
1. Play the guessing game
2. List of all animals
3. Search for an animal
4. Calculate statistics
5. Print the Knowledge Tree
0. Exit
> 3
Enter the animal:
> snake
Facts about the snake:
 - It can't fly.
 - It doesn't have legs.

What do you want to do:
1. Play the guessing game
2. List of all animals
3. Search for an animal
4. Calculate statistics
5. Print the Knowledge Tree
0. Exit
> 0
I'm off!
```