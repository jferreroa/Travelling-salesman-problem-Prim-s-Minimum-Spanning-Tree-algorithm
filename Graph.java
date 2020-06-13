import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Objects;
import	java.util.Vector;
public class Graph {
    private int numVertex;
    private Vector<Double>edges;
    public static double infinity = Double.POSITIVE_INFINITY;

    public Double getPos(int row,  int col){
        int index = row * numVertex + col;
        return edges.get(index);
    }
     public void set(int row,int col, Double value){
        int index = row * numVertex + col;
        edges.add(index,value);
    }
    public Boolean isEqual(@NotNull Vector<Double> a, @NotNull Vector<Double> b){
        boolean res = true;
        if(b.size() != a.size()) res = false;
        else{
            Collections.sort(a);
            Collections.sort(b);

            if(!a.equals(b))
                res = false;
        }
        return res;
    }
    Boolean belongsToTheSameSet(@NotNull Vector<Double> a, Double b){
        boolean res = false;
        for (Double aDouble : a) {
            if (aDouble.equals(b)) {
                res = true;
                break;
            }
        }
        return res;
    }
    public String printSmallGraph(String s){
        StringBuilder ss = new StringBuilder();
        ss.append(s).append("\n");
        for (int i = 0; i < numVertex; i++) {
            ss.append("\n["); ss.append(i+1); ss.append("] ");
            for (int j = 0; j < numVertex; j++)
                if (getPos(i,j) == infinity)
                    ss.append(" -- ");
                else {
                    if (getPos(i,j) < 10) {
                        ss.append("  ");
                        ss.append(getPos(i, j));
                        ss.append(" ");
                    }else{
                        ss.append(" ");
                        ss.append(getPos(i, j));
                        ss.append(" ");
                    }
                }
        }
        ss.append("\n");
        return ss.toString();

    }

    public String printLargeGraph() {
         StringBuilder ss = new StringBuilder();
        ss.append(ss).append(": ").append("\n");
        for(int i  =0; i< numVertex; i++){
            ss.append("\n[").append(i+1).append("]").append((i< 9)?" ":"");
            for(int j = 0; j < numVertex; j++){
                if(getPos(i,j) == infinity){
                    ss.append("----");
                }else{
                    if(getPos(i,j) < 10){
                        ss.append("   ").append(getPos(i,j).intValue()).append(" ");
                    }else{
                        if(getPos(i,j) < 100){
                            ss.append("  ").append(getPos(i,j).intValue()).append(" ");
                        }else{
                            if(getPos(i,j) < 1000){
                                ss.append(" ").append(getPos(i,j).intValue()).append(" ");
                            }else{
                                ss.append(getPos(i,j).intValue()).append(" ");
                            }
                        }
                    }
                }
            }
        }
        return ss.toString();
    }
    public Double cost(int i, int j){
        return getPos(i,j);
    }
    public void insert(int i, int j, Double cost){
        set(i, j, cost);
    }
    public int totalVertex(){
        return numVertex;
    }

    public Graph(int v,Vector<Double> vec){
        numVertex=v;
        edges= new Vector<>(numVertex * numVertex);
        for(int i=0;i<numVertex*numVertex;i++){

            edges.add(vec.elementAt(i));

        }
    }

    public Graph(int numVertex){
        this.numVertex = numVertex;
        edges = new Vector<>(numVertex * numVertex, 0);
        for(int i = 0; i < numVertex; i++){
            for(int j = 0; j < numVertex; j++){
                set(i,j, infinity);
            }
        }
    }
    public Vector<Double>SetSubtraction(Vector<Double>a, Vector<Double> b){
       Vector<Double> res = new Vector<>();
        Collections.sort(a);
        Collections.sort(b);
        for(int i =0 ; i<a.size(); i++){
            for (Double aDouble : b) {
                if (!Objects.equals(a.get(i), aDouble)) {
                    res.add(a.elementAt(i));
                }
            }
        }
        return res;
    }


    public void erase1(Vector<Double> a,int num){
        for(int i  =0; i< a.size(); i++){
            if(a.elementAt(i) == num){
                a.removeElementAt(i);
            }
        }
    }

    public Vector<Edge> tspPrim(@NotNull Graph g, int startingPosition){
        Vector<Double> set_V = new Vector<>(g.totalVertex());
        int i =0;
        while (i < g.totalVertex()) {
            set_V.add((double)i);
            i++;
        }

        Vector<Double> set_T = new Vector<>();
        set_T.add((double)startingPosition);

        Vector<Double> set_W = SetSubtraction(set_V,set_T);

        Vector<Double>  vertexDegreeLessThan2 = new Vector<>(g.totalVertex());
        int k =0;
        while (k < g.totalVertex()) {
            vertexDegreeLessThan2.add((double)0);
            k++;
        }

        Vector<Edge> resultTSP = new Vector<>();
        int u = startingPosition;
        int w = 0;
        while (!isEqual(set_T,set_V)) {
            Double minimumCost = infinity;

            for(int j=0;j<g.totalVertex();j++){
                if((getPos(u, j) < minimumCost) && (u != j) &&
                        belongsToTheSameSet(set_W, (double) j) &&
                        belongsToTheSameSet(set_T, (double) u) &&
                        (vertexDegreeLessThan2.elementAt(u) < 2) &&
                        (vertexDegreeLessThan2.elementAt(j) < 2)){

                    minimumCost=g.cost(u,j);
                    w=j;

                }
            }
            vertexDegreeLessThan2.set(u,vertexDegreeLessThan2.elementAt(u)+1);
            vertexDegreeLessThan2.set(w,vertexDegreeLessThan2.elementAt(w)+1);
            resultTSP.add(new Edge(u,w,g.cost(u,w)));

            set_T.add((double)w);
            erase1(set_W,w);

            u = w;
        }

        Integer  IndexU,IndexW;
        StringBuilder bothNum = new StringBuilder();

        for(int a =vertexDegreeLessThan2.size()-1; a>=0; a--){
            if(vertexDegreeLessThan2.elementAt(a)<2){
                bothNum.append(a + " ");
                vertexDegreeLessThan2.set(a,vertexDegreeLessThan2.elementAt(a)+1);
            }
        }
         IndexU = Integer.parseInt(String.valueOf((bothNum.charAt(0))));
         IndexW = Integer.parseInt(String.valueOf((bothNum.charAt(2))));

        resultTSP.add(new Edge(IndexU, IndexW, g.cost(IndexU,IndexW)));
        return resultTSP;

    }

}
