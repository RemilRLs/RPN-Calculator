public class ObjEmp extends PileRPL{
    int x;

    /**
     * Constructor of the ObjEmp
     * @param x Value
     */
    public ObjEmp(int x){
        this.x = x;
    }

    /**
     * Method to get the value of x
     * @return x
     */
    public String toString(){
        return Integer.toString(x);
    }

    /**
     * Method to add two ObjEmp
     * @param second Second ObjEmp
     * @return New ObjEmp with the sum of the two ObjEmp
     */
    public ObjEmp add(ObjEmp second) {
        return new ObjEmp(this.x + second.x);
    }

    /**
     * Method to substract two ObjEmp
     * @param second Second ObjEmp
     * @return New ObjEmp with the substraction of the two ObjEmp
     */
    public ObjEmp sub(ObjEmp second){
        return new ObjEmp(this.x - second.x);
    }


    /**
     * Method to multiply two ObjEmp
     * @param second Second ObjEmp
     * @return New ObjEmp with the multiplication of the two ObjEmp
     */
    public ObjEmp mul(ObjEmp second){
        return new ObjEmp(this.x * second.x);
    }

    /**
     * Method to divide two ObjEmp
     * @param second Second ObjEmp
     * @return New ObjEmp with the division of the two ObjEmp
     */
    public ObjEmp div(ObjEmp second){
        if(second.x == 0){
            System.err.println("[X] - Cannot divide by 0");
            return null;
        }

        return new ObjEmp(this.x / second.x);

    }


}
