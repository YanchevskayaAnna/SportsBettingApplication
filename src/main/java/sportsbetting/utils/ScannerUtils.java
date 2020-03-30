package sportsbetting.utils;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ScannerUtils {
    private  final Scanner scanner = new Scanner(System.in);
    public double getInputDouble() {
        return scanner.nextDouble();
    }

    public boolean hasInputDouble() {
        return scanner.hasNextDouble();
    }

    public int getInputInt() {
        return scanner.nextInt();
    }

    public boolean hasInputInt() {
        return scanner.hasNextInt();
    }
}
