package net.mtproject.rewildskript.Util;

import net.mtproject.rewildskirpt.Util.ObfuscatorUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;

public class Obfuscator {

    public static void obfuscate(String from, String to, int power) throws IOException {
        File sourceFile = new File(from.replaceAll("/", Matcher.quoteReplacement(File.separator)));
        if (!sourceFile.exists()) {
            return;
        }

        File destinationFile = new File(to.replaceAll("/", Matcher.quoteReplacement(File.separator)));
        if (!destinationFile.exists()) {
            destinationFile.getParentFile().mkdirs();
            destinationFile.createNewFile();
        }

        String code = ObfuscatorUtils.readFileContent(sourceFile);
        StringBuilder obfuscatedCode = new StringBuilder();
        for (char c : code.toCharArray()) {
            int i = (int) c;
            i = (i * power) * 2;
            obfuscatedCode.append(i).append("-");
        }

        File tempFile = new File("temp.ws");
        try (FileWriter fileWriter = new FileWriter(tempFile)) {
            fileWriter.write(obfuscatedCode.toString());
        }

        ObfuscatorUtils.zipFile(tempFile, destinationFile);
        tempFile.delete();
    }

    public static String deobfuscate(File file, int power) {
        String obfuscatedContent = ObfuscatorUtils.unzipFile(file);
        String[] values = obfuscatedContent.split("-");
        StringBuilder originalCode = new StringBuilder();
        for (String value : values) {
            if (value == null || value.isEmpty()) {
                continue;
            }
            int i = Integer.parseInt(value);
            i = (i / power) / 2;
            originalCode.append((char) i);
        }
        return originalCode.toString();
    }
}
