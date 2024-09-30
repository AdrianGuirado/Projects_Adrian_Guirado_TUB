class Graph():

    def __init__(self, stages, cp, weight, benefit):
        self.STAGES    = stages
        self.CAPACITY  = cp
        self.size      = weight
        self.benefit   = benefit
        self.val_f     = [[0 for column in range(cp + 1)] for row in range(stages)]
        self.space_taken = [[0 for column in range(cp + 1)] for row in range(stages)]

        for i in range(stages):
            for j in range(len(self.val_f[0])):
                self.val_f[i][j] = -1

    def reward(self, item, taken_space):
        return self.benefit[item] * (taken_space / self.size[item])

    def possible_slots(self, item, avail_space):
        slots = []
        init_size = 0
        while init_size <= avail_space:
            slots.append(init_size)
            init_size += self.size[item]
        return slots

    def f(self, stage, avail_space):

        # to be completed

        pass


if __name__ == "__main__":

    stages   = 4
    capacity = 10
    sizes    = [4, 3, 5]
    benefit  = [11, 7, 12]

    g = Graph(stages, capacity, sizes, benefit)

    reward = g.f(0, capacity)

    print("\nresultant f(stage, avail_space):")
    for stage in range(g.STAGES-1):
        print("val_f[stage=",stage,"][avail_space]=", g.val_f[stage])

    stage       = 0
    avail_space = capacity

    print("\nmax reward: ", reward)
    for item in range(g.STAGES - 1):
        space_taken = g.space_taken[stage][avail_space]
        print("item:", item, ", num_items introduced:", space_taken / g.size[item])
        stage += 1
        avail_space -= space_taken

