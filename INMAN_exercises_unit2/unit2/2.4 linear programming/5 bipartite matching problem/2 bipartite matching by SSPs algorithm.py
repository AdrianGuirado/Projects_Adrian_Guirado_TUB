INF = float("Inf")

class Graph:

    def __init__(self, cap, cost):
        self.N    = len(cap)
        self.cap  = cap
        self.cost = cost
        self.pi      = [0] * self.N
        self.parent  = [0] * self.N
        self.flow    = [[0 for _ in range(self.N)] for _ in range(self.N)]

    # this is the Dijkstra algorithm:
    # in self.parent we are registering the shortest path (cost_to[node])
    # to all nodes, including the path to the dst:
    def shortestPathToAllNodes(self, src, dst):

        visited = [False] * self.N
        cost_to = [INF]   * (self.N + 1)

        u = src

        cost_to[src] = 0

        while u != self.N:

            best_node  = self.N
            visited[u] = True

            for v in range(self.N):

                if visited[v]:
                    continue

                if self.flow[v][u] != 0:

                    cost_to_v = (cost_to[u] + self.pi[u] -
                                 self.pi[v] - self.cost[v][u])

                    if cost_to[v] > cost_to_v:
                        cost_to[v] = cost_to_v
                        self.parent[v] = u

                if self.flow[u][v] < self.cap[u][v]:

                    cost_to_v = (cost_to[u] + self.pi[u] -
                                 self.pi[v] + self.cost[u][v])

                    if cost_to[v] > cost_to_v:
                        cost_to[v] = cost_to_v
                        self.parent[v] = u

                if cost_to[v] < cost_to[best_node]:
                    best_node = v

            u = best_node

        # pi results in the accumulated cost to i of
        # each round, if reachable according to present flows.
        for i in range(self.N):
            self.pi[i] = min(self.pi[i] + cost_to[i], INF)

        return visited[dst]


    #getMaxFlow because we are not setting the total amount of data to be transmitted:
    def getMaxFlow(self, src, dst):

        total_flow = 0
        total_cost = 0

        #if the method does not return True for the sink, according to the available
        #capacities in all edges, we can not reach the sink, so we are done:
        while self.shortestPathToAllNodes(src, dst):

            # to obtain the net_flow of the shortest path to sink:
            net_flow = INF
            v = dst
            while v != src:
                u = self.parent[v]
                net_flow = min(net_flow, self.flow[v][u] if (self.flow[v][u] != 0) else
                                     self.cap[u][v] - self.flow[u][v])
                v = self.parent[v]

            print("net_flow: ", net_flow, " path: ", self.path(src, dst))

            # to update the flow through the shortest path to sink:
            v = dst
            while v != src:
                u = self.parent[v]
                if self.flow[v][u] != 0:
                    self.flow[v][u] -= net_flow
                    total_cost -= net_flow * self.cost[v][self.parent[v]]

                else:
                    self.flow[u][v] += net_flow
                    total_cost += net_flow * self.cost[u][v]

                v = self.parent[v]

            total_flow += net_flow

        return [total_flow, total_cost]

    def path(self, src, dst):
        path = [dst]
        node = dst
        while node != src:
            path.append(self.parent[node])
            node = self.parent[node]
        path.reverse()
        return path

if __name__ == "__main__":

    s = 0
    d = 9

    cap = [[0, 1, 1, 1, 1, 0, 0, 0, 0, 0],
           [0, 0, 0, 0, 0, 1, 1, 1, 1, 0],
           [0, 0, 0, 0, 0, 1, 1, 1, 1, 0],
           [0, 0, 0, 0, 0, 1, 1, 1, 1, 0],
           [0, 0, 0, 0, 0, 1, 1, 1, 1, 0],
           [0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
           [0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
           [0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
           [0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
           [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
           ]

    # here we can see that the paths are ordered by cost:
    cost = [[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0,11,12,18,40, 0],
            [0, 0, 0, 0, 0,14,15,13,22, 0],
            [0, 0, 0, 0, 0,11,17,19, 9, 0],
            [0, 0, 0, 0, 0,17,14,20,10, 0],
            [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
           ]

    g = Graph(cap, cost)

    ret = g.getMaxFlow(s, d)

    print("\ntotal flow: {}, total cost: {}".format(ret[0], ret[1]))

''' 
net_flow:  1  path:  [0, 3, 8, 9]              -> 3-8 (but later undone)
net_flow:  1  path:  [0, 1, 5, 9]              -> 1-5 (but later undone) 
net_flow:  1  path:  [0, 2, 7, 9]              -> 2-7
net_flow:  1  path:  [0, 4, 8, 3, 5, 1, 6, 9]  -> 4-8, 3-5, 1-6 

total flow: 4, total cost: 46
 '''
