using Graphs
using GraphAnalysis

g = load_graph("C:\\skola\\bakalarka\\projects\\resultgenerator\\graphs\\ZelPage_SVK\\default_edges.txt")
result = centrality_eccentricity(g)
array = [x for x=values(result)]
array = sort(collect(array))

max = maximum(array)
min = minimum(array)
rozdiel = max - min
N = 10
size = rozdiel / 10

for i = [1:length(array)]
	c = min + size
	for part=[0:N-1]
		if (array[i] <= c )
			array[i] = c
			break
		end
		c = c + size
	end
end
