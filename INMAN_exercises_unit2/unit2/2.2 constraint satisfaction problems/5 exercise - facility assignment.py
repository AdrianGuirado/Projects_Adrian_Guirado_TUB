class Graph():

    def __init__(self, num_users, BS_cap):
        self.num_users = num_users
        self.num_BS    = len(BS_cap)
        self.graph     = [[0 for column in range(self.num_BS)] for row in range(num_users)]
        self.BS_cap    = BS_cap

    # check if user can use BS:
    def isSafe(self, user, BS, state):
        # to be completed
        pass

    # A recursive function to solve the assignment problem:
    # assignment is the assigned BS for each user,
    # state is the load of all BSs
    def successor_function(self, user, assignment, state):

        # to be completed

        pass

    def BS_assignment(self):

        load_state = [0] * len(self.BS_cap)
        assignment = [-1 for _ in range(self.num_users)]

        assignment_success = self.successor_function(user=0, assignment=assignment, state=load_state)

        if not assignment_success:
            print('no solution for current location of users')

        else:
            print("Solution exist with assigned BSs:")
            for BS in assignment:
                print(BS, end=' ')
            print("\nload: ", load_state)

if __name__ == "__main__":

    num_users = 10
    g = Graph(10, [4, 3, 4])
    g.graph = [[1, 1, 1],
               [1, 0, 1],
               [1, 1, 0],
               [1, 1, 0],
               [1, 1, 1],
               [1, 0, 1],
               [1, 0, 0],
               [0, 1, 1],
               [0, 1, 1],
               [0, 1, 0],
               ]

    g.BS_assignment()

