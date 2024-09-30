# this is said to be a dynamic-programming algorithm,
# algorithm feasible thanks to the optimal substructure of the problem:
# a shortest path between two vertices contains the shortest paths in between its intermediate nodes.

# we are applying dynamic programming because, the solution to a problem
# is obtained from the solution to its constituent sub-problems, therefore,
# the solution is obtained in a recursive (iterative in this case) manner,
# solving all sub-problems (bottom-up) until solving the original problem.
# this is what is done implicitly, although what is explained before is exactly
# the description of the dynamic programming method.

class Graph():

    def __init__(self, vertices):
        self.V     = vertices
        self.dist = [[0 for column in range(vertices)] for row in range(vertices)]
        self.next = [[0 for column in range(vertices)] for row in range(vertices)]

    def init_next(self):
        for i in range(self.V):
            for j in range(self.V):
                if self.dist[i][j] != float("Inf"):
                    self.next[i][j] = j
                else:
                    self.next[i][j] = float("Inf")

    def floydWarshall(self):
        self.init_next()
        # complexity: O(V^3)
        # k = 0, we want to consider using node 0 as alternative path between each pair of nodes,
        # only makes sense if node 0 is in a valid path: [i-0-j]
        # k = 1, we want to use node 1 between each pair of nodes, from the previous step:
        # for any valid [1-0-j] or [i-0-1], we can reuse those sub-chains starting or ending with 1 to
        # define new valid paths if they are a shorter path for two edge-nodes:
        # i+[1-0-j] or [i-0-1]+j, aside from valid new sub-chains: [i-1-j],
        # with k = 2, the sub-chains: [2-1-0-j], [i-0-1-2], [2-1-j], [2-0-j], [i-1-2], [i-0-2], that is,
        # any sub-chain starting or ending with 2, with or without 0 and 1 in between,
        # can be reuse to define a new path:[i..2..j], therefore at each iteration of the k for-loop
        # we are discovering all valid and useful sub-chains with the combinations
        # of up to the first k nodes in between any: i and j, and we reuse all the subchains
        # starting or ending with the k of the next iteration.
        # in the end we get the shortest-path of ALL!!! the pairs.
        for k in range(self.V):
            for i in range(self.V):
                for j in range(self.V):
                    if self.dist[i][j] > self.dist[i][k] + self.dist[k][j]:
                        self.dist[i][j] = self.dist[i][k] + self.dist[k][j]
                        self.next[i][j] = self.next[i][k]

        self.printDistances()
        self.printPaths()

    def printDistances(self):

        for i in range(self.V):
            for j in range(self.V):
                if(self.dist[i][j] == float("Inf")):
                    print ("%s" % "i", end=" ")
                else:
                    print ("%d" % self.dist[i][j], end=' ')
            print()

    def printPaths(self):
        for i in range(self.V):
            for j in range(self.V):
                if i != j:
                    path = self.path(i, j)
                    if len(path) > 0:
                        print("shortest path from: ", i, " to: ", j, ":", self.path(i, j))

    def path(self, src, dst):
        path = []
        if self.next[src][dst] == float("Inf"):
            return path
        path.append(src)
        while src != dst:
            src = self.next[src][dst]
            path.append(src)
        return path

if __name__ == "__main__":

    i = float("Inf")

    g = Graph(6)

    g.dist = [ [0, 4, 3, i, i, i],
               [i, 0, i, 2, 2, i],
               [i, i, 0, 8, 2, i],
               [i, i, i, 0, i, 2],
               [i, i, i, i, 0, 2],
               [i, i, i, i, i, 0]]

    g.floydWarshall()

    print("\n--------------------\n")

    g = Graph(4)
    g.dist = [ [0, 6, 5, i],
               [i, 0,-2, i],
               [i, i, 0, 2],
               [i, i, i, 0]]
    g.floydWarshall()
