import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) {

        Graph g=new Matrix();

        for(String s: loadStrings("SubCombi//combi.txt")){
            String[] a= s.split(" , ");
            g.insertEdge(a[0],a[1],Integer.parseInt(a[2]));
            g.insertEdge(a[1],a[0],Integer.parseInt(a[2]));
        }
        g.printGraph();

        HashSet<Vertex> visited=new HashSet<Vertex>();
        System.out.println(" ");
        System.out.println(" ");
        g.visitDepthFirst(g.vertex("DATA"),visited);
        System.out.println(" ");
        System.out.println("Antal moduler: "+visited.size());
        //System.out.println(g.vertices());
        //System.out.println(visited);
    }
    static ArrayList<String> loadStrings(String f){
        ArrayList<String> list=new ArrayList<>();
        try{
            BufferedReader in=new BufferedReader(new FileReader(f));
            while(true){
                String s=in.readLine();
                if(s==null)break;
                list.add(s);
            }
            in.close();
        }catch(IOException e){
            return null;
        }
        return list;
    }
}