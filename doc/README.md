# cellsociety 

### Inheritance Review

#### Bricks
Inheritance for bricks would be useful because some bricks are different than others. For example, some have power-ups, some are differently shaped, and some take more than 1 hit to break. However, all go back to the same basic functionalities: breaking, deflecting the ball, (possibly) dropping power-ups, etc.

###### Method Signatures
```java
public abstract Class Brick extends Rectangle {

	// check if the ball has collided with a brick
	public abstract boolean checkCollision(Ball ball);
	
	// performs the action when the brick is hit
	public abstract void collide();
```

#### Power Ups
It would be useful to have inheritance for power-ups because each one changes different features in the game. There could be a method `implementPowerUp()` that applies the relevant changes for the game, and appears in the abstract class `PowerUp`. Additionally, each power-up appears differently.

###### Method Signatures
```java
public abstract Class PowerUp {

	// implements the power-up in the game
	public abstract void implement(Group root);
	
	// sets the image for this power-up
	public abstract void setImage(Image image);
```

#### Levels
A level might have different rules for where the paddle can/can't go, where the blocks are, how fast the ball moves, etc. But each level has basic functionalities like placing blocks, placing the paddle and ball, etc. Thus, having super abstract class that defines these methods would be useful, but each `Level` instance can implement them separately.

### Cell Society High Level Design

1. The cell knows what rules to apply based on predefined rules that the program knows from the configuration file.
2. A cell knows about its neighbors because there is a grid of all locations of cells, and each cell can access it to determine its neighbors. A cell has its own update method (defined in its super class) that is independent of other cells updating, but it will only update once the _current_ states of all cells are saved. That way, no conflicts are introduced.
3. The grid is a data structure containing the locations of all of the cells. The grid has behaviors defined by the simulation, but at a low-level it knows everything that inhabits it and it can update itself at each time iteration. It needs to be able to get information from all of the cells so that it can properly update it. The cell needs to know about the grid, because it needs to know the neighbors around it. 
4. The configuration file needs to contain:
	* The initial state (including position) of all cells
	* The size of the grid
	* Basic properties of cells, like appearance, size, etc.
	* Update rules (behaviors) of cells
5. Each cell holds two values - its current state and its next state based upon the __current states__ of all other cells. Once each cell has its next state, the GUI redraws the cells (on the grid) in their new states.
