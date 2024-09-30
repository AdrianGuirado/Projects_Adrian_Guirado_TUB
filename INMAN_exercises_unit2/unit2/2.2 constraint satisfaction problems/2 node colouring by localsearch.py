import random
import numpy as np
import copy

class Graph():

    def __init__(self, path):
        self.nodes_id, self.graph = self.get_graph(path)
        self.num_colours = 0

    def get_graph(self, path):

        neighbours = {}
        f = open(path, 'r')
        f.readline()
        data = f.readlines()
        for line in data:
            line = line.split()
            node = int(line.pop(0)[:-1])
            line = list(map(int, line))
            neighbours[node] = line

        graph = np.zeros((len(neighbours), len(neighbours)))
        for node, node_neighbours in neighbours.items():
            for neighbour in node_neighbours:
                graph[node, neighbour] = 1

        return list(neighbours.keys()), graph

    def get_random_solution(self):
        nodes_of_same_colour = {}
        num_nodes = len(self.nodes_id)

        for c in range(self.num_colours):
            nodes_of_same_colour.setdefault(c, [])

        colour_of_node = [random.randrange(0, self.num_colours) for i in range(num_nodes)]

        for i in range(num_nodes):
            nodes_of_same_colour[colour_of_node[i]].append(self.nodes_id[i])

        return nodes_of_same_colour

    def get_alternative_solutions(self, color_solution, conflict_pairs_per_colour):

        alternative_solutions = []

        for colour, list_of_pairs in conflict_pairs_per_colour.items():

            for pair in list_of_pairs:

                other_colours_list = [c for c in range(self.num_colours)]
                other_colours_list.remove(colour)

                for another_colour in other_colours_list:

                    for j in range(2):

                        alternative_solution = copy.deepcopy(color_solution)
                        alternative_solution[colour].remove(pair[j])
                        alternative_solution[another_colour].append(pair[j])
                        alternative_solutions.append(alternative_solution)

        return alternative_solutions


    def get_conflicts_of_one_colour(self, nodes_of_a_given_colour):
        subset_num = len(nodes_of_a_given_colour)
        conflicting_pairs = []
        count = 0

        #check each node with the others of the subset, except the last one:
        for i in range(subset_num - 1):
            #the others to be checked are located on my right:
            for j in range(i + 1, subset_num):

                if self.graph[nodes_of_a_given_colour[i], nodes_of_a_given_colour[j]] == 1:
                    conflicting_pairs.append([nodes_of_a_given_colour[i], nodes_of_a_given_colour[j]])
                    count += 1

        return count, conflicting_pairs


    def get_all_conflict_pairs(self, nodes_of_same_colour):
        conflicting_pairs_per_colour = {}
        total_conflicts = 0

        for c in range(self.num_colours):
            c_conflicts, conflicting_pairs_per_colour[c] = self.get_conflicts_of_one_colour(list(nodes_of_same_colour[c]))
            total_conflicts += c_conflicts

        return total_conflicts, conflicting_pairs_per_colour


    def iteration(self, nodes_of_same_colour):

        current_num_conflicts, current_conflict_pairs_per_colour = self.get_all_conflict_pairs(nodes_of_same_colour)

        best_num_conflicts = current_num_conflicts
        best_solution = nodes_of_same_colour

        print("Num conflicts ： ", current_num_conflicts)
        print("The current solution ： ", nodes_of_same_colour)
        print("Conflicting pairs : ", current_conflict_pairs_per_colour)

        alternative_solutions = self.get_alternative_solutions(nodes_of_same_colour, current_conflict_pairs_per_colour)

        for alternative_sol in alternative_solutions:

            new_num_conflicts, new_conflict_pairs = self.get_all_conflict_pairs(alternative_sol)
            if new_num_conflicts < best_num_conflicts:
                best_num_conflicts = new_num_conflicts
                best_solution = alternative_sol

        return best_num_conflicts, best_solution


    def graphColouring(self, num_colours):

        self.num_colours = num_colours

        iter_num = 200
        min_num_conflicts = len(self.nodes_id)
        nodes_of_same_colour = self.get_random_solution()

        print("random color solution: ", nodes_of_same_colour)

        for i in range(iter_num):

            print("\nIteration: ", i + 1)

            new_num_conflicts, new_color_solution = self.iteration(nodes_of_same_colour)

            if new_num_conflicts == 0:
                print("\nOptimal solution!!! ：", nodes_of_same_colour)
                return True

            if new_num_conflicts < min_num_conflicts:
                min_num_conflicts = new_num_conflicts
                nodes_of_same_colour = new_color_solution
            else:
                nodes_of_same_colour = self.get_random_solution()

        return False


if __name__ == '__main__':

    g = Graph(r'2_node_colouring_localsearch.txt')
    num_colours = 3
    g.graphColouring(num_colours)