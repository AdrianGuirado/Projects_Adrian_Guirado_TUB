import random

class Graph():

	def __init__(self, vertices):
		self.V           = vertices
		self.graph       = [[0 for column in range(vertices)] for row in range(vertices)]
		self.master      = [[-1 for column in range(vertices)] for row in range(vertices)]
		self.num_edges   = 0
		self.edges_mastered = [0] * len(self.graph)

	def random_graph(self, prob):
		for u in range(len(self.graph)):
			for v in range(len(self.graph[0])):
				if random.random() < prob and u != v and self.graph[v][u] == 0:
					self.graph[u][v] = 1
					self.graph[v][u] = 1
					self.num_edges += 1

	def printResult(self):

		print("edges: ")
		for node in range(len(self.graph)):
			print(self.graph[node])

		print("\nResultant Vertex Cover solution:")
		for u in range(len(self.master)):
			for v in range(len(self.master[u])):
				if self.master[u][v] != -1:
					print(self.master[u][v], end =" ")
				else:
					print("-", end = " ")
			print(" ")
		print("mastered edges:", sum(self.edges_mastered), ", num edges:", self.num_edges, "(are bidirectional)")
		print("num_masters:", sum(i > 0 for i in self.edges_mastered))

	def vertex_cover(self):

		# to be completed

		pass


if __name__ == "__main__":

	g = Graph(10)
	g.random_graph(0.3)
	g.vertex_cover()
	g.printResult()

