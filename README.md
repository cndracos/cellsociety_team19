### Overview

* There will be a `Main` class that will launch the simulation and the GUI. It will do one thing which is create a new instance of the class called `CellSociety` which runs the game loop and will handle all JavaFX drawing. Inside of `CellSociety`, there will be an object called `Grid`, which handles updating all of the cells. The cells all live inside the grid, and the grid knows all of the cells' positions. 
