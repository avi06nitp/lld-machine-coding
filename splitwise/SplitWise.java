import splitwise.controller.SplitwiseController;

import java.util.Scanner;

public class SplitWise {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SplitwiseController splitwiseController = new SplitwiseController(scanner);
        splitwiseController.initialize();
    }
}