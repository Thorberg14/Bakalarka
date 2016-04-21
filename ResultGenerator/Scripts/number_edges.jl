using Graphs
using GraphAnalysis

g = load_graph(ARGS[1])

println(num_edges(g))
