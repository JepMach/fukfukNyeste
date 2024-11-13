
class Edge{
    Vertex from,to;
    int weight;
    Edge(Vertex from,Vertex to,int w){this.from=from; this.to=to; weight=w;}

    public String toString(){return from.name+" - "+weight+" -> "+to.name; }
}