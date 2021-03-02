import java.util.ArrayList;
public class Primes {
     public static ArrayList<Integer> primes = new ArrayList<Integer>();
     public static void main(String[] args) {
     for (int i = 2; i < 100; i++) {
         if (isPrime(i)) {
             System.out.println(i);
         primes.add(i);
         }
         }
         }
     public static boolean isPrime(int n) {
     for (int prime : primes) {
         if (prime*prime > n)
            break;
         else if (n % prime == 0)
            return false;
     }
 return true;
 }
}
