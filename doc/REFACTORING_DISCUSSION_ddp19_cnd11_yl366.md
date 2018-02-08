## Recitation: Refactoring

### Note: we made all of our changes on the master branch since most were minimal. Check refactoring there.

* We lost out on a large portion of class time because the SonarQube analysis tool did not work on our repository initially, specifically due to our `FireGrid.java` file. There was a method inside of it, `addNeighbors()`, which had a complexity that caused the program to time out. So, naturally, the first order of business was to refactor this method to make method calls more dispersed.
* Especially with GUI components, there were a lot of "magic values", like the size of nodes on the screen. We changed values that appeared out of thin air to instance variables instead. This improves upon readability of the code.
* The analysis tool didn't point this out, but we are going to create a class that sets up components of the GUI. Then, the `CellSociety` class will only run the simulation and handle user input, while the GUI setup will handle placing nodes, structuring the `BorderPane`, etc. 