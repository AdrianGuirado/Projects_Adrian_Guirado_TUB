from pulp import *


class IP_solver:

    def __init__(self):
        self.max = 0
        self.x   = [0, 0, 0]

    # we had to change the order of enumerated items to get the maximum:
    def LP_solving(self, x0_bnds, x1_bnds, x2_bnds):

        problem = LpProblem("knapsack_problem", LpMaximize)
        x0 = LpVariable("x0", lowBound=x0_bnds[0], upBound=x0_bnds[1])
        x1 = LpVariable("x1", lowBound=x1_bnds[0], upBound=x1_bnds[1])
        x2 = LpVariable("x2", lowBound=x2_bnds[0], upBound=x2_bnds[1])
        problem += 7 * x0 + 11 * x1 + 12 * x2
        problem += 3 * x0 +  4 * x1 +  5 * x2 <= 10
        problem.solve()
        return problem.status, problem.objective.value(), x0.value(), x1.value(), x2.value()

    def IP_solving(self, x0_bnds, x1_bnds, x2_bnds):

        status, outcome, x0, x1, x2 = self.LP_solving(x0_bnds, x1_bnds, x2_bnds)

        if status != 1 or outcome < self.max:
            return

        if abs(int(x0) - x0) > 0.01:
            res_left  = self.IP_solving((int(x0+1), x0_bnds[1]),(0, x1_bnds[1]), (0, x2_bnds[1]))
            res_right = self.IP_solving((0, int(x0)),           (0, x1_bnds[1]), (0, x2_bnds[1]))

        else:
            if abs(int(x1) - x1) > 0.01:
                res_left  = self.IP_solving((x0, x0_bnds[1]), (int(x1+1), x1_bnds[1]), (0, x2_bnds[1]))
                res_right = self.IP_solving((x0, x0_bnds[1]), (0, int(x1)),            (0, x2_bnds[1]))

            else:
                if abs(int(x2) - x2) > 0.01:
                    res_left  = self.IP_solving((x0, x0_bnds[1]), (x1, x1_bnds[1]), (int(x2+1), x2_bnds[1]))
                    res_right = self.IP_solving((x0, x0_bnds[1]), (x1, x1_bnds[1]), (0, int(x2)))

                else:
                    if outcome > self.max:
                        self.max = outcome
                        self.x = [x0, x1, x2]
                    else:
                        return

if __name__ == "__main__":

    ip = IP_solver()
    ip.IP_solving((0, None), (0, None), (0, None))
    print("optimal value: ", ip.max, ", solution: ", ip.x)



