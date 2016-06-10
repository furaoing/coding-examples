package test3

import scala.util.control.Breaks._

case class AdjacentVertex(_id:Int, _edge_len:Int) {
    val id = _id
    val edge_len = _edge_len
  }

case class Vertex(_id:Int, _distance:Option[Int]) {
  val id = _id
  var adjacentVertex:List[AdjacentVertex] = List()
  var distance:Option[Int] = _distance

  def push(_v:AdjacentVertex) = {
    adjacentVertex = adjacentVertex :+ _v
  }
}

case class Graph() {
  var vertice:List[Vertex] = List(Vertex(0, None))
  // Placeholder

  var vertex_set:Set[Vertex] = Set()
  var spt_set:Set[Vertex] = Set()

  def push(_v:Vertex) = {
    vertice = vertice :+ _v
    vertex_set += _v
  }

  def pick_vertex():Vertex = {
    var tmp_vertex = vertex_set.head
    for (x <- vertex_set) {
        if( x.distance.getOrElse(99) < tmp_vertex.distance.getOrElse(99)){
          tmp_vertex = x
      }
    }
    tmp_vertex
  }
}


object Dijkstra extends App {
   val g = Graph()
   for (i <- Range(1,7)){
     if (i == 1){
       g.push(Vertex(i, Some(0)))
     }
     else {
       g.push(Vertex(i, None))
     }
   }

  g.vertice(1).push(AdjacentVertex(2, 7))
  g.vertice(1).push(AdjacentVertex(3, 9))
  g.vertice(1).push(AdjacentVertex(6, 14))
  g.vertice(2).push(AdjacentVertex(1, 7))
  g.vertice(2).push(AdjacentVertex(3, 10))
  g.vertice(2).push(AdjacentVertex(4, 15))
  g.vertice(3).push(AdjacentVertex(1, 9))
  g.vertice(3).push(AdjacentVertex(2, 10))
  g.vertice(3).push(AdjacentVertex(4, 11))
  g.vertice(3).push(AdjacentVertex(6, 2))
  g.vertice(4).push(AdjacentVertex(3, 11))
  g.vertice(4).push(AdjacentVertex(5, 6))
  g.vertice(4).push(AdjacentVertex(2, 15))

  g.vertice(5).push(AdjacentVertex(6, 9))
  g.vertice(5).push(AdjacentVertex(4, 6))

  g.vertice(6).push(AdjacentVertex(1, 14))
  g.vertice(6).push(AdjacentVertex(3, 2))
  g.vertice(6).push(AdjacentVertex(5, 9))

  while (g.vertex_set.nonEmpty) {
      val v = g.pick_vertex()
      g.spt_set += v
      g.vertex_set = g.vertex_set - v
      for (x <- v.adjacentVertex){
        if (!g.spt_set.contains(g.vertice(x.id))) {
          if ((v.distance.get + x.edge_len)<g.vertice(x.id).distance.getOrElse(999)) {
            g.vertice(x.id).distance = Some(v.distance.get + x.edge_len)
          }
        }
      }
    val c = 1
  }

  for(v <- g.vertice){
    println("ID: " + v.id + ";Distance: " + v.distance.getOrElse(0))
  }
}