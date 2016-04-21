using Graphs
using Gadfly
using Cairo
using GraphAnalysis

function plot_histogram(g::GenericGraph, path::String, title::String)
  result = centrality_closeness(g)
  array = [x for x=values(result)]
  array = sort(collect(array))
  map!(x -> round(x,3)*1000,array)
  p = Gadfly.plot(x=int(array[1:end]), Geom.histogram, Scale.x_discrete, Theme(
       panel_fill=color(colorant"white"),
       panel_opacity=0.1,
       panel_stroke=color(colorant"blue"), background_color=color(colorant"white")),
       Guide.ylabel("Count"),
      Guide.xlabel("Closeness centrality value (*10^3)"),
      Guide.title(title))
draw(PNG(path, 4inch, 2.67inch), p)
end

g = GraphAnalysis.load_graph(ARGS[1])

plot_histogram(g, ARGS[2], "Closeness centrality Distribution")
println(ARGS[3]);