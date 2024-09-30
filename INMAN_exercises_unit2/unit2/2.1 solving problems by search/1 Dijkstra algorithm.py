# this is a greedy algorithm that obtains the shortest path thanks to:
# the optimal substructure of the problem: a shortest path between two vertices
# contains the shortest paths in between its intermediate nodes, and we can
# apply the greedy strategy: make a greedy choice and go for the remaining
# sub-problem.

class Graph():

    def __init__(self, vertices):
        self.V     = vertices
        self.cost = [[0 for column in range(vertices)] for row in range(vertices)]
        self.dist  = [float("Inf")] * self.V
        self.predecessor = {}
        self.counter = 0

    def next_non_visited_node(self, visited):

        # always returns a valid index while there is a not visited node
        min = float("Inf")

        for v in range(self.V):
            if self.dist[v] <= min and not visited[v]:
                min = self.dist[v]
                min_index = v

        return min_index

    # Dijkstra uses uniform-cost search, visits the nodes in an ordered way,
    # by known distances to the src, and does not update the distance to any
    # already visited node.
    def dijkstra(self, src):

        self.dist[src] = 0
        visited = [False] * self.V

        # as many steps as nodes to be visited: (V-1)
        # no need to visit the last node of the graph.
        for _ in range(self.V-1):

            if not any(visited):
                u = src
            else:
                u = self.next_non_visited_node(visited)

            visited[u] = True
            print("iter: ", _+1 , ", visited: ", u)

            for v in range(self.V):

                self.counter += 1

                if (not visited[v] and self.dist[v] > self.dist[u] + self.cost[u][v]):
                    self.dist[v] = self.dist[u] + self.cost[u][v]
                    self.predecessor[v] = u

        self.printDistances()
        print("\nshortest path from ", src, " to ", self.V - 1, ":", self.path(src, self.V - 1))

    def printDistances(self):
        print("\ndistances from source:", end=" ")
        for node in range(self.V):
            print(node, ":", self.dist[node], " | ", end=" ")
        print("\n\nPredecessors: ", self.predecessor)

    # we already know the predecessors of any node as the shortest path from src
    # before calling this method:
    def path(self, src, dst):
        path = [dst]
        node = dst
        while node != src:
            path.append(self.predecessor[node])
            node = self.predecessor[node]
        path.reverse()
        return path


if __name__ == "__main__":

    i = float("Inf")

    g = Graph(6)
    g.cost = [[0, 4, 3, i, i, i],
              [i, 0, i, 2, 2, i],
              [i, i, 0, 8, 2, i],
              [i, i, i, 0, i, 2],
              [i, i, i, i, 0, 2],
              [i, i, i, i, i, 0]]
    g.dijkstra(0)

    print("\n counter = ", g.counter)
    print("\n--------------------\n")

# here we can see, with a negative length, the algorithm provides a wrong result
# because for an already visited node, we never update its distance from source.
    g = Graph(4)
    g.cost = [[0, 6, 5, i],
              [i, 0,-2, i],
              [i, i, 0, 2],
              [i, i, i, 0]]
    g.dijkstra(0)
    print("\n counter = ", g.counter)

