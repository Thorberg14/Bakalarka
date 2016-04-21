using Graphs
using GraphAnalysis

g = load_graph(ARGS[1])

println(integer(graph_radius(g,components=true)[1])," km")
