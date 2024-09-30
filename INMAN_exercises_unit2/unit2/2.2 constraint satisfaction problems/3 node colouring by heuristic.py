import numpy as np


class Graph():

    def __init__(self, vertices):
        self.V = vertices
        self.graph = [[0 for column in range(vertices)] for row in range(vertices)]
        self.num_colours = 0
        self.availableColours_per_node = self.available_colours()

    def conflicting_degree(self):
        num_conflicts =[]
        for node in range(self.V):
          num_conflicts.append(sum(self.graph[node]))
        indexes_by_num_conflicts = np.flip(np.argsort(num_conflicts), 0)
        return indexes_by_num_conflicts

    def available_colours(self):
        availableColours = {}
        for node in range(self.V):
          availableColours[node]=["Orange", "Yellow", "Green", "Blue", "Red", "White", "Black"]
        return availableColours

    def graphColouring(self):

        assigned_colour_per_node = [-1] * self.V
        indexed_by_num_conflicts = self.conflicting_degree()

        for node in indexed_by_num_conflicts:

          if len(self.availableColours_per_node[node]) == 0:
              print("no solution using 7 colours at the most")

          assigned_colour_per_node[node] = self.availableColours_per_node[node][0]

          for j in range(self.V):
            if self.graph[node][j]==1 and (assigned_colour_per_node[node] in self.availableColours_per_node[j]):
              self.availableColours_per_node[j].remove(assigned_colour_per_node[node])

        for node in range(self.V):
          print("Node", node," = ", assigned_colour_per_node[node])

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

    g.graphColouring()