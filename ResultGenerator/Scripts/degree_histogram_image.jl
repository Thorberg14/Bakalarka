using Graphs
using Gadfly
using Cairo
using GraphAnalysis

function plot_histogram(g::GenericGraph, path::String, title::String)
  result = degree_histogram(g)
  result = sort(collect(result))
  array = Int64[]
  for (key, value) in result
    append!(array, [key for i=1:value])
  end
  p = Gadfly.plot(x=int(array[1:end]), Geom.histogram, Scale.x_discrete, Theme(
       panel_fill=color(colorant"white"),
       panel_opacity=0.1,
       panel_stroke=color(colorant"blue"), background_color=color(colorant"white")),
       Guide.ylabel("Count"),
      Guide.xlabel("Node Degree"),
      Guide.title(title))
draw(PNG(path, 4inch, 2.67inch), p)
end

g = GraphAnalysis.load_graph(ARGS[1])

plot_histogram(g, ARGS[2], "Node Degree Distribution")
println(ARGS[3]);
