import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

public class PileRPL{
    private final ArrayList<Object> pile = new ArrayList<>(); // Dynamic list useful for my case.
    private int maxSizeArray = 1;


    /**
     * Function to add an object into the top of the stack.
     * @param obj Object
     */
    public void push(Object obj){
        pile.add(obj);
    }

    /**
     * Function to get the size of the array list to know if there is at least 2 elements into the stack.
     * @return True : arraylist >= 2 | False : arraylist < 2
     */
    public boolean checkSizeArray(){
        return pile.size() >= 2;
    }

    /**
     * Function to return the top of the stack.
     * @return get the head (top of the stack) object.
     */
    public Object head() {
        try {
            return pile.get(pile.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Function to add two object and remove 2 objects from the stack.
     */
    public void addition() {

        if(checkSizeArray()){
            Object x = pop();
            Object y = pop();

            // I need to check if I have a object in one dimension or two dimension (Vecteur2D).
            if(x instanceof ObjEmp && y instanceof ObjEmp){
                ObjEmp resultObj = ((ObjEmp) x).add((ObjEmp) y);
                pile.add(resultObj);
            } else if (x instanceof Vecteur2D && y instanceof Vecteur2D){
                Vecteur2D resultObj = ((Vecteur2D) x).add((Vecteur2D) y);
                pile.add(resultObj);
            } else if (x instanceof Vecteur2D && y instanceof ObjEmp) {
                Vecteur2D resultObj = ((Vecteur2D) x).add((ObjEmp) y);
                pile.add(resultObj);
            } else if (x instanceof ObjEmp && y instanceof Vecteur2D) {
                Vecteur2D resultObj = ((Vecteur2D) y).add((ObjEmp) x);
                pile.add(resultObj);
            }


        }
        else {
            if (!pile.isEmpty()) {
                pile.get(0);
            }
        }

    }
    /**
     * Function to sub two object and remove 2 objects from the stack.
     */
    public void soustraction() {
        if(checkSizeArray()){
            Object x = pop();
            Object y = pop();

            if(x instanceof ObjEmp && y instanceof ObjEmp){
                ObjEmp resultObj = ((ObjEmp) x).sub((ObjEmp) y);
                pile.add(resultObj);
            } else if (x instanceof Vecteur2D && y instanceof Vecteur2D){
                Vecteur2D resultObj = ((Vecteur2D) x).sub((Vecteur2D) y);
                pile.add(resultObj);
            } else if(x instanceof ObjEmp && y instanceof Vecteur2D){
                Vecteur2D resultObj = ((Vecteur2D) y).sub((ObjEmp) x);
                pile.add(resultObj);
            } else if(x instanceof Vecteur2D && y instanceof ObjEmp){
                Vecteur2D resultObj = ((Vecteur2D) x).sub((ObjEmp) y);
                pile.add(resultObj);
            }
        }
        else {
            if (!pile.isEmpty()) {
                pile.get(0);
            }
        }

    }

    /**
     * Function to multiply two object and remove 2 objects from the stack.
     */
    public void multiplication() {
        if(checkSizeArray()){
            Object x = pop();
            Object y = pop();

            if (x instanceof ObjEmp && y instanceof ObjEmp){
                ObjEmp resultObj = ((ObjEmp) x).mul((ObjEmp) y);
                pile.add(resultObj);
            } else if (x instanceof Vecteur2D && y instanceof Vecteur2D){
                Vecteur2D resultObj = ((Vecteur2D) x).mul((Vecteur2D) y);
                pile.add(resultObj);
            } else if (x instanceof Vecteur2D && y instanceof ObjEmp){
                Vecteur2D resultObj = ((Vecteur2D) x).mul((ObjEmp) y);
                pile.add(resultObj);
            } else if (x instanceof ObjEmp && y instanceof Vecteur2D){
                Vecteur2D resultObj = ((Vecteur2D) y).mul((ObjEmp) x);
                pile.add(resultObj);
            }
        }
        else {
            if (!pile.isEmpty()) {
                pile.get(0);
            }
        }

    }
    /**
     * Function to divide two object and remove 2 objects from the stack.
     * @return True : The division is possible | False : The division is not possible.
     */
    public boolean division() {
        if(checkSizeArray()){
            Object x = pile.get(pile.size() - 1);
            Object y = pile.get(pile.size() - 2);

            if ((x instanceof ObjEmp && ((ObjEmp) x).x == 0) || (x instanceof Vecteur2D && ((Vecteur2D) x).getX() == 0)) {
                System.err.println("[X] - Division by zero is not allowed.");
                return false;
            }

            x = pop();
            y = pop();

            if(x instanceof ObjEmp && y instanceof ObjEmp){
                ObjEmp resultObj = ((ObjEmp) y).div((ObjEmp) x);
                if(resultObj != null){
                    pile.add(resultObj);
                } else { // If the result is null I put back the two objects into the stack.
                    push(y);
                    push(x);
                    return false;
                }
            } else if (x instanceof Vecteur2D && y instanceof Vecteur2D){
                Vecteur2D resultObj = ((Vecteur2D) y).div((Vecteur2D) x);
                if(resultObj != null){
                    pile.add(resultObj);
                } else {
                    push(y);
                    push(x);
                    return false;
                }
            } else if (x instanceof Vecteur2D && y instanceof ObjEmp){
                Vecteur2D resultObj = ((Vecteur2D) x).div((ObjEmp) y);
                if(resultObj != null){
                    pile.add(resultObj);
                } else {
                    push(y);
                    push(x);
                    return false;
                }
            } else if (x instanceof ObjEmp && y instanceof Vecteur2D){
                Vecteur2D resultObj = ((Vecteur2D) y).div((ObjEmp) x);
                if(resultObj != null){
                    pile.add(resultObj);
                } else {
                    push(y);
                    push(x);
                    return false;
                }
            }

            return true;
        }
        else {
            if (!pile.isEmpty()) {
                pile.get(0);
                return true;
            }
        }

        return false;
    }


    /**
     * Function that remove the top element (Last In First Out) of the Arraylist (Stack).
     * @return ObjEmp : The object that we removed from the stack.
     */
    public Object pop() {
        try {
            return pile.remove(pile.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    /**
     * Function to get the maxsize of the Arraylist to use it during the print of that one.
     * @return the max size of the ArrayList (stack)
     */

    public int getMaxSizeArray(){
        if(maxSizeArray < pile.size()){
            maxSizeArray = pile.size();

            return maxSizeArray;
        }

        return maxSizeArray;
    }

    public String toString(){
        String pilePrint = "\n\nStack\n-----------------\n";
        for(int i = getMaxSizeArray() - 1; i >= 0 ; i--){
            try {
                pilePrint += i + " : " + pile.get(i) + "\n";
            }
            catch(Exception IndexOutOfBoundsException){
                pilePrint += i + " :  \n";
            }
        }

        return pilePrint;

    }
}
