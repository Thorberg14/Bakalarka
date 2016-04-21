using Graphs
using k_cores
g = read_edges_from_file(ARGS[1])
println(maximum(values(core_number(g))))