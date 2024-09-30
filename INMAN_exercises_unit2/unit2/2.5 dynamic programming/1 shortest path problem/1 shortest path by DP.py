class Graph():

    def __init__(self, stages, dist):
        self.STAGES    = stages
        self.dist      = dist
        self.val_f     = [[inf for column in range(len(self.dist))] for row in range(stages)]
        self.next_state = [[-1 for column in range(len(self.dist))] for row in range(stages)]

        for i in range(stages):
            for j in range(len(self.val_f[0])):
                self.val_f[i][j] = inf

    def next_states(self, state):
        states = []
        for j in range(len(dist[state])):
            if dist[state][j] != inf and state != j:
                states.append(j)
        return states

    def f(self, stage, state):

        # we have reached the last stage, so:
        if stage == self.STAGES - 1:
            return 0

        min_value_state = inf
        chosen_state    = -1

        for next_state in self.next_states(state):
            if self.val_f[stage+1][next_state] == inf:
                print("calculating to f(", stage+1, ",", next_state, ")")
                self.val_f[stage+1][next_state] = self.f(stage+1, next_state)
            else:
                print("taken from store f(", stage + 1, ",", next_state, ")")
            new_value_state = dist[state][next_state] + self.val_f[stage+1][next_state]
            if new_value_state < min_value_state:
                min_value_state = new_value_state
                chosen_state = next_state

        self.val_f[stage][state]      = min_value_state
        self.next_state[stage][state] = chosen_state

        return min_value_state


if __name__ == "__main__":

    inf = float("Inf")

    # we know the number of stages because the graph from which to obtain
    # the shortest-path is a figurative-graph not coming from a real network,
    # the figurative-graph will come from solving a generic optimization problem.
    stages = 5
    dist   = [[0, 550, 900, 770, inf, inf, inf, inf, inf, inf],
              [inf, 0, inf, inf, 680, 790, 1050, inf, inf, inf],
              [inf, inf, 0, inf, 580, 760, 660, inf, inf, inf],
              [inf, inf, inf, 0, 510, 700, 830, inf, inf, inf],
              [inf, inf, inf, inf, 0, inf, inf, 610, 790, inf],
              [inf, inf, inf, inf, inf, 0, inf, 540, 940, inf],
              [inf, inf, inf, inf, inf, inf, 0, 790, 270, inf],
              [inf, inf, inf, inf, inf, inf, inf, 0, inf, 1030],
              [inf, inf, inf, inf, inf, inf, inf, inf, 0, 1390],
              [inf, inf, inf, inf, inf, inf, inf, inf, inf, 0],
              ]

    g = Graph(stages, dist)

    cost = g.f(0, 0)

    print("\nresultant f(stage, state):")
    for stage in range(g.STAGES-1):
        print(g.val_f[stage])

    state = 0

    print("\nmin cost: ", cost)
    for stage in range(g.STAGES - 1):
        next_state = g.next_state[stage][state]
        print("stage:", stage, ", next_state:", next_state+1)
        state = next_state

