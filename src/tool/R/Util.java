/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.doshisha.bil0167.tool.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.rosuda.JRI.Rengine;

/**
 *
 * @author katsuya
 */
public class Util {

    public final static String JRI = "./lib/JRI.ini";

    public static String getDefaultJRIVersion() {
        try {
            java.util.Properties prop = new java.util.Properties();
            prop.load(new java.io.FileInputStream(JRI));
            System.out.println(prop.getProperty("version"));
            return prop.getProperty("version");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void copyTransfer(String srcPath, String destPath)
            throws IOException {

        FileChannel srcChannel = new FileInputStream(srcPath).getChannel();
        FileChannel destChannel = new FileOutputStream(destPath).getChannel();
        try {
            srcChannel.transferTo(0, srcChannel.size(), destChannel);
        } finally {
            srcChannel.close();
            destChannel.close();
        }

    }

    public static boolean deleteFile(File dirOrFile) {
        if (dirOrFile.isDirectory()) {//ディレクトリの場合  
            String[] children = dirOrFile.list();//ディレクトリにあるすべてのファイルを処理する  
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteFile(new File(dirOrFile, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // 削除  
        return dirOrFile.delete();
    }

    public static void updateINIFile(String file, Map<String, String> values) throws Exception {
        try {
            FileWriter fw = new FileWriter(file);
            for (String property : values.keySet()) {
                String value = values.get(property);
                fw.write(property + "=" + value + "\n");
            }
            fw.flush();
            fw.close();
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }

    public static boolean isInteger(String str) {
        try {
            Integer.valueOf(str);
        } catch (Exception e) {
        }
        return true;
    }

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static int[] readInVector(Rengine engine, String text, String sep, String name) throws UnsupportedEncodingException {
        int[] num = new int[2];

        //parse input text
        String[] inputs = text.split("\n");
        String[] elements = inputs[1].split(sep);
        if (Util.isDouble(elements[1])) {
            int colNumber = inputs[0].split(sep).length - 1;
            int rowNumber = inputs.length - 1;
            num[0] = rowNumber;
            num[1] = colNumber;

            String[] colNames = new String[colNumber];
            String[] rowNames = new String[rowNumber];
            double[][] data = new double[colNumber][rowNumber];
            Pattern pattern = Pattern.compile("\"(.+?)\"");
            for (int i = 0; i < inputs.length; i++) {
                String line = inputs[i];
                elements = line.split(sep);
                if (i == 0) {
                    for (int j = 1; j < elements.length; j++) {
                        Matcher matcher = pattern.matcher(elements[j]);
                        if (matcher.find()) {
                            colNames[j - 1] = matcher.group(1);
                        } else {
                            colNames[j - 1] = elements[j];
                        }
                    }
                } else {
                    for (int j = 0; j < elements.length; j++) {
                        if (j == 0) {
                            Matcher matcher = pattern.matcher(elements[j]);
                            if (matcher.find()) {
                                rowNames[i - 1] = matcher.group(1);
                            } else {
                                rowNames[i - 1] = elements[j];
                            }
                        } else {
                            try {
                                data[j - 1][i - 1] = Double.valueOf(elements[j]);

                            } catch (Exception ex) {

                            }
                        }
                    }
                }
            }

            // create a named list or data.frame
            long[] la = new long[data.length];
            for (int i = 0; i < data.length; i++) {
                la[i] = engine.rniPutDoubleArray(data[i]);
            }

            // now build a list (generic vector is how that's called in R)
            long vector_xp = engine.rniPutVector(la);

            // now let's add row names and column names
            long colName_xp = engine.rniPutStringArray(colNames);
            engine.rniSetAttr(vector_xp, "names", colName_xp);
            long rowName_xp = engine.rniPutStringArray(rowNames);
            engine.rniSetAttr(vector_xp, "row.names", rowName_xp);

            long class_xp = engine.rniPutString("data.frame");
            engine.rniSetAttr(vector_xp, "class", class_xp);

            // assign the whole thing to the name
            engine.rniAssign(name, vector_xp, 0);

        } else {
            int end = 2;
            for (int i = 2; i < elements.length; i++) {
                if (Util.isDouble(elements[i])) {
                    break;
                } else {
                    end++;
                }
            }

            int colNumber = inputs[0].split(sep).length;
            int rowNumber = inputs.length - 1;
            num[0] = rowNumber;
            num[1] = colNumber;

            String[] colNames = new String[colNumber];
            String[][] data0 = new String[end][rowNumber];
            double[][] data1 = new double[colNumber - end][rowNumber];
            Pattern pattern = Pattern.compile("\"(.+?)\"");
            for (int i = 0; i < inputs.length; i++) {
                String line = inputs[i];
                elements = line.split(sep);
                if (i == 0) {
                    for (int j = 0; j < elements.length; j++) {
                        Matcher matcher = pattern.matcher(elements[j].trim());
                        if (matcher.find()) {
                            colNames[j] = matcher.group(1);
                        }
                    }
                } else {
                    for (int j = 0; j < elements.length; j++) {
                        if (j <= (end - 1)) {
                            Matcher matcher = pattern.matcher(elements[j].trim());
                            if (matcher.find()) {
                                data0[j][i - 1] = matcher.group(1);
                            }
                        } else {
                            data1[j - end][i - 1] = Double.valueOf(elements[j]);
                        }
                    }
                }
            }

            // create a named list or data.frame
            long[] la = new long[colNumber];
            for (int j = 0; j < colNumber; j++) {
                if (j <= (end - 1)) {
                    la[j] = engine.rniPutStringArray(data0[j]);
                } else {
                    la[j] = engine.rniPutDoubleArray(data1[j - end]);
                }
            }

            // now build a list (generic vector is how that's called in R)
            long vector_xp = engine.rniPutVector(la);

            // now let's add row names and column names
            long colName_xp = engine.rniPutStringArray(colNames);
            engine.rniSetAttr(vector_xp, "names", colName_xp);

            long class_xp = engine.rniPutString("data.frame");
            engine.rniSetAttr(vector_xp, "class", class_xp);
            String[] rowNames = new String[rowNumber];
            for (int i = 1; i <= rowNames.length; i++) {
                rowNames[i - 1] = String.valueOf(i);
            }
            long rowName_xp = engine.rniPutStringArray(rowNames);
            engine.rniSetAttr(vector_xp, "row.names", rowName_xp);

            // assign the whole thing to the name
            engine.rniAssign(name, vector_xp, 0);
        }

        return num;
    }

}
