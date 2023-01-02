# ECMM462-Team-L
## This is a Readme file of this project For Team L
### Team member：Chenyang Fan, Ruilong Liu, Tweedie Adam, Srinivas Karan, Suresh Yedukrishnan

## First, we created a model of the container environment.
#### simulation.java: Generate a simulation environment.
#### world.java: Generate a word that includes the timestamp, amount of blocks, amount of buffer stacks, a crane and Functions to change the variable state in the world.
#### stack.java: Define arrive stack, buffer stacks and handover stacks. Here we defined that buffer stacks have three stacks and their capacity is seven, the capacity of the arrive stack is five.
#### block.java: Defined the id of the block, the arrival time of the block, initial position and due time. Then randomly generate the blocks.
#### crane.java: Defined a crane, and a function of the crane state.
#### property.java: Define the time of each action, here all defaults to 1.
#### solver.java: Generate a solver, Depending on the state of the Crane and stacks, the crane is made to move and grip accordingly.

## Second, apply evolutionary algorithms to solve the current model
#### RandomNumberGenerator.java: Randomly generate arrival time, due time and ready time of each block.
#### ea.java: Specific evolutionary processes, including: crossover, mutation and tournament selection
#### evaluation.java: Evaluation of the fitness


## Reproduce the outputs


