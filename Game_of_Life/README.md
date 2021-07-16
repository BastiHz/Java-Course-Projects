## Game of Life

[Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) 
cellular automaton. With a Swing GUI that enables the user to start and stop 
the simulation and step through it. The simulation and the GUI run in separate 
threads.

At the start the world is randomly populated with living and dead cells. 
At every time step all cells and their 8 direct neighbors are checked:
- A living cell with 2 or 3 alive neighbors survives. Otherwise, it dies.
- A dead cell with exactly 3 living neighbors becomes alive.

The world is a torus. That means the left and right borders and the top and 
bottom borders are connected to each other.
