using Graphs
using GraphAnalysis

g = load_graph(ARGS[1])
avg = sum([out_degree(v, g) for v = vertices(g)])/num_vertices(g)
println(round(avg,2))
