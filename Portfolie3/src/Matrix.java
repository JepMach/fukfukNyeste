import java.util.*;

class Matrix extends Graph{
    ArrayList<ArrayList<Integer>> matrix=new ArrayList<>();
    ArrayList<Vertex> vertex=new ArrayList<>();
    HashMap<Vertex,Integer> vertexIndex=new HashMap<>();
    void insertEdge(String u,String v,int w){
        Vertex u1=vertex(u);
        Vertex v1=vertex(v);
        if(!vertexIndex.containsKey(u1)) {
            vertexIndex.put(u1, vertexIndex.size());
            vertex.add(u1);
        }
        if(!vertexIndex.containsKey(v1)) {
            vertexIndex.put(v1, vertexIndex.size());
            vertex.add(v1);
        }
        setMatrix(vertexIndex.get(u1),vertexIndex.get(v1),w);
    }
    void setMatrix(int i,int j,int w){
        while(matrix.size()<=i)matrix.add(new ArrayList<Integer>());
        ArrayList<Integer> row=matrix.get(i);
        while(row.size()<=26)row.add(null);
        row.set(j,w);
    }
    Collection<Edge> edges(){
        HashSet<Edge> edges=new HashSet<>();
        for(int i=0;i<matrix.size();i++){
            ArrayList<Integer> row=matrix.get(i);
            for(int j=0;j<row.size();j++){
                if(row.get(j)==null)continue;
                edges.add(new Edge(vertex.get(i), vertex.get(j), row.get(j)));
            }
        }
        return edges;
    }
    Collection<Edge> outEdge(Vertex v){
        ArrayList<Integer> row=matrix.get(vertexIndex.get(v));
        HashSet<Edge> edges=new HashSet<>();
        for(int j=0;j<row.size();j++){
            if(row.get(j)==null)continue;
            edges.add(new Edge(v,vertex.get(j),row.get(j)));
        }
        return edges;
    }


    void printGraph() {
        //System.out.println(vertexIndex);
        System.out.print("     ");
        for(int i=0;i<matrix.size();i++) {
            //String længde
            StringBuilder verLength = new StringBuilder(vertex.get(i).toString());
            switch (verLength.length()){
                case 2:
                    verLength.append("  ");
                    break;
                case 3:
                    verLength.append(" ");
                    break;
                case 4:
                    break;
                default:
                    verLength.setLength(4);
            }
            System.out.print(" " + verLength);
        }
        System.out.println();
        for(int i=0;i<matrix.size();i++){
            //String længde
            StringBuilder verLength = new StringBuilder(vertex.get(i).toString());
            switch (verLength.length()){
                case 2:
                    verLength.append("  ");
                    break;
                case 3:
                    verLength.append(" ");
                    break;
                case 4:
                    break;
                default:
                    verLength.setLength(4);
            }
            System.out.print(verLength);
            ArrayList<Integer> row=matrix.get(i);
            for(int j=0;j<row.size();j++){
                if(row.get(j)==null) {
                    System.out.print("    0");
                }
                else {
                    StringBuilder intLength = new StringBuilder(row.get(j).toString());
                    switch (intLength.length()){
                        case 1:
                            System.out.print("  ");
                            break;
                        case 2:
                            System.out.print(" ");
                            break;
                        default:
                            break;
                    }
                    System.out.print("  " + intLength);
                }
            }
            System.out.println();
        }
    }


}