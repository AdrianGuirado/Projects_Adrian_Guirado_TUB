# this is another common greedy algorithm.

import random

class Graph():

    def __init__(self, vertices):
        self.V             = vertices
        # shortest_edge contains (is updated) the shortest-edge (distance)
        # to all non-visited nodes from all visited nodes:
        self.my_shortest_edge = [float("Inf")] * self.V
        self.graph  = [[0 for column in range(vertices)] for row in range(vertices)]
        self.linked_to = [None] * self.V

    def printMST(self):
        print("Resultant Minimum Spanning Tree:")

        # to be completed

        pass

    def get_shortest_edge(self, visited):

        min = float("Inf")

        # return the closest node to the existing tree, not yet in the tree
        # to be completed

        pass

    def primMST(self):

        first_node = 0 #random.randint(0, self.V - 1)
        self.my_shortest_edge[first_node] = 0
        self.linked_to[first_node] = -1
        visited = [False] * self.V

        # to be completed

        pass


if __name__ == "__main__":

    i = float("Inf")

    g = Graph(5)

    g.graph = [ [0, 6, 4, i, 5],
                [6, 0, 2, 1, 4],
                [4, 2, 0, 2, 2],
                [i, 1, 2, 0, 3],
                [5, 4, 2, 3, 0]]

    g.primMST()

