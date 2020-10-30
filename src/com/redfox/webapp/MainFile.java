package com.redfox.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File("./src/com/redfox/webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//      TODO: Сделать рекурсивный обход и вывод имени файлов в каталогах и подкаталогах (корневой каталог - ваш проект)
        System.out.println("==================================================");
        File root = new File("./");
        printFiles(root, "");
        System.out.println("==================================================");
        System.out.println(root.getAbsoluteFile().getParentFile().getName());
    }

    static void printFiles(File root, String indent) {
        System.out.println(indent + root.getName());
        indent += "-";
        for (File file : root.listFiles()) {
            if (file.isDirectory()) {
                printFiles(file, indent);
            } else {
                System.out.println(indent + file.getName());
            }
        }
    }
}
