
    public class Primes {
        public static void main(String[] args){
        for (int i = 2; i<=100; i++){
            if(isPrime(i)) System.out.println(i + " ");
        }

    }
        public static boolean isPrime(int n) {
            boolean ans = true;
            for (int i = 2; i <n; i++){
                if (n% i == 0) ans =false;

            }
            return ans;
        }
}
