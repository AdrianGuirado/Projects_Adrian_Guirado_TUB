class Graph():

    def __init__(self, vertices):
        self.V = vertices
        self.graph = [[0 for column in range(vertices)] for row in range(vertices)]

    # check if agent a can do task t:
    def isSafe(self, a, t, state):
        # to be completed
        pass

    # A recursive function to solve the bipartite matching problem:
    # a is the agent, state are the current assignments to agents
    def successor_function(self, a, state):

        # to be completed

        pass

    def getMatching(self):

        state = [-1] * self.V

        assignment_success = self.successor_function(a=0, state=state)

        if not assignment_success:
            print('no solution exist')

        else:
            print("Solution exist with assigned tasks:")
            for index in range(len(state)):
                print("Agent ", index, " to Task ", state[index])

if __name__ == "__main__":

    g = Graph(6)
    g.graph = [[0, 1, 1, 0, 0, 0],
               [1, 0, 0, 1, 0, 0],
               [0, 0, 1, 0, 0, 0],
               [0, 0, 1, 1, 0, 0],
               [0, 0, 0, 0, 1, 1],
               [0, 0, 0, 0, 1, 1]]

    g.getMatching()

