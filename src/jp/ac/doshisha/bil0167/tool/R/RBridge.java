/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.doshisha.bil0167.tool.R;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author katsuya
 */
public class RBridge {

    private boolean R_VALID = false;
    private Component parentcom = null;

    public RBridge(Component com) {
        parentcom = com;
    }

    public boolean checkR() throws Exception {
        String path = System.getenv("PATH");

        Pattern pattern = Pattern.compile("^([^;]+?\\\\R\\\\R-\\d+?.\\d+?.\\d+?\\\\bin\\\\i386)|"
                + ";([^;]+?\\\\R\\\\R-\\d+?.\\d+?.\\d+?\\\\bin\\\\i386)");
        Matcher matcher = pattern.matcher(path);
        String R_HOME = null;
        if (matcher.find()) {
            R_HOME = matcher.group(1);
            if (R_HOME == null) {
                R_HOME = matcher.group(2);
            }
        }

        if (R_HOME == null) {
            processingErrorMessage("Please install 32 bit R, and add the R folder (..\\R\\..\\bin\\i386) to the System PATH variable, at first.", parentcom);
            return true;
        } else {
            File R_FILE = new File(R_HOME + "\\R.exe");
            if (!R_FILE.exists()) {
                processingErrorMessage("Please install 32 bit R, and add the R folder (..\\R\\..\\bin\\i386) to the System PATH variable, at first.", parentcom);
                return true;
            } else {
                //check R version
                int idx0 = R_HOME.indexOf("R-") + 2;
                int idx1 = R_HOME.indexOf(".", idx0) + 1;
                idx1 = R_HOME.indexOf(".", idx1);
                String R_VERSION = R_HOME.substring(idx0, idx1) + ".0";

                boolean RELOAD = false;

                //check rJava
                String result = execCmd("Rscript ./R/rJava0.R")[1];
                //rJava has not been installed
                if (result.contains("find.package")) {
                    String mes = "Package \"rJava\" is required.\n"
                            + "Do you want to install the package now? (Please make sure that the proxy settings are correct.)";
                    int answer = showConfirmMessage(mes, "YES_NO_OPTION", parentcom);
                    if (answer == 0) { // YES
                        result = execCmd("Rscript ./R/rJava1.R")[1];
                        showMessage(result, parentcom);
                    } else {
                        return false;
                    }
                }// end of check rJava

                //check JRI.jar
                if (R_VERSION.compareTo(Util.getDefaultJRIVersion()) != 0) {
                    String srcPath = "./lib/JRI/" + R_VERSION + "/JRI.jar";
                    String destPath = "./lib/JRI.jar";
                    Util.copyTransfer(srcPath, destPath);
                    srcPath = "./lib/JRI/" + R_VERSION + "/jri.dll";
                    destPath = "./jri.dll";
					//Util.copyTransfer(srcPath, destPath);

                    Map<String, String> values = new HashMap<String, String>();
                    values.put("version", R_VERSION);
                    Util.updateINIFile(Util.JRI, values);

                    RELOAD = true;
                }

                //check JavaGD
                result = execCmd("Rscript R\\JavaGD0.R")[1];
                //JavaGD has not been installed
                if (result.contains("find.package")) {
                    String mes = "Package \"JavaGD\" is required.\n"
                            + "Do you want to install the package now? (Please make sure that the proxy settings are correct.)";
                    int answer = showConfirmMessage(mes, "YES_NO_OPTION", parentcom);
                    if (answer == 0) { // YES
                        result = execCmd("Rscript R\\JavaGD1.R")[1];
                        showMessage(result, parentcom);

                        //copy JavaGD files
                        String srcPath = R_HOME.substring(0, R_HOME.indexOf("\\bin")) + "\\library\\JavaGD\\java\\javaGD.jar";
                        String destPath = "./lib/javaGD.jar";
                        Util.copyTransfer(srcPath, destPath);
                        srcPath = R_HOME.substring(0, R_HOME.indexOf("\\bin")) + "\\library\\JavaGD\\libs\\i386\\JavaGD.dll";
                        destPath = "./JavaGD.dll";
                        Util.copyTransfer(srcPath, destPath);

                        RELOAD = true;
                    } else {
                        return false;
                    }
                }
                if (RELOAD) {
                    String mes = "It is needed to restart MTMineR to use R.\n"
                            + "Do you want to close MTMineR now?";
                    int answer = showConfirmMessage(mes, "YES_NO_OPTION", parentcom);
                    if (answer == 0) {
                        //saveCharset();
                        removeTmpFolder();
                        System.exit(0);
                    } else {
                        showMessage("Please restart MTMineR later!", parentcom);
                        //jMenuR.setEnabled(false);
                    }
                    R_VALID = false;
                    return false;
                } else {
                    R_VALID = true;
                    return true;
                }
            }
        }
    }

    private void processingErrorMessage(String msg, Component parentComponent) {
        JOptionPane.showMessageDialog(parentComponent,
                msg,
                "Processing Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private String[] execCmd(String cmd) throws Exception {
        Process process = Runtime.getRuntime().exec(cmd);

        InputStream in = process.getInputStream();
        InputStream ein = process.getErrorStream();

        String in_result = "";
        String er_result = "";
        String line;

        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        while ((line = br.readLine()) != null) {
            in_result += line;
        }
        br.close();
        in.close();

        BufferedReader ebr = new BufferedReader(new InputStreamReader(ein));
        while ((line = ebr.readLine()) != null) {
            er_result += line;
        }
        ebr.close();
        ein.close();

        process.waitFor();

        String[] results = {in_result, er_result};

        return results;
    }

    protected int showConfirmMessage(final String msg, final String title, Component parentComponent) {
        return JOptionPane.showConfirmDialog(parentComponent,
                msg,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }

    private void showMessage(String msg, Component parentComponent) {
        JOptionPane.showMessageDialog(parentComponent,
                msg,
                "Information",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void removeTmpFolder() {
        Util.deleteFile(new File("tmp"));
    }

}
