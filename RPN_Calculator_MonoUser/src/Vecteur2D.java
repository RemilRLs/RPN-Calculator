public class Vecteur2D {
    private int x;
    private int y;

    /**
     * Constructor of the class Vecteur2D
     * @param x x Value
     * @param y y Value
     */
    public Vecteur2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Method to get the value of x
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * Method to get the value of y
     * @return y
     */
    public double getY() {
        return y;
    }

    /**
     * Method to add two Vecteur2D
     * @param v Second Vecteur2D
     * @return New Vecteur2D with the sum of the two Vecteur2D
     */
    public Vecteur2D add(Vecteur2D v) {
        return new Vecteur2D(this.x + v.x, this.y + v.y);
    }

    /**
     * Method to add a Vecteur2D and an ObjEmp
     * @param obj Second ObjEmp
     * @return New Vecteur2D with result of the sym of the Vecteur2D and the ObjEmp
     */
    public Vecteur2D add(ObjEmp obj) {
        // I need that because if I want to add a uni dimension and a vecteur (same for the others)
        return new Vecteur2D(this.x + obj.x, this.y);
    }

    /**
     * Method to substract two Vecteur2D
     * @param v Second Vecteur2D
     * @return New Vecteur2D with the substraction of the two Vecteur2D
     */
    public Vecteur2D sub(Vecteur2D v) {
        return new Vecteur2D(this.x - v.x, this.y - v.y);
    }

    /**
     * Method to sub a Vecteur2D and an ObjEmp
     * @param obj Second ObjEmp
     * @return New Vecteur2D with result of the sub of the Vecteur2D and the ObjEmp
     */
    public Vecteur2D sub(ObjEmp obj) {
        return new Vecteur2D(this.x - obj.x, this.y);
    }

    /**
     * Method to multiply two Vecteur2D
     * @param v Second Vecteur2D
     * @return New Vecteur2D with the multiplication of the two Vecteur2D
     */
    public Vecteur2D mul(Vecteur2D v) {
        return new Vecteur2D(this.x * v.x, this.y * v.y);
    }

    /**
     * Method to multiply a Vecteur2D and an ObjEmp
     * @param obj Second ObjEmp
     * @return New Vecteur2D with result of the multiplication of the Vecteur2D and the ObjEmp
     */
    public Vecteur2D mul(ObjEmp obj) {
        return new Vecteur2D(this.x * obj.x, this.y);
    }

    /**
     * Method to divide two Vecteur2D
     * @param v Second Vecteur2D
     * @return New Vecteur2D with the division of the two Vecteur2D
     */
    public Vecteur2D div(Vecteur2D v) {
        if (v.x == 0 || v.y == 0) {
            System.err.println("[X] - Error : Cannot divide by zero.");
            throw new ArithmeticException("[X] - Division by zero.");
        }
        return new Vecteur2D(this.x / v.x, this.y / v.y);
    }

    /**
     * Method to divide a Vecteur2D and an ObjEmp
     * @param obj Second ObjEmp
     * @return New Vecteur2D with result of the division of the Vecteur2D and the ObjEmp
     */
    public Vecteur2D div(ObjEmp obj) {
        if (obj.x == 0) {
            System.err.println("[X] - Error : Cannot divide by zero.");
            return null;
        }
        return new Vecteur2D(this.x / obj.x, this.y);
    }

    @Override
    public String toString() {
        return String.format("[%d, %d]", x, y);
    }
}