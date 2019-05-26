import java.io.*;
import java.util.*;

class Driver{
    static Document[] result;
    static String[] query;
    final static int range = 1500; //determines the number of docs to pick from the file
    public static void main(String[] args)throws IOException{
        //int k = query[query.length - 1];
        query = args;
        // System.out.println(query.length-1);
        String path = "finefoods.txt";
        result = fetchDocs(path);
        //System.out.println(result[0].toString());
        setNormalizedScore();
        Document[] finalResult = findFirstK();
    }

    private static Document[] findFirstK(){
        int k = query.length-1;
        Document[] finalK = new Document[k];
        //asumming k will always be less than all document Size
        for(int i = 0; i < k; i++){
            finalK[i] = result[i];
        }
        // since query size is small can use bubble sort
        // System.out.println("First");
        bubbleSort(finalK, k);
        for(int n = k + 1; n < range; n++){
            if(finalK[0].normalizedScore < result[n].normalizedScore){
                finalK[0] = result[n];
                bubbleSort(finalK, k);
            }
        }
        //ans
        return finalK;
    }

    public static void bubbleSort(Document[] finalK, int k){
        boolean sorted = false;
        Document temp;
        while(!sorted){
            sorted = true;
            for(int i = 0; i < k-1; i++){
                if(finalK[i].normalizedScore > finalK[i+1].normalizedScore){
                    temp = finalK[i];
                    finalK[i] = finalK[i+1];
                    finalK[i+1] = temp;
                    sorted = false;
                }
            }
        }
        // System.out.println("Sorting done");
    }

    private static void setNormalizedScore(){
        for(int i = 0; i < range; i++){
            int normValue = 0;
            int denom = query.length-1;
            HashMap temp = result[i].map;
            //since reviews are only in summary and text we don't need others
            String checkSummary = temp.get("summary").toString();
            String checkText = temp.get("text").toString();
            for(int z = 0; z < query.length-1; z++){
                if(checkSummary.contains(query[z])){
                    normValue++;
                }
                if(checkText.contains(query[z])){
                    normValue++;
                }
            }
            // System.out.println(normValue + " and " + denom);
            result[i].setnormalizedScore(normValue/(float)denom);
            // System.out.println(result[i].normalizedScore);
        }
    }

    private static Document[] fetchDocs(String path) throws IOException{
        String[] inputs = new String[8];
        Document[] all = new Document[range];
        Document doc = new Document();
        int i = 0;
        int j = 0;
        FileInputStream inputStream = null;
        Scanner sc = null;
        // the following approach...
        try{
            inputStream = new FileInputStream(path);
            sc = new Scanner(inputStream, "UTF-8");
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                if(line.length() == 0 || line == null){
                    continue;
                }
                //System.out.println("Size: " + line.length());
                StringTokenizer st = new StringTokenizer(line, ":");
                String key = st.nextToken();
                String value = st.nextToken();
                doc.setMap(i, value);
                i++;
                if(i == 8){
                    i = 0;
                    // System.out.println(doc.printDoc());
                    all[j] = doc;
                    doc = new Document();
                    j++;
                }
                //System.out.println("J: " + j);
                if(j == range){
                    break;
                }
                // System.out.println(line);
                // System.out.println(key + ", " + value);
                //doc.set()
                //System.out.println("set\n\n");
            }
            if(sc.ioException() != null){
                throw sc.ioException();
            }
        }catch(IOException e){
            System.out.println(e);
        } finally {
            if(inputStream != null){
                inputStream.close();
            }
            if(sc != null){
                sc.close();
            }
        }
        return all;
        // System.out.println(all.length());
    }


}
