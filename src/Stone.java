import java.io.Serializable;

public class Stone implements Serializable{

    //color can be white, black or blank
    private String color;

    public Stone(){
        this.color = "blank";
    }

    public Stone(String color){
        this.color = color;
    }

    public void setColor(String color){
        this.color = color;
    }

    public String getColor(){
        return color;
    }
}
