class Graph():

    def __init__(self, num_nodes):
        self.N = num_nodes
        self.graph = [[0 for column in range(num_nodes)] for row in range(num_nodes)]
        self.NUM_COLOURS = 0
        # we set the names of the colours up to 7 colours, to be extended if we need more
        self.colour_name = ["NONE", "Orange", "Green", "Yellow", "Blue", "Red", "White", "Black"]

    # check if node can use colour c:
    def isSafe(self, node, c, state):
        for other_node in range(self.N):
            if self.graph[node][other_node] == 1 and state[other_node] == c:
                return False
        return True

    # A recursive function to solve the colouring problem:
    def successor_function(self, node, state):

        # complete assignment, solution found, we exit:
        if node == self.N:
            return True

        # checking, in order, to assign a colour,
        # from set: {1..NUM_COLOURS} to current node:
        for c in range(1, self.NUM_COLOURS + 1):

            if self.isSafe(node, c, state):

                state[node] = c

                if self.successor_function(node=node + 1, state=state):
                    return True

                # this is the backtracking strategy of depth-first-search,
                # the colour for node is reset, so, we check next colour
                # within the above loop: for c in range...
                else:
                    state[node] = 0

        return False

    def graphColouring(self, num_colours):

        self.NUM_COLOURS = num_colours

        # colour 0 means no colour assigned yet,
        # initial state: [0,...,0]:
        state = [0] * self.N

        assignment_success = self.successor_function(node=0, state=state)

        if not assignment_success:
            print('no solution for NUM_COLOURS = ' + str(num_colours))

        else:
            print("Solution exist with assigned colours:")
            for index in range(len(state)):
                print("Node ", index, " = ", self.colour_name[state[index]])

if __name__ == "__main__":

    g = Graph(8)
    g.graph = [[0, 1, 1, 0, 0, 0, 0, 0],
               [1, 0, 1, 1, 1, 0, 0, 0],
               [1, 1, 0, 0, 1, 1, 0, 0],
               [0, 1, 0, 0, 1, 0, 1, 0],
               [0, 1, 1, 1, 0, 1, 1, 1],
               [0, 0, 1, 0, 1, 0, 0, 1],
               [0, 0, 0, 1, 1, 0, 0, 1],
               [0, 0, 0, 0, 1, 1, 1, 0],
               ]

    num_colours = 3

    g.graphColouring(num_colours)

