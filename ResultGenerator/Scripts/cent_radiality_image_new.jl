using Graphs
using Gadfly
using Cairo
using GraphAnalysis

function plot_histogram(g::GenericGraph, path::String, title::String)
  result = centrality_radiality(g)
  array = [x for x=values(result)]
  array = sort(collect(array))

  max = maximum(array)
  min = minimum(array)
  rozdiel = max - min
  N = 50
  size = rozdiel / N

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
  
  p = Gadfly.plot(x=round(float(array[1:end]),5), Geom.histogram, Scale.x_discrete, Theme(
       panel_fill=color(colorant"white"),
       panel_opacity=0.1,
       panel_stroke=color(colorant"blue"), background_color=color(colorant"white")),
       Guide.ylabel("Count"),
      Guide.xlabel("Radiality centrality value"),
      Guide.title(title))
draw(PNG(path, 4inch, 2.67inch), p)
draw(PNG(path*"-HD", 12inch, 6inch), p)
end

g = GraphAnalysis.load_graph(ARGS[1])

plot_histogram(g, ARGS[2], "Radiality centrality Distribution")
println(ARGS[3]);
