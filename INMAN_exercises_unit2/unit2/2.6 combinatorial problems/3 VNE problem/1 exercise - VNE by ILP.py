
"""Solve a VNE_Problem using a MIP solver"""

from pulp import *

'''

3 types of Virtual Networks:

                       VNODE1_0 -  VNODE1_1  -  VNODE1_2
                  
                       VNODE2_3 -  VNODE2_4  -  VNODE2_6
                                \  VNODE2_5  /
                     
                               VNODE3_8 - VNODE3_10
                                /             \
                       VNODE3_7                  VNODE3_12
                                \             /
                               VNODE3_9 - VNODE3_11

problem input:                        
- a given amount of requests of the 3 types of VNs,
- the 3 VN-graphs with their VNODEs weights, 
- a network of routers and servers (to embed VNODEs and VLINKs).

'''


def main():

    # using a dictionary as the input data of the problem:

    data = {
            "num_reqs": 6,
            "vnet_type": [0, 0, 0, 1, 1, 2], # 3 types of VNs are defined

            "num_vnds": 13,
            "vnode_weight": [ 1, 1, 1,    1, 1, 1, 1,    1, 1, 1, 1, 1, 1],
            "vnode_in_vnet": [
                [1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1],
            ],
            "vnode_to_vnode": [
                [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
                [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
            ],

            "num_ndes": 8,
            "srv_node": [1, 1,  1, 0, 0,  1, 1,  1],  # {0,1,2,5,6,7}
            "srv_cap":  [5, 5,  5, 0, 0,  5, 5,  5],
            "node_to_node":
                [[0, 1, 1, 1, 0, 0, 0, 0],
                 [1, 0, 1, 0, 1, 1, 0, 0],
                 [1, 1, 0, 1, 0, 1, 1, 0],
                 [1, 0, 1, 0, 0, 0, 1, 0],
                 [0, 1, 0, 0, 0, 1, 0, 1],
                 [0, 1, 1, 0, 1, 0, 1, 1],
                 [0, 0, 1, 1, 0, 1, 0, 1],
                 [0, 0, 0, 0, 1, 1, 1, 0]],
            "phy_bw":
               [[  0, 10, 10, 10,  0,  0,  0,  0],
                [ 10,  0, 10,  0, 10, 10,  0,  0],
                [ 10, 10,  0, 10,  0, 10, 10,  0],
                [ 10,  0, 10,  0,  0,  0, 10,  0],
                [  0, 10,  0,  0,  0, 10,  0, 10],
                [  0, 10, 10,  0, 10,  0, 10, 10],
                [  0,  0, 10, 10,  0, 10,  0, 10],
                [  0,  0,  0,  0, 10, 10, 10,  0]]
            }

    REQs = range(data["num_reqs"])
    VNDs = range(data["num_vnds"])
    NDEs = range(data["num_ndes"])

    # min problem:
    problem = LpProblem("VNE_Problem", LpMinimize)

    # binary variables:
    var_vnode = LpVariable.dicts("vnode_fit", (REQs, VNDs, NDEs), 0, 1, LpInteger)
    var_vlink = LpVariable.dicts("vlink_fit", (REQs, VNDs, VNDs, NDEs, NDEs), 0, 1, LpInteger)

    # utility function: minimize the amount of p_links (u,v) used to map all v_links (k,k') of all reqs:
    # TO BE COMPLETED

    # constraints:

    # for each node, if server, all allocated vnodes must not exceed the srv capacity:
    # TO BE COMPLETED

    # for each p_link (u,v), all allocated v_links (k,k') must not exceed the p_link capacity:
    # TO BE COMPLETED

    # flow conservation:
    # for each req, for each vlink (k,k'), for each node_u not hosting vnode_k or vnode_k', output_flow_from_u - input_flow_to_u = 0
    # for each req, for each vlink (k,k'), for each node_u hosting vnode_k,                 output_flow_from_u - input_flow_to_u = 1
    # for each req, for each vlink (k,k'), for each node_u hosting vnode_k',                output_flow_from_u - input_flow_to_u = -1
    # TO BE COMPLETED

    # for each req, for each vnode, the vnode must be allocated in a single srv_node:
    # TO BE COMPLETED

    # for each req, for each node, if server, it can only contain one of the vnodes of the req:
    #                              if not server, it cannot contain any vnodes of the req:
    # TO BE COMPLETED

    problem.solve()

    # The status of the solution is printed to the screen:
    print(f"Status: {LpStatus[problem.status]}\n")

    # print out the VNODEs allocated at each server:
    total_vnodes = 0
    total_weight = 0
    for node in NDEs:
        if data["srv_node"][node] == 1:
            print(f"Content of srv_{node}:")
            srv_weight = 0
            for req in REQs:
                for vnode in VNDs:
                    if var_vnode[req][vnode][node].value() == 1.0:
                        print(f"req_{req}_vnode_{vnode}")
                        srv_weight += data["vnode_weight"][vnode]
                        total_vnodes += 1

            print(f"weight of srv_{node} = {srv_weight}\n")
            total_weight += srv_weight

    print(f"total accepted vnodes = {total_vnodes}")
    print(f"total weight of requests = {total_weight}\n")

    # print out the p_links used to map all v_links of all requests:
    total_vlinks = 0
    total_plinks = 0
    for req in REQs:
        print(f"\n\nreq_{req}")
        for vnode_k in VNDs:
            for vnode_k_ in VNDs:
                if data["vnode_in_vnet"][data["vnet_type"][req]][vnode_k] == 1 and \
                   data["vnode_in_vnet"][data["vnet_type"][req]][vnode_k_] == 1 and \
                   data["vnode_to_vnode"][vnode_k][vnode_k_]:
                    total_vlinks += 1
                    print(f"\nvnode_{vnode_k}_vnode_{vnode_k_} by edges: ", end="")
                    for node_u in NDEs:
                        for node_v in NDEs:
                            if var_vlink[req][vnode_k][vnode_k_][node_u][node_v].value() == 1.0:
                                print(f"{node_u}_{node_v}, ", end="")
                                total_plinks += 1

    print(f"\n\ntotal vlinks = {total_vlinks}")
    print(f"total used plinks = {total_plinks}\n")


if __name__ == "__main__":
    main()