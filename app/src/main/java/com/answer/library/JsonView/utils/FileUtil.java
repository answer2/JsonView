package com.answer.library.JsonView.utils;

import android.content.Context;
import android.util.Log;
import com.answer.library.JsonView.debug.JsonLog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Utility class for file operations, including reading, writing, copying, and decompressing files.
 */
public class FileUtil {

    public static final String TAG = "FileUtil";

    /**
     * Writes a string to a file.
     *
     * @param path The path of the file.
     * @param data The string data to write.
     */
    public static void writeStringToFile(String path, String data) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(data.getBytes());
        } catch (FileNotFoundException e) {
            JsonLog.d(TAG + "_writeStringToFile", "The file doesn't exist.");
        } catch (IOException e) {
            JsonLog.d(TAG + "_writeStringToFile", e.getMessage());
        }
    }

    /**
     * Writes byte array to a file.
     *
     * @param path The path of the file.
     * @param data The byte array data to write.
     */
    public static void writeBytesToFile(String path, byte[] data) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(data);
        } catch (FileNotFoundException e) {
            JsonLog.d(TAG + "_writeBytesToFile", "The file doesn't exist.");
        } catch (IOException e) {
            JsonLog.d(TAG + "_writeBytesToFile", e.getMessage());
        }
    }

    /**
     * Reads text data from a file.
     *
     * @param file The file to read from.
     * @return The content of the file as a string.
     */
    public static String readFile(File file) {
        return readFile(file.getAbsolutePath());
    }

    /**
     * Reads text data from a file specified by path.
     *
     * @param filePath The path of the file.
     * @return The content of the file as a string.
     */
    public static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        File file = new File(filePath);

        if (!file.isDirectory()) {
            try {
                InputStream inputStream = new FileInputStream(file);
                InputStreamReader inputReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputReader);

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            } catch (FileNotFoundException e) {
                JsonLog.d(TAG + "_readFile", "The file doesn't exist.");
            } catch (IOException e) {
                JsonLog.d(TAG + "_readFile", e.getMessage());
            }
        } else {
            JsonLog.d(TAG, "The file path points to a directory.");
        }

        return content.toString();
    }

    /**
     * Unzips an asset file into the specified output directory.
     *
     * @param context           The application context.
     * @param assetName         The name of the asset file to unzip.
     * @param outputDirectory   The directory to extract files to.
     * @throws IOException If an I/O error occurs.
     */
    public static void unzipAssets(Context context, String assetName, String outputDirectory) throws IOException {
        File outputDir = new File(outputDirectory);

        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        try (InputStream inputStream = context.getAssets().open(assetName);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {

            ZipEntry zipEntry;
            byte[] buffer = new byte[4096];
            int count;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                File file = new File(outputDirectory, zipEntry.getName());

                if (zipEntry.isDirectory()) {
                    file.mkdirs();
                } else {
                    file.getParentFile().mkdirs();
                    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                        while ((count = zipInputStream.read(buffer)) > 0) {
                            fileOutputStream.write(buffer, 0, count);
                        }
                    }
                }
                zipInputStream.closeEntry();
            }
        }
    }

    /**
     * Unzips an APK file into the specified directory.
     *
     * @param context   The application context.
     * @param dir       The directory inside the APK to unzip.
     * @param extDir    The directory to extract files to.
     * @throws IOException If an I/O error occurs.
     */
    public static void unApk(Context context, String dir, String extDir) throws IOException {
        try (ZipFile zipFile = new ZipFile(context.getApplicationInfo().publicSourceDir)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            int dirLength = dir.length() + 1;

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();

                if (name.startsWith(dir)) {
                    String path = name.substring(dirLength);
                    File outputFile = new File(extDir, path);

                    if (entry.isDirectory()) {
                        outputFile.mkdirs();
                    } else {
                        File parentDir = outputFile.getParentFile();
                        if (!parentDir.exists()) {
                            parentDir.mkdirs();
                        }

                        if (!outputFile.exists() || entry.getSize() != outputFile.length() || !getFileMD5(zipFile.getInputStream(entry)).equals(getFileMD5(outputFile))) {
                            try (InputStream in = zipFile.getInputStream(entry);
                            FileOutputStream out = new FileOutputStream(outputFile)) {
                                byte[] buffer = new byte[4096];
                                int count;
                                while ((count = in.read(buffer)) != -1) {
                                    out.write(buffer, 0, count);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Copies an asset file to the SD card.
     *
     * @param context      The application context.
     * @param inFileName   The name of the asset file.
     * @param outFileName  The path of the destination file.
     * @throws IOException If an I/O error occurs.
     */
    public static void assetsToSD(Context context, String inFileName, String outFileName) throws IOException {
        try (InputStream myInput = context.getAssets().open(inFileName);
        OutputStream myOutput = new FileOutputStream(outFileName)) {

            byte[] buffer = new byte[4096];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
        }
    }

    /**
     * Copies a file from one location to another.
     *
     * @param from The source file path.
     * @param to   The destination file path.
     */
    public static void copyFile(String from, String to) {
        try {
            copyFile(new FileInputStream(from), new FileOutputStream(to));
        } catch (IOException e) {
            Log.i(TAG + "_copyFile", e.getMessage());
        }
    }

    /**
     * Copies a file from an input stream to an output stream.
     *
     * @param in  The input stream.
     * @param out The output stream.
     * @return True if the operation was successful, false otherwise.
     */
    public static boolean copyFile(InputStream in, OutputStream out) {
        try {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            return true;
        } catch (Exception e) {
            Log.i(TAG + "_copyFile", e.getMessage());
            return false;
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Copies a directory from one location to another.
     *
     * @param from The source directory path.
     * @param to   The destination directory path.
     * @return True if the operation was successful, false otherwise.
     */
    public static boolean copyDir(String from, String to) {
        return copyDir(new File(from), new File(to));
    }

    /**
     * Copies a directory from one file to another.
     *
     * @param from The source directory.
     * @param to   The destination directory.
     * @return True if the operation was successful, false otherwise.
     */
    public static boolean copyDir(File from, File to) {
        boolean success = true;
        if (from.isDirectory()) {
            if (!to.exists()) {
                to.mkdirs();
            }
            for (File file : from.listFiles()) {
                success &= copyDir(file, new File(to, file.getName()));
            }
        } else {
            try {
                if (!to.exists()) {
                    to.createNewFile();
                }
                success &= copyFile(new FileInputStream(from), new FileOutputStream(to));
            } catch (IOException e) {
                Log.i(TAG + "_copyDir", e.getMessage());
                success = false;
            }
        }
        return success;
    }

    /**
     * Deletes a directory and its contents.
     *
     * @param dir The directory to delete.
     * @return True if the directory was deleted successfully, false otherwise.
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                deleteDir(file);
            }
        }
        return dir.delete();
    }

    /**
     * Deletes a directory and files with a specific extension.
     *
     * @param dir The directory to check.
     * @param ext The file extension to delete.
     */
    public static void deleteDir(File dir, String ext) {
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                deleteDir(file, ext);
            }
            dir.delete();
        }
        if (dir.getName().endsWith(ext)) {
            dir.delete();
        }
    }

    /**
     * Calculates the MD5 hash of a file.
     *
     * @param file The file to hash.
     * @return The MD5 hash of the file.
     */
    public static String getFileMD5(String file) {
        return getFileMD5(new File(file));
    }

    /**
     * Calculates the MD5 hash of a file.
     *
     * @param file The file to hash.
     * @return The MD5 hash of the file.
     */
    public static String getFileMD5(File file) {
        try {
            return getFileMD5(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    /**
     * Calculates the MD5 hash of an input stream.
     *
     * @param in The input stream to hash.
     * @return The MD5 hash of the input stream.
     */
    public static String getFileMD5(InputStream in) {
        byte[] buffer = new byte[4096];
        int len;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

