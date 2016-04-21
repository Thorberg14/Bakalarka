using Graphs
using GraphAnalysis

g = load_graph(ARGS[1])

println(minimum([out_degree(v, g) for v = vertices(g)]))
