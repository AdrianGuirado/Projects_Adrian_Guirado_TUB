
INF = float("Inf")

class Graph:

    def __init__(self, cap):
        self.N    = len(cap)
        self.cap  = cap
        self.parent  = [0] * self.N
        self.flow    = [[0 for _ in range(self.N)] for _ in range(self.N)]

    def thereIsPath(self, src, dst):

        visited   = [False] * self.N
        reachable = [False] * self.N

        u = src
        reachable[src] = True

        while u != self.N:

            next_node  = self.N
            visited[u] = True

            for v in range(self.N):

                if visited[v]:
                    continue

                # this is a di-graph, if there is flow: v -> u
                # the link is v -> u, and we can remove some flow:
                if self.flow[v][u] != 0:
                    reachable[v]   = True
                    self.parent[v] = u
                # if cap[u][v] > 0, the flow is u -> v,
                # let us check if flow < cap:
                elif self.flow[u][v] < self.cap[u][v]:
                    reachable[v]   = True
                    self.parent[v] = u

                # next_node will be the first non-visited reachable
                # node (following the node numbering order),
                # reachable due to this for-loop or a previous one:
                if next_node == self.N and reachable[v]:
                    next_node = v

                if v==dst and reachable[dst]:
                    return True

            u = next_node

        return False

    def getMaxFlow(self, src, dst):

        total_flow = 0

        while self.thereIsPath(src, dst):

            net_flow = INF
            v = dst
            while v != src:
                u = self.parent[v]
                net_flow = min(net_flow, self.flow[v][u] if (self.flow[v][u] != 0) else
                                     self.cap[u][v] - self.flow[u][v])
                v = self.parent[v]

            print("net_flow: ", net_flow, " path: ", self.path(src, dst))

            v = dst
            while v != src:
                u = self.parent[v]

                if self.flow[v][u] != 0:
                    self.flow[v][u] -= net_flow
                else:
                    self.flow[u][v] += net_flow

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

    # taken from ford_fulkerson graph example:

    s = 0
    d = 7

    cap = [[0, 10,  5, 15,  0,  0,  0,  0],
           [0,  0,  4,  0,  9, 15,  0,  0],
           [0,  0,  0,  4,  0,  8,  0,  0],
           [0,  0,  0,  0,  0,  0, 16,  0],
           [0,  0,  0,  0,  0, 15,  0, 10],
           [0,  0,  0,  0,  0,  0, 15, 10],
           [0,  0,  6,  0,  0,  0,  0, 10],
           [0,  0,  0,  0,  0,  0,  0,  0]]

    g = Graph(cap)

    ret = g.getMaxFlow(s, d)

    print("\ntotal flow: ", ret)
