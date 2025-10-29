package task3.t1;

public class Task1 {
    public static void main(String[] args) {
        int number = new java.util.Random().nextInt(900)+ 100;

        int sum = number / 100 + (number / 10) % 10 + number % 10;;

        System.out.println("Сгенерированное число: " + number);
        System.out.println("Сумма его цифр: " + sum);
        System.out.println("Цифры: " + (number/100) + "," + (number/10%10) + "," + (number%10));
    }
}
