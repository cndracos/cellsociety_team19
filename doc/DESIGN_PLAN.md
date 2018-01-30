### Introduction 

* Over the course of this project we will create a program based on Cellular Automata (CA) that is capable of simulating at least three different scenarios. At the very least, we will simulate a grid of cells updated according to rules of the following four models:
	1. [Schelling’s Model of Segregation](http://nifty.stanford.edu/2014/mccown-schelling-model-segregation/), which describes how population groups separate over time.
	2. [The Wa-Tor model](http://nifty.stanford.edu/2011/scott-wator-world/), which describes predator/prey relationships.
	3. [The spreading of fire](http://nifty.stanford.edu/2007/shiflet-fire/), which delineates how wildfires spread with trees.
	4. [Game of Life](https://bitstorm.org/gameoflife/)

The program will be written so that if a new user would like to implement a new type of simulation, all that they need to do is design an XML file in the proper format and add a subclasses to the superclasses`Cell` and `Grid` that describes the rules of the simulation. `Cell` and `Grid` will be abstract classes, with subclasses that represent each distinct simulation. The cells will reside in a grid, which will require change based upon the type of the simulation; there are small rules difference better implemented in grid than cell. All of the updating and coloring of the cells is done within the individual cell subclasses, so users have a high degree of freedom and flexibility if they wish to design novel simulations. If they do so, they will also need to change one method in the class that displays the graphics, namely the method that picks the appropriate cells subclass. Besides that method, we would prefer to keep all other implementation of the class that runs the simulation closed. 

### Overview

* Here are the classes that we plan to implement:
	1. **Main**. This class will launch the simulation and the GUI. It will do one thing which is create a new instance of the class called `CellSociety`.
	2. **CellSociety**. This runs the game loop and will handle all JavaFX drawing. It contains the GUI and the sliders that can change the rate of the simulation (how often it is updated). The file that explains initial cell positions and the type of simulation will be read here based upon a pop-up file selector. The grid object will reside in `CellSociety`, which has a method `step()` that redraws the cells according to their new states. When setting up the grid, this class will pick the appropriate cell subclass based upon the type of simulation requested.
	3. **Grid** This is an abstract class that handles updating all of the cells. It contains subclasses that differ based upon the type of simulation selected. The cells all live inside the grid, and the grid knows all of the cells' positions but not their states unless it is an empty cell. The grid holds all of the cells in a 2D array, and it makes 2 full passes through that 2D array. On the first pass, it calculates what should be the updated state of each cell. On the second pass, it updates the states of all cells to that newly-calculated state (this is also where the cells change color/appearance). 
	4. **Cell**. This is the only part of the program that knows the rules for updating. It will be implemented as an abstract class with a constructor, a `getCurrentState()` method, a `setCurrentState()` method, a `setColor()` method, and an abstract `update()` method, which is the only place where cell subclasses differ. The `update()` method contains the rules for updating, and it has access to the cells neighbors through a variable `myNeighbors`, which is created during initialization. Each subclass will pertain to a different simulation - one for segregation, one for fire, and one for Wa-Tor. The subclasses will contain hard-coded rules in the `update()` method pertaining to that simulation’s rules.

A basic layout for our program can be seen [here](./overview_layout.jpg).


### User Interface 

* **Input:** The user will start the program from the `Main` class, which essentially just sets the `Stage` and launches the application. The first step will be a file selector which prompts the user to pick an input XML file from a directory called “data”. If the user navigates out of the directory and tries to select a different file that is not in XML format, not in the format required by our program, or lacking the proper data needed to start the program, an error box will appear saying “Please select a valid input file.” When the user does select a valid file, the program will read it in, pick the correct cell subclass based upon the input file, initialize the grid, and then display it. The file selector is roughly pictured [here](./file_selector.jpg).

* **Mid-simulation adjustments:** All simulations will begin with a constant predetermined step size (or simulation rate) that we have yet to determine as a group. If a user would like to start with a different step size, they should refer to the `start()` function contained within the `CellSociety` class. There will be a slider underneath the display where users can either increase or decrease the step size (in seconds) to make the simulation faster or slower, respectively. 

* **Switching Simulations**: If the user wishes to switch the type of simulation during a simulation, there will be a button at the bottom right to do so. It will simply re-open the file selector in the proper directory so that the user can pick which type of simulation they would like to switch to.

* **Basic Appearance:** The cells will appear as squares in a rectangular grid, each with a black border so that it can be distinguished from other cells. Each simulation will have its own colors for each respective state defined in the cell subclasses. The title of the window will be the type of simulation, i.e. “Fire”, “Wa-Tor”, or “Segregation”. The top seven-eights of the window will be the simulation itself, and the bottom one-eighth will contain the slider to change the rate of simulation and the button to switch simulations. The basic appearance is roughly pictured [here](./example_simulation.jpg).

### Design Details

* **Parameters** (rules): speed of grid, decay speed, probability of update
 1. **CellSociety**: 

The constructor calls a function `start()` to launch the start user interface.

The start user interface allows the user to choose a specific simulation to play with. In our program, we are planning to implement three simulations that are listed on the course website. The user then chooses the simulation by typing in the name of that simulation to the program, and the name string will be assigned to a variable called `simulationType`. The user can also adjust the simulation rate through a slide on interface, and that value will be assigned to a variable called `simulationRate`.

Once the `simulationRate` is assigned a value, this class starts to read in specific XML files containing information about grid size and each cell’s initial state according to the value of` simulationType`. There are also informations about `update probability` and `step speed`. Maybe we need a another class `XMLReader `here to handle all such file readings. We also need additional variables to store these information.

After we read in the file, this class will initialize a specific type of grid(fire grid, segregation grid, etc) object according to the value of `simulationType`. Information from XML files will be passed to the grid object. This class will also initialize an interface for the grid during simulation loop. On that interface, there are different buttons for users to change the specific parameters, and also a button to exit the current simulation.

Then this class will initialize a timeline to handle the update of the grid. It will call grid’s `isUpdating()` method to check whether the current iteration of the grid ends or not, if no, it will do nothing; else it will call grid’s `update()` method to update.
2. **Grid**

This is super class to be inherited by subclasses segregation grid, Wa-Tor grid and fire grid.

The members of this class includes grid size, 2D dimension array of cells and simulation rate.

In the constructor, passed in values are stored to the private instance members of the class. And it calls a method `initGrid()`.

`initGrid()` method contains two for loop:In the first loop, initialize an two dimensional array of grid size to store all cells. The cell object is a specific type of cell according to a type of simulation. For each position, the method initialize a cell with specific initial state, step speed and update probability according to the passed in values.In the second for loop, check each cell’s neighbors in the grid, and send them as a `ArrayList<Cell>` to the cell by call cell’s `setNeighbors()` methods.

“Update” methods contains two for loops.

In the first for loop to pass all neighbors’ states as an ArrayList to the each cell for it to update. We do this by calling each cell’s `findState()` method.

In another for loop, for each cell we assign each cell’s current state to its next state and change its graphics by calling each cell’s “setState()” method. Last, this method modifies variable `isUpdating` to be false.

`isUpdating()` method return the value of `isUpdating`.

For subclasses, there may be other methods. For example, we need two data structure s, one to store all empty cells and the other to store all dissatisfied cells at each iteration in grid, and declare a method `findPosition()` to find the position where a dissatisfied cell is moved to, and update the two data structures.

3.**Cell**

This is super class to be inherited by subclasses of different cells. It manages members of `currentState`, `nextState`, update probability, step speed (?) and its neighbors. It also manages a update rule list.

In the constructor, assign all passed in values to local members.

In the `setNeighbors()` method, it assign passed in value to a private member of the class.

In the `findState()` method, the cell will access to all neighbors’ current state and compute its next state by call `updateByRule()` method. Then it decides whether or not to assign this computed new state to next state according to the `update probability`. The update also depends on `step speed`. In segregation simulation, this variable is 1, and in fire simulation, it has different values based on different states senarios. Rules of how to apply `step speed` will be implemented in each cell subclasses.

`updateByRule()` is an abstract method which contains specific rules of update and will be instantiate in each subclasses in more details.

`setState()` method set `currState` to `nextStat`e.It also change its graphics based on new `currState`.

4. **Use Cases**


* Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
* Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)

Our design handles these two cases together. Each cell will be passed in all its neighbors it has when it is initialized. So it does not need to check how many neighbors it have. 

* Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically

In our grid class, we use a two-phase protocol to do update. In the first for loop, each cell computes its own next state and stores it, but not change state and graphics immediately. In the second loop, all cells change to the next state. This design avoids mutual interfere between cell’s current state and next state. We also use` isUpdating()` to mark if the grid is during an update process, and therefore the game loop will not start a new iteration until the current one is done.

* Set a simulation parameter: set the value of a parameter, `probCatch`, for a simulation, Fire, based on the value given in an XML fire

The `CellSociety` class handles XML files reading and `probCatch` will be passed to grid. Then grid then passes it to each cell.

* Switch simulations: use the GUI to change the current simulation from Game of Life to Wator

In our simulation interface, there is a button to stop the current simulation, and the game will go back to the start interface. User can choose another simulation from there.

### Design Considerations 

Our group had a lot of debate over where to make our abstract class/subclasses due to having difficulty understanding where it is best for the simulation to perform certain actions. One such debate was on whether or not it was worth it to make ‘cell’ and abstract class with FireCell, SegCell, and WatorCell subclasses or to just have one cell class that encapsulates all the different types of cells across simulations. While it is may seem easier to have one cell that does a handful of classic ‘cell’ methods such as update, getState, and changeState that all differ based on the ‘type’ of cell we assign to it, we agreed that having all of that code in one place would lead to making it difficult to work with once we had more types of cells and other simulations. From this we chose then to have subclasses which implement the Cell superclass, and that each cell has variables specific to a certain simulation that we can easily manipulate. 

From this we then moved onto how to organize our cells, which we agreed would be held in a 2D array called grid. This array would hold type Cell and serve as a ‘home’ for which we can easily access specific locations in our square grid simulation. Each index in the array would represent a single cell, and we can iterate through this array easily and know how to access a cell in any given location. While having one grid seemed okay to us at first, it became apparent later that not only do cells vary from simulation to simulation, the way that a grid functions can vary as well. In the Fire simulation, we would want to put a ‘box’ of empty cells around our grid, while in Segregation we would not. Segregation in turn, seems to best be implemented by keeping track of the location of its empty cells, anywhere on the grid, while other simulations do not. From this we agreed that Grid should instead be an abstract class, with FireGrid, SegGrid, and WatorGrid subclasses that allow us to specify dimensions of the grid that are unique to each simulation while keeping the classic grid structure and methods, such as get(Index n, Index k). 

Once we came up with how we would organize our grid and our cells, choosing to specify the pairing dependant on the simulation, we started discussing which information would live where, how it would be accessed/used, and where it would be best for the given information to live. The first problem we encountered was figuring out how to best have a cell to know about its neighbors. At first we were planning of having an individual cell know its indexes in the 2D array then call the eight neighbors using +1/-1 from these indexes, accessing the neighbor cells’ data then comparing them with its own. This seemed clunky, as in order to access the neighbor it would have to call the grid class to give it the cell, then store that cell’s data before moving on to read all the neighbors (if necessary, as in Fire simulation once a neighbor tells it that it is on fire it can stop looking, but in Segregation it must know all neighbors before choosing whether or not to move). Instead of having the grid access a cell each loop, then have that cell call back to grid, we decided that the data of neighbors should be known by the cell after the grid has already been made. The grid would add all cells, then read through each cell again and add the cells to an ArrayList<Cell> in the cell, as now the cell would have direct access to its neighbors after a rather ugly method of doing so, but in the future things would be cleaner. 

This is still a problem we are faced with, and have considered a  couple of options that would make the process of receiving inputs from neighbors a bit easier. We are considering having the neighbors live in the grid class, so that Cell would not have to access a level higher, and then this ArrayList<Cell> could be passed through in a method to be used by Cell. Another option is a Neighbor class specific to the simulation, which gives us the aggregate values of all neighbors (such as whether any are on fire, or what percentage are X’s) of a certain cell, stored in another 2D array where the indexes are now neighbor classes that have the same indexes as the cell whose neighbors it is representing. We also are considering adding a stateManager class per simulation that would take this neighbor information and compare it with a given cell then update the cell depending on the rules of the game. All this, however, would require two new classes per simulation, adding on a layer of complexity that may not be necessary. 

A second problem we have encountered was where to store the ‘rules’ of a specific game. The probability of a fire spreading is a value that differs from game to game, and would have to read from our file then passed to wherever we choose to make the decisions on how a cell reacts to its inputs. For example, if our file told us that a cell had a 30% chance of catching on fire, we would have to pass the file to cellSociety where it would read the file and its rules, either passing the file itself or a double value called probCatch = .3 to the grid class for the simulation. Currently, we have the cell updating itself in the Cell subclasses, meaning that the grid would have to once again pass this value when creating the cell for the cell to then store for later use. If we were, however, to have a stateManager class, we would then want to pass the .3 value there. Where to put values that manage differences in a certain simulation can be complicated when moving these values and where to implement these variations, and our groups has not yet discussed the details of where they would go although we have kept them in mind as we create our game, trying to keep our code as flexible as possible. 

### Team Responsibilities

* **Charlie** will handle implementing the grid itself and all of the subclasses. Since Yameng will be handling cell implementation, Charlie will work closely with her in order to ensure smooth interactions.

* **Yameng** will handle implementing the cells and all of their subclasses. As mentioned before, she will work closely with Charlie.

* **Dylan** will handle the GUI through the `CellSociety` class, and the file selection process that accompanies it. He will also create the `Main` class through which the program will be launched. He will make sure that the grid displays properly and that cells change color according to the simulation.

We will begin by creating the `Main` and `CellSociety` classes, so that we are sure that we can display a GUI (this will also be helpful in testing our implementation). Once we have a GUI up and working, we will next create the cell class and the "Game of Life" subclass, because it is the simplest and will be easiest to test (along with the grid). Once the “Game of Life” subclass has all of its methods written, we will move on to the grid. We will first test placing cells on the grid, and then test if we can update those cells in a loop according to rules. After we have all of this working, we will implement the other three simulations, Segregation, Wa-Tor, and Fire. 



