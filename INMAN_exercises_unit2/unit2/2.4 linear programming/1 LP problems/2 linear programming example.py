from pulp import *

# max: z = 2*x0 + 3*x1
# subject to:
#    x0 + 2*x1 <=  6
#  2*x0 +   x1 <=  8
#    x0,    x1 >=  0

# max problem:
problem = LpProblem("problem_example_2", LpMaximize)

x0 = LpVariable("x0", lowBound=0)
x1 = LpVariable("x1", lowBound=0)

# utility function:
problem += 2 * x0 + 3 * x1

# constraints:
problem +=  1 * x0 + 2 * x1 <=  6
problem +=  2 * x0 + 1 * x1 <=  8

# The problem is solved using PuLP's choice of Solver
problem.solve()

# The status of the solution is printed to the screen
print(f"Status: {LpStatus[problem.status]}\n")

# Each of the variables is printed with it's resolved optimal value:
print("result: ")
print(f"max value: {problem.objective.value()}")
print(f"{x0.name} = {x0.value()}")
print(f"{x1.name} = {x1.value()}")

