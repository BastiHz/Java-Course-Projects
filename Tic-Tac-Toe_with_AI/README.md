## Tic-Tac-Toe with AI

A simple console application of the famous game Tic-tac-toe, also known as 
Noughts and Crosses. You can play against another human, or an ai with three
difficulty levels:
- easy: The computer chooses randomly.
- medium: Checks the next move to see if it can win or prevent the opponent 
  from winning. Otherwise, it chooses randomly.
- hard: This one uses the minimax algorithm to check all possible 
  outcomes and always picks the best move.

At the start the program, when prompted, type `start player1 player2` 
and replace the players with one of the following: `user`, `easy`, `medium`, `hard`.
Choose `user` for human players. You can also let two ais battle against each other.
For example: To play against the hard ai and make the first move, enter
`start user hard`.  
Type `exit` to quit.

When prompted to enter coordinates, type the row and column number separated by a space. 
For example `1 3` results in this:
```
Enter the coordinates: 1 3
---------
|     X |
|       |
|       |
---------
```

Have fun!