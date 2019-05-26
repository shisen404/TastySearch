import java.util.*;
class Document{
    float normalizedScore;
    HashMap<String, String> map = new HashMap<>();
    static String[] keys = {"productID", "userID", "name", "helpful", "score", "time", "summary", "text"};

    // public Document(){
    //     map = null;
    //     normalizedScore = 0;
    // }
    public void setMap(int key, String value){
        map.put(keys[key], value);
        this.normalizedScore = 0;
    }

    public void printDoc(){
        for(int i = 0; i < 8; i++){
            System.out.println(keys[i] + "-->" + this.map.get(keys[i]));
        }
        // return 1;
    }

    public void setnormalizedScore(float normalizedScore){
        this.normalizedScore = normalizedScore;
    }
}
