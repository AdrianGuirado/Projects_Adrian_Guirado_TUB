# Edmonds-Karp implementation (BFS) of Ford–Fulkerson, algorithm with complexity: O(V*E^2)
# based on using always the successive shortest paths of the residual net from src to dst,
# and possible thanks to the optimal substructure of the shortest-path problem.

INF = float("Inf")

class Graph:

    def __init__(self, graph):
        self.adj = graph
        self.N = len(graph)
        self.parent = [-1] * (self.N)

    # only looking there is path from src to any node,
    # including dst to stop the search, but also
    # obtaining the shortest-path of the residual net
    # each time, because the nodes are queued or visited by BFS,
    # by number of hops from source:
    # the path is implicitly the shortest path in number of hops
    def thereIsPath(self, src, dst):

        visited = [False] * (self.N)
        reachable = []

        reachable.append(src)
        visited[src] = True

        while reachable:

            u = reachable.pop(0)

            for v, cap in enumerate(self.adj[u]):
                if not visited[v] and cap > 0:
                    reachable.append(v)
                    visited[v] = True
                    self.parent[v] = u
                    if v == dst:
                        return True

        return False

    def getMaxFlow(self, src, dst):

        total_flow = 0

        while self.thereIsPath(src, dst):

            net_flow = INF
            v = dst
            while v != src:
                u = self.parent[v]
                net_flow = min(net_flow, self.adj[u][v])
                v = self.parent[v]

            print("net_flow: ", net_flow, " path: ", self.path(src, dst))

            v = dst
            while v != src:
                u = self.parent[v]
                self.adj[u][v] -= net_flow
                v = self.parent[v]

            total_flow += net_flow

        return total_flow

    def path(self, src, dst):
        path = [dst]
        node = dst
        while node != src:
            path.append(self.parent[node])
            node = self.parent[node]
        path.reverse()
        return path


if __name__ == "__main__":

    capacity = [[0,10, 5,15, 0, 0, 0, 0],
                [0, 0, 4, 0, 9,15, 0, 0],
                [0, 0, 0, 4, 0, 8, 0, 0],
                [0, 0, 0, 0, 0, 0,16, 0],
                [0, 0, 0, 0, 0,15, 0,10],
                [0, 0, 0, 0, 0, 0,15,10],
                [0, 0, 6, 0, 0, 0, 0,10],
                [0, 0, 0, 0, 0, 0, 0, 0]]

    g = Graph(capacity)

    source = 0
    sink = 7

    ret = g.getMaxFlow(source, sink)

    print("\ntotal flow: ", ret)
