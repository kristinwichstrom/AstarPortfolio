import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStarGraph {
    private ArrayList<Vertex> vertices;
    public AStarGraph() {
        vertices = new ArrayList<Vertex>();
    }
    public void addvertex(Vertex v) {
        vertices.add(v);
    }
    public void newconnection(Vertex v1, Vertex v2, Double dist) {
        v1.addOutEdge(v2,dist);
        v2.addOutEdge(v1,dist);
    }
    public boolean A_Star(Vertex start, Vertex destination)
    {   if (start==null || destination==null)
        return false;
        PriorityQueue<Vertex> Openlist = new PriorityQueue<Vertex>();
        ArrayList<Vertex> Closedlist = new ArrayList<Vertex>();
        Openlist.offer(start);
        Vertex Current;
        ArrayList<Vertex> CurrentNeighbors;
        Vertex Neighbor;
        //Initialize h with chosen heuristic
        for (int i=0; i<vertices.size();i++)
        {
            /*
            Implement the Manhattan.
            Maybe make the Euclidean as well, so you can select between thous two.
             */
            vertices.get(i).seth(Manhattan(vertices.get(i),destination));
        }
        start.setg(0.0);
        start.calculatef();
        //Start algorithm
        System.out.println("Start Algorithm");
        /*
        Implement the A* algorithm
        Start the While loop
         */
        while (!Openlist.isEmpty()){
            Current = Openlist.poll();
            if (Current == destination) {
                return true;
            }
            Closedlist.add(Current);
            for (int i=0; i<Current.getNeighbours().size(); i++) {
                Double tempGOfV = Current.getg() + Current.getNeighbourDistance().get(i);
                // tempV = neighbours to current
                Vertex tempV = Current.getNeighbours().get(i);
                if (tempGOfV < tempV.getg()) {
                    tempV.setPrev(Current);
                    tempV.setg(tempGOfV);
                    tempV.calculatef();

                    if (!Closedlist.contains(tempV) && !Openlist.contains(tempV)){
                        Openlist.add(tempV);
                    }
                }
            }
        }
        return false;
    }

    public Double Manhattan(Vertex currentVertex ,Vertex destination){
        int h = (currentVertex.getx() - destination.getx()) + (currentVertex.gety() - destination.gety());
        return Double.valueOf(h);
    }

    public Double Euclidean( Vertex from,Vertex to){
        //Implement this
        return 0.0;
    }
}
// Comparable because we want to find the f with the minimum value
class Vertex implements Comparable<Vertex>{
    private String id;
    private ArrayList<Vertex> Neighbours = new ArrayList<Vertex>();
    private ArrayList<Double> NeighbourDistance = new ArrayList<Double>();
    private Double f; //f = total estimated cost/how far it is from the origin to goal.
    private Double g; //g = the cost so far. The distance from origin to the current vertex. Always true.
    private Double h; //h = estimate form current vertex to the end. g+h=f
    private Integer x; //coordinates of our vertex - used to calculate h
    private Integer y; //coordinates of our vertex - used to calculate h

    private Vertex prev =null;
    // constructor for vertex.
    public Vertex(String id, int x_cor,int y_cor){
        this.id=id;
        this.x=x_cor;
        this.y = y_cor;
        f=Double.POSITIVE_INFINITY;
        g=Double.POSITIVE_INFINITY;
        h=0.0; // Will change when running A*, depending on which estimated function (Manhattan/Euclidean)
    }
    public void addOutEdge(Vertex toV, Double dist){
        Neighbours.add(toV);
        NeighbourDistance.add(dist);
    }
    public ArrayList<Vertex> getNeighbours(){
        return Neighbours;
    }
    public ArrayList<Double> getNeighbourDistance(){
        return NeighbourDistance;
    }
    public String getid(){ return id; };
    public Integer getx(){ return x; }
    public Integer gety(){return x; }
    public Double getf() { return f; }
    public void calculatef(){ f=g+h; }

    public Double getg() { return g; }

    public void setg(Double newg){ g=newg; }
    public Double geth(){ return h; }
    public void seth(Double estimate){ h=estimate; }
    public Vertex getPrev() { return prev; }
    public void setPrev(Vertex v){prev=v;}
    public void printVertex(){
        System.out.println(id + " g: "+g+ ", h: "+h+", f: "+f);
    }
    @Override
    public int compareTo(Vertex o) {
//Implement this
//
        return 0;
    }
}
