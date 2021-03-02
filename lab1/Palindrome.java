public class Palindrome {
    //Основная функция
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String s = args[i];
            System.out.println("isPalindrome(" + s + ") = " + isPalindrome(s));
        }
    }
    //Функция которая переворачивает строчку
    public static String reverseString(String s) {
        String ans = "";
        for (int i = s.length()-1; 0 <= i; i--) {
            ans += s.charAt(i);
        }
        return ans;
    }
    //Логическая функция которая возвращяет значение true или false, в зависимости от того оказалось слово палиндромом
    //или нет.
    public static boolean isPalindrome(String s) {
        boolean ans = false;
        if (s.equals(reverseString(s)))
            ans = true;
        return ans;
    }
}
