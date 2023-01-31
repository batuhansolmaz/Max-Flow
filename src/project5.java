import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.print.DocFlavor.STRING;

public class Project5 {
    public static void main(String[] args) throws FileNotFoundException {

        File myObj = new File(args[0]);
        Scanner myReader = new Scanner(myObj);
        int N = myReader.nextInt();
        System.setOut(new java.io.PrintStream(args[1]));
        myReader.nextLine();
        String[] names = myReader.nextLine().split(" ");
        int size = N+8;
        int[][] graph = new int[size][size];
        HashMap <String, Integer> Vertice_map = new HashMap<String, Integer>();
        Vertice_map.put("s", 0);


        Vertice_map.put("KL", size-1);



        int number =1;
        int count = 0;
        while(myReader.hasNextLine()){
            String data = myReader.nextLine();
            String[] data_array = data.split(" ");

            for (int i = 0; i < data_array.length; i++) {
                if (i == 0) {
                    if(! Vertice_map.containsKey(data_array[i]) ) {
                        if (count<6){
                            graph[0][number] = Integer.parseInt(names[count]);
                            count++;
                            Vertice_map.put(data_array[i], number);
                            number++;
                        }
                        else{
                            Vertice_map.put(data_array[i], number);
                            number++;
                        }

                    }
                }

                else {

                    if(! Vertice_map.containsKey(data_array[i]) ) {
                        Vertice_map.put(data_array[i], number);
                        int temp = Vertice_map.get(data_array[0]);
                        graph[temp][number] = Integer.parseInt(data_array[i+1]);
                        number++;

                    }
                    else {
                        int temp = Vertice_map.get(data_array[0]);
                        graph[temp][Vertice_map.get(data_array[i])] = Integer.parseInt(data_array[i+1]);
                    }

                    i++;
                }
            }

        }

        System.out.println(MaxFlow(graph, size));


    }
    public static int MaxFlow(int[][] graph, int size) {
        
        int s = 0;
        int t = size - 1;
        int maxFlow = 0;
        int[][] residualGraph = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                residualGraph[i][j] = graph[i][j];
            }
        }
            int[] parent = new int[size];
          
            while (true) {
                
                Arrays.fill(parent, -1);
                Queue<Integer> q = new LinkedList<>();
                q.add(s);
                parent[s] = s;
                while (!q.isEmpty()) {
                    int u = q.poll();
                    int v = 0;
                    while (v < size) {
                        if (parent[v] == -1 && residualGraph[u][v] > 0) {
                            q.add(v);
                            parent[v] = u;
                            if (v == t) {
                                break;
                            }
                        }
                        v++;
                    }
                }
                
                if (parent[t] == -1) break;
          
                
                int minResidualCapacity = Integer.MAX_VALUE;
                int v = t;
                while (v != s) {
                    int u = parent[v];
                    minResidualCapacity = Math.min(minResidualCapacity, residualGraph[u][v]);
                    v = parent[v];
                }
          
                
                v = t;
                while (v != s) {
                    int u = parent[v];
                    residualGraph[u][v] -= minResidualCapacity;
                    residualGraph[v][u] += minResidualCapacity;
                    v = parent[v];
                }
                maxFlow += minResidualCapacity;
            }
          
            return maxFlow;
        }
    
          

}