
public class Test {
    
    public static void main(String[] args) {
        
        WeirdList List = new WeirdList(3, WeirdList.EMPTY);
        WeirdList List2 = new WeirdList(4, List);
        WeirdList List3 = new WeirdList(5, List2);
        WeirdList List4 = new WeirdList(6, List);
        List3.print();
        System.out.println("");
        System.out.println(List3.length());
        System.out.println(List2.length());
        System.out.println(List.length());
        System.out.println(List4.length());
    }

}
