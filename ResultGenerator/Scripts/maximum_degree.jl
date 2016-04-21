using Graphs
using GraphAnalysis

g = load_graph(ARGS[1])

println(maximum([out_degree(v, g) for v = vertices(g)]))
