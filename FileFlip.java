import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FileFlip {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Meminta pengguna memasukkan nama file
        System.out.print("Masukkan nama file teks yang akan dibaca: ");
        String fileName = scanner.nextLine();

        // Meminta pengguna memasukkan ukuran bagian
        System.out.print("Masukkan ukuran bagian (dalam jumlah baris): ");
        int partSize = scanner.nextInt();
        splitFile(fileName, partSize);

        scanner.close();
    }

    private static void splitFile(String fileName, int partSize) {
        Queue<String> queue = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int partNumber = 1;
            int lineCount = 0;


            while ((line = reader.readLine()) != null) {
                queue.add(line);
                lineCount++;
                if (lineCount == partSize) {
                    writePartToFile(queue, partNumber);
                    partNumber++;
                    lineCount = 0;
                }
            }

            // Menulis sisa baris yang mungkin ada
            if (!queue.isEmpty()) {
                writePartToFile(queue, partNumber);
            }

            System.out.println("File telah dipotong menjadi beberapa bagian.");
        } catch (IOException e) {
            System.err.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        }
    }

    private static void writePartToFile(Queue<String> queue, int partNumber) {
        String outputFileName = "part_" + partNumber + ".txt";

        try (FileWriter writer = new FileWriter(outputFileName)) {
            while (!queue.isEmpty()) {
                writer.write(queue.poll() + System.lineSeparator());
            }
            System.out.println("Bagian " + partNumber + " ditulis ke " + outputFileName);
        } catch (IOException e) {
            System.err.println("Terjadi kesalahan saat menulis file: " + e.getMessage());
        }
    }
}
