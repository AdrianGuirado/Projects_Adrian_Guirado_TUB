class Graph:

    def __init__(self, vertices):
        self.V     = vertices
        self.graph = []
        self.dist  = [float("Inf")] * self.V
        self.predecessor = {}

    def addEdge(self, u, v, w):
        self.graph.append([u, v, w])

    def BellmanFord(self, src):

        self.dist[src] = 0
        # this is the trick, initially you don't know self.dist[u]
        # except for the src, yourself, if a router using a distance-vector
        # routing protocol, you only know the distances between pairs of nodes.

        # complexity: O(V * E)
        # the shortest-path to any node only visits each node along the
        # path one time, therefore, iterating V-1 times assures
        # the array: dist has been updated to its definitive values, as,
        # on each iteration, one or more dist will be definitive.
        # usually we will not need V-1 iterations.
        for _ in range(self.V - 1):

            print("iter: ",_+1)
            for u, v, w in self.graph:

                if self.dist[u] + w < self.dist[v]:
                    self.dist[v] = self.dist[u] + w
                    print("updated dist[",v,"]=",self.dist[v])
                    # this makes sense if v is one-hop away from u:
                    self.predecessor[v] = u

        # this is the result:
        for u, v, w in self.graph:

            if self.dist[u] + w < self.dist[v]:
                print("Graph contains a negative cycle!!! [",u,",",v,"]")

        self.printDistances()
        print("\npath: ", self.path(src, self.V - 1))

    def path(self, src, dst):
        path = [dst]
        node = dst
        while node != src:
            path.append(self.predecessor[node])
            node = self.predecessor[node]
        path.reverse()
        return path

    def printDistances(self):
        print("distance from source:", end=" ")
        for node in range(self.V):
            print(node, ":", self.dist[node], " | ", end=" ")


if __name__ == "__main__":

    g = Graph(6)
    g.addEdge(0, 1, 4)
    g.addEdge(0, 2, 3)
    g.addEdge(1, 3, 2)
    g.addEdge(1, 4, 2)
    g.addEdge(2, 3, 8)
    g.addEdge(2, 4, 2)
    g.addEdge(3, 5, 2)
    g.addEdge(4, 5, 2)

    g.BellmanFord(0) # 0, 1, 2, 3, 4.

    print("\n--------------------\n")

    g = Graph(4)
    g.addEdge(1, 2,-2)
    g.addEdge(2, 3, 2)
    g.addEdge(0, 1, 6)
    g.addEdge(0, 2, 5)

    g.BellmanFord(0)

