Yameng Liu
===================


I am doing Cell parts in the group.

----------


Part 1
-------------
1. I encapsulate the the process to compute the new state for other parts. So the cell class receive information about the neighbors, and all computation of a new state to update to is going within the class itself.
2. In my part, there is a super class"Cell" which implements all the common behaviors, like store the neighbor list, new state and current state.
	For each different simulation, there is a cell subclass which implements different update rules and different rules to update colors according to the current state.
3. I closed computation of new state and new color, and the cell class communicates with grid class and neighbors to receive information for update.
4. Errors should occurs at front-end. For my part, errors may occurs when player type in type of simulation that current program can not handle, in this situation, the program will throw a "SimulationTypeError".
5. I close all part that a cell class needs to close and open all parts that a cell class needs to open. This is also a measure of a good design. Also a good design does not have redundant parts. This is also true in my design, cause none of members or methods are useless.

Part 2
-------------
1. My cell received updated states of neighbors at each iteration, and grid call cells to let it update.
2. Yes, it depends on the codes in grid. The grid class has to include codes to call cell class to update.
3. In my implementation, excepts those information which needs to be received from outside (like when to update and neighbors's states), I encapsulate other parts in cell classes( like how to update).
3. I closed computation of new state and new color, and the cell class communicates with grid class and neighbors to receive information for update.
4. Super class "Cell" and subclass "FireCell". In common, they need to get the array of neighbors from grid class, update neighbors's state from neighbors, and be called when grid is asking them to update at iteration. What "FireCell" class has additionally is a method to compute the new state according to specific rule lists and update graphics according to the state.
One part to improve is to encapsulate the method to compute the new state to be a separate class. Since this methods contains many details, it could be better in design if put them in another class.

Part 3
-------------
1. (1) The player chooses a specific type of simulation, and that parameter will be passed to the program, and program instantiates specific type of cell subclasses.
    (2)For fire simulation, incorporate step speed into program. So a tree will not catch fire immediately. I should design carefully to make sure there are no conflicts between state transition.
    (3) Since the neighbors are passed to cell class at the beginning, it will not change through the simulation. In segregation simulation, rather than move cells, we exchange the states of cells, so each cell's neighbors are constant.
    (4) The grid does not handle update, the cell classes does. Each cell does a one step update and it is shown on grid.
    (5) To add more rules, the programmer change the updateByRule() method. 
2. To write more complicated update rule including step speed and design an elegant algorithm for dissatisfied cell to find cell to move to.
3. Read specific files according to the passed values from interface. For example, player chooses the type of simulation and program opens the according XML file. We have little experiences to handle interface, so we need more time to figure it out.
