using Graphs
using GraphAnalysis

g = load_graph(ARGS[1])
for e in edges(g) 
	e.attributes["weight"]=1
end
println(integer(graph_diameter(g,components=true)[1]))
