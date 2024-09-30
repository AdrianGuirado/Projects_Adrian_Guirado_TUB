class Graph():

    def __init__(self, stages, rsc):
        self.STAGES    = stages
        self.RESOURCES = rsc
        self.val_f     = [[0 for column in range(rsc + 1)] for row in range(stages)]
        self.rsc_taken = [[0 for column in range(rsc + 1)] for row in range(stages)]

        for i in range(stages):
            for j in range(len(self.val_f[0])):
                self.val_f[i][j] = -1

    def reward(self, activity, rsc):
        if rsc > 0:
            if activity == 0:
                return 7 * rsc + 2
            if activity == 1:
                return 3 * rsc + 7
            if activity == 2:
                return 4 * rsc + 5
        else:
            return 0

    def f(self, stage, avail_rsc):

        # to be completed

        pass


if __name__ == "__main__":

    stages    = 4
    resources = 6

    g = Graph(stages, resources)

    reward = g.f(0, 6)

    print("\nresultant f(stage, avail_rsc):")
    for stage in range(g.STAGES-1):
        print(g.val_f[stage])

    stage     = 0
    avail_rsc = 6

    print("\nmax reward: ", reward)
    for activity in range(g.STAGES-1):
        rsc_taken = g.rsc_taken[stage][avail_rsc]
        print("activity:", activity, ", rsc_taken:", rsc_taken)
        stage += 1
        avail_rsc -= rsc_taken

