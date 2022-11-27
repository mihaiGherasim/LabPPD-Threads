public class Monomial {
    int value;
    int grade;

    public Monomial(int value, int grade){
        this.value = value;
        this.grade = grade;
    }

    @Override
    public String toString(){
        return this.value+"x^"+this.grade;
    }
}
