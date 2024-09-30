class Graph():

    def __init__(self, vertices):
        self.V       = vertices
        self.graph   = [[0 for column in range(vertices)] for row in range(vertices)]
        self.dist    = [0] * self.V
        self.predecessor = {}

    def longest_path(self, src):
        # obtain the longest distances from src to any other node
        # to be completed
        pass

    def printDistances(self):
        print("\ndistances from source:", end=" ")
        # print all longest distances from src to any other node
        # to be completed
        pass

    def path(self, src, dst):
        # return the longest path from src to dst
        # to be completed
        pass


if __name__ == "__main__":

    i = float("Inf")

    g = Graph(6)

    # use the same graph as for the shortest-path problem
    #g.graph = ...

    g.longest_path(0)

