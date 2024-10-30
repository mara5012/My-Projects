import java.util.Scanner;

public class Main {
    static int cmmdc(int a, int b){
        int r = a % b;
        while(r != 0){
            a = b;
            b = r;
            r = a % b;
        }
        return b;
    }
    public static void main(String args[]){
        int d = 0;
        if(args.length != 0)
            try{
                int nr1 = Integer.parseInt(args[0]);
                for(int i = 1; i < args.length; i++){
                    int nr2 = Integer.parseInt(args[i]);
                    d = cmmdc(nr1, nr2);
                    nr1 = d;
                }
                System.out.println("Cel mai mare divizor comun este: " + d);
            }catch(NumberFormatException e){
                System.out.println("Argumentele trebuie sa fie numere intregi!");;
                //System.out.println("Cel mai mare divizor comun este: " + d);
            }
        else{
            try {
                Scanner input = new Scanner(System.in);
                System.out.println("Introduceti un numar de argumente: ");
                int n;
                int v[] = new int [100];
                n = Integer.parseInt(input.nextLine());
                System.out.println("Introduceti numerele: ");
                for(int i = 0; i < n; i++){
                    v[i] = Integer.parseInt(input.nextLine());
                }
                int nr1 = v[0];
                for(int i = 1; i < n; i++){
                    int nr2 = v[i];
                    d = cmmdc(nr1, nr2);
                    nr1 = d;
                }
                System.out.println("Cel mai mare divizor comun este: " + d);
            } catch (NumberFormatException e) {
                System.out.println("Argumentele trebuie sa fie numere intregi!");
                System.out.println("Cel mai mare divizor comun este: " + d);
            }
        }

    }
}
