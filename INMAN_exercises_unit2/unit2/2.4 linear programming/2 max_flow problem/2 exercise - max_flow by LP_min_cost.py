# adding a fictitious link from sink to source with cost -1,
# all other links of cost 0, we want to minimize the overall cost,
# equivalent to maximize the data transmitted through the sink-to-source link,
# regarding the constraints, at all nodes the input flow equals the output flow.
from pulp import *

# to be completed