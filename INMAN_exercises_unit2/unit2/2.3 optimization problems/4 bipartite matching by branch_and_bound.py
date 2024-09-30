import numpy as np
import copy
import math

class Graph():

    def __init__(self, graph):

        self.graph       = graph
        self.num_agents  = len(graph)
        self.num_tasks   = len(graph[0])
        self.state       = 0
        self.hist_assign = {}
        self.hist_LB_UB  = {}
        self.min_UB      = 0

        self.costs_per_task = {}
        for task in range(self.num_tasks):
            self.costs_per_task.setdefault(task, [])
            for agent in range(self.num_agents):
                self.costs_per_task[task].append(self.graph[agent][task])
                self.min_UB += self.graph[agent][task]

    def LB_UB(self, tasks_assigned_to_agents):

        LB = 0
        UB = 0
        remaining_costs_per_task = copy.deepcopy(self.costs_per_task)
        for agent, task in tasks_assigned_to_agents.items():
            LB += self.graph[agent][task]
            UB += self.graph[agent][task]
            remaining_costs_per_task.pop(task)
            for remain_task in remaining_costs_per_task:
                remaining_costs_per_task.get(remain_task).remove(self.graph[agent][remain_task])

        for task in remaining_costs_per_task:
            LB += min(remaining_costs_per_task[task])
            UB += max(remaining_costs_per_task[task])

        if LB <= self.min_UB:
            self.hist_assign[self.state] = copy.deepcopy(tasks_assigned_to_agents)
            self.hist_LB_UB[self.state] = [LB, UB]
            self.state += 1
        else:
            print("discarded tasks_assigned_to_agents: ", tasks_assigned_to_agents, " LB = ", LB, " > ", "min_UB = ", self.min_UB)

        self.min_UB = min(self.min_UB, UB)

    def pendingTasks(self, tasks_assigned_to_agents):
        pending_tasks = [task for task in range(self.num_tasks)]
        for task in list(tasks_assigned_to_agents.values()):
            pending_tasks.remove(task)
        return pending_tasks

    def setOfCases(self, checked_agents):
        keys = copy.deepcopy(list(self.hist_assign.keys()))
        depth = sum(checked_agents)

        for key, tasks_assigned_to_agents in self.hist_assign.items():
            if len(tasks_assigned_to_agents)<depth:
                keys.remove(key)

        return keys

    def minLB(self):
        min_val = self.hist_LB_UB.get(0)[1]
        min_key = 0
        for key, tasks_assigned_to_agents in self.hist_assign.items():
            if len(tasks_assigned_to_agents)<self.num_agents:
                continue
            [LB, UB] = self.hist_LB_UB.get(key)
            if LB < min_val:
                min_val = LB
                min_key = key
        return min_key

    def getMatching(self):

        tasks_assigned_to_agents = {}
        pending_tasks = self.pendingTasks(tasks_assigned_to_agents)
        checked_agents = [False for agent in range(self.num_agents)]

        self.LB_UB(tasks_assigned_to_agents)

        for agent in range(self.num_agents):

            for case in self.setOfCases(checked_agents):

                tasks_assigned_to_agents = copy.deepcopy(self.hist_assign.get(case))
                pending_tasks = self.pendingTasks(tasks_assigned_to_agents)

                for task in pending_tasks:

                    tasks_assigned_to_agents[agent] = task
                    self.LB_UB(tasks_assigned_to_agents)

            checked_agents[agent] = True

        best_case = self.minLB()
        cost      = self.hist_LB_UB.get(best_case)
        asignment = self.hist_assign.get(best_case)

        print("\nMin cost: ", cost, "; asignment: ", asignment, " ; num_states: ", self.state)


if __name__ == "__main__":

    bpGraph = [[11, 12, 18, 40],
               [14, 15, 13, 22],
               [11, 17, 19, 23],
               [17, 14, 20, 28]]

    g = Graph(bpGraph)

    g.getMatching()


