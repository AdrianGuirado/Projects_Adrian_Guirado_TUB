from pulp import *


def main():

    # using a dictionary as the input data of the problem:

    data = {
            "num_reqs": 3,
            "ing_node": [0, 1, 2], # ingress_nodes
            "egr_node": [5, 4, 3], # egress_nodes
            "flow":     [4, 4, 2], # commodity_flow

            "num_ndes": 6,
            "nde_to_nde":
                [[0, 1, 1, 0, 0, 0],
                 [0, 0, 0, 1, 1, 0],
                 [0, 0, 0, 1, 1, 0],
                 [0, 0, 0, 0, 1, 1],
                 [0, 0, 0, 0, 0, 1],
                 [0, 0, 0, 0, 0, 0]],
            "capacity":
                [[0, 8, 6, 0, 0, 0],
                 [0, 0, 0, 6, 1, 0],
                 [0, 0, 0, 3, 4, 0],
                 [0, 0, 0, 0, 6, 4],
                 [0, 0, 0, 0, 0, 6],
                 [0, 0, 0, 0, 0, 0]],
            "cost":
                [[0, 3, 3, 0, 0, 0],
                 [0, 0, 0, 3, 3, 0],
                 [0, 0, 0, 3, 3, 0],
                 [0, 0, 0, 0, 3, 3],
                 [0, 0, 0, 0, 0, 3],
                 [0, 0, 0, 0, 0, 0]],
            }

    REQs = range(data["num_reqs"])
    NDEs = range(data["num_ndes"])

    # min problem:
    problem = LpProblem("unsplittable_MCF_problem", LpMinimize)

    # variables of the ILP problem:
    # TO BE COMPLETED
    var_flow = ...

    # utility function: minimize the cost of transmitting all flows through all links:
    # TO BE COMPLETED

    # constraints:

    # for each link(u,v), all allocated flows must not exceed the link capacity:
    # TO BE COMPLETED

    # for each req, for each node_u applies the flow conservation:
    # TO BE COMPLETED

    problem.solve()

    # The status of the solution is printed to the screen
    print(f"Status: {LpStatus[problem.status]}\n")

    # print out the flow sent by each link(u,v) of all requests
    print(f"MCF unsplittable min cost: {problem.objective.value()}")
    for req in REQs:
        print(f"\nreq_{req}, used links: ", end="")
        for node_u in NDEs:
            for node_v in NDEs:
                if var_flow[req][node_u][node_v].value() == 1.0:
                    print(f"link_{node_u}{node_v}, ", end="")

    print(f"\ntotal cost = {value(problem.objective)}\n")


if __name__ == "__main__":
    main()