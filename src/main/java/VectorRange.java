import java.io.Serializable;

public class VectorRange implements Serializable {
    private int startPoint;
    private int endPoint;
    private int total;
    private int order;
    public VectorRange(){
        startPoint=0;
        endPoint=0;
        total=0;
        order=3;
    }
    public VectorRange(int start,int end){
        setStartPoint(start);
        setEndPoint(end);
        setTotal();
    }

    public int getEndPoint() {
        return endPoint;
    }

    public int getStartPoint() {
        return startPoint;
    }

    public int getTotal(){
        return this.total;
    }
    public int getOrder(){return this.order;}
    public void setEndPoint(int endPoint) {
        this.endPoint = endPoint;
    }

    public void setStartPoint(int startPoint) {
        this.startPoint = startPoint;
    }
    public void setTotal(){
        this.total=(endPoint-startPoint)>0?endPoint-startPoint+1:0;

    }
    public void setOrder(int order){this.order=order;}
    public void setRange(int startPoint,int endPoint){
        setStartPoint(startPoint);
        setEndPoint(endPoint);
        setTotal();
    }

    public String toString(){
        return "Start:"+startPoint+" End:"+endPoint+" Total:"+total+" Order:"+order+"\n";
    }
}
