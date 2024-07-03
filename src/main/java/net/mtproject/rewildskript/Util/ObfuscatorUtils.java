package net.mtproject.rewildskirpt.Util;

import java.io.*;
import java.util.Enumeration;
import java.util.Random;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ObfuscatorUtils {

    public static void zipFile(File input, File output) {
        byte[] buffer = new byte[1024];
        try (FileOutputStream fos = new FileOutputStream(output);
             ZipOutputStream zos = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(input)) {

            ZipEntry zipEntry = new ZipEntry(input.getName());
            zos.putNextEntry(zipEntry);

            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            zos.closeEntry();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String unzipFile(File zipFile) {
        StringBuilder content = new StringBuilder();
        try (ZipFile zip = new ZipFile(zipFile)) {
            Enumeration<? extends ZipEntry> entries = zip.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                try (InputStream is = zip.getInputStream(entry);
                     Scanner scanner = new Scanner(is, "UTF-8")) {
                    content.append(scanner.useDelimiter("\\A").next());
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return content.toString();
    }

    public static String readFileContent(File file) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public static String generateRandomDir() {
        StringBuilder dir = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            dir.append((char) (random.nextInt(26) + 'a')).append(File.separator);
        }
        return dir.toString();
    }
}
