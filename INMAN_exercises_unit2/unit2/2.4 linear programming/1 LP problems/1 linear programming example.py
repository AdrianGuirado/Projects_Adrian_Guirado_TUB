from pulp import *

# min: z = -x0 + 4*x1
# subject to:
# -3*x0 +  x1 <=  6
#   x0 + 2*x1 <=  4
#          x1 >= -3

# min problem:
problem = LpProblem("problem_example_1", LpMinimize)

x0 = LpVariable("x0")
x1 = LpVariable("x1")

# utility function:
problem += -1 * x0 + 4 * x1

# constraints:
problem += -3 * x0 + 1 * x1 <=  6
problem +=  1 * x0 + 2 * x1 <=  4
problem +=               x1 >= -3

# The problem is solved using PuLP's choice of Solver
problem.solve()

# The status of the solution is printed to the screen
print(f"Status: {LpStatus[problem.status]}\n")

# Each of the variables is printed with it's resolved optimal value:
print("result: ")
print(f"min value: {problem.objective.value()}")
print(f"{x0.name} = {x0.value()}")
print(f"{x1.name} = {x1.value()}")

