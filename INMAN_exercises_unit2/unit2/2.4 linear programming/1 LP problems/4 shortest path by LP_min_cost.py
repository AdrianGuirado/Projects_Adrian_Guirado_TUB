from pulp import *

# min problem:
problem = LpProblem("shortest_path_problem", LpMinimize)

# each link is a variable of the LP problem:
# [lnk_01, lnk_02, lnk_13, lnk_14, lnk_23, lnk_24, lnk_35, lnk_45]
lnk_01 = LpVariable("link_01", lowBound=0)
lnk_02 = LpVariable("link_02", lowBound=0)
lnk_13 = LpVariable("link_13", lowBound=0)
lnk_14 = LpVariable("link_14", lowBound=0)
lnk_23 = LpVariable("link_23", lowBound=0)
lnk_24 = LpVariable("link_24", lowBound=0)
lnk_35 = LpVariable("link_35", lowBound=0)
lnk_45 = LpVariable("link_45", lowBound=0)

link_cost = [4, 3, 2, 2, 8, 2, 2, 2]

# utility function: minimize the overall cost:
problem += lpDot(link_cost, [lnk_01, lnk_02, lnk_13, lnk_14, lnk_23, lnk_24, lnk_35, lnk_45])

# constraints:

# flow conservation at each node_u, input_flow_to_u == output_flow_from_u:
problem +=               1 == lnk_01 + lnk_02
problem +=          lnk_01 == lnk_13 + lnk_14
problem +=          lnk_02 == lnk_23 + lnk_24
problem += lnk_13 + lnk_23 == lnk_35
problem += lnk_14 + lnk_24 == lnk_45
problem += lnk_35 + lnk_45 == 1

# The problem is solved using PuLP's choice of Solver
problem.solve()

# The status of the solution is printed to the screen
print(f"Status: {LpStatus[problem.status]}\n")

print(f"cost value: {problem.objective.value()}\n")

# Each of the variables is printed with it's resolved optimal value
print("shortest path: ", end="")
for v in problem.variables():
    if v.varValue > 0:
        print(f" - {v.name}", end="")