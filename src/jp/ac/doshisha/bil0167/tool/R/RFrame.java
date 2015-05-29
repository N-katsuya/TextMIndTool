/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.ac.doshisha.bil0167.tool.R;

import java.awt.AWTEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import jp.ac.doshisha.bil0167.tool.main.GuiRConsole;
import jp.ac.doshisha.bil0167.tool.main.NameForm;
import jp.ac.doshisha.bil0167.tool.main.Tool_Main;
import org.rosuda.REngine.JRI.JRIEngine;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPList;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.RList;

/**
 *
 * @author katsuya
 */
public class RFrame extends javax.swing.JFrame {
private static Vector element = new Vector();
private static HashMap<String,ArrayList> dataRowNames = new HashMap<String,ArrayList>();

    public static void putDataRowNames(String dataname,ArrayList array) {
        RFrame.dataRowNames.put(dataname, array);
    }

    public static void setElement(Vector element) {
        RFrame.element = element;
    }

    /**
     * Creates new form RFrame
     */
    public RFrame() {
        initComponents();
    }

    public RFrame getRFrame() {
        return this;
    }

    public RFrame(final Tool_Main toolmain, boolean load, boolean NODATA) throws Exception {
        super("R操作パネル");
        this.toolmain = toolmain;
        this.load = load;
        this.NODATA = NODATA;
        initComponents();
      
        jComboBox1.setModel(combomodel);
        combomodel.addElement("なし");
        jComboBox1.repaint();
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                toolmain.toFront();
                Tool_Main.setIsRF(false);
                getRFrame().dispose();

            }

        });

    }

    
    
    public RFrame( boolean load, boolean NODATA) throws Exception {
        super("R操作パネル");
        this.load = load;
        this.NODATA = NODATA;
        initComponents();
      
        jComboBox1.setModel(combomodel);
        combomodel.addElement("なし");
        jComboBox1.repaint();
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
//                toolmain.toFront();
                Tool_Main.setIsRF(false);
                getRFrame().dispose();

            }

        });

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jButton8 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jButton9 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuFileExit = new javax.swing.JMenuItem();
        FileMenuAllExit = new javax.swing.JMenuItem();

        buttonGroup1.add(jRadioButton1);
        buttonGroup1.add(jRadioButton3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jRadioButton1.setText("カイ二乗検定");

        jRadioButton3.setText("フィッシャーの直接確率検定");

        jButton8.setText("グラフ表示");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("結果を表示する");

        jCheckBox2.setText("結果を表示する");

        jButton9.setText("グラフ表示");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox2)
                            .addComponent(jCheckBox1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jRadioButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addComponent(jButton9))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jRadioButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton8)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton3)
                    .addComponent(jButton9))
                .addGap(47, 47, 47))
        );

        jButton1.setText("実行");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton7.setText("jButton7");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jButton7.setVisible(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("検定", jPanel1);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton4.setText("実行");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("実行");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("実行");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel2.setText("主成分分析");

        jLabel3.setText("非階層的クラスター分析");

        jLabel4.setText("対応分析");

        jLabel5.setText("主成分分析(FactoMineR)");

        jButton2.setText("実行");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel6.setText("対応分析(FActoMineR)");

        jButton3.setText("実行");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel7.setText("階層的クラスター分析");

        jButton10.setText("実行");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel3)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addComponent(jLabel7))
                .addGap(64, 64, 64)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton10)
                    .addComponent(jButton6)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jButton3))
                .addGap(13, 13, 13)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jButton10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6)
                    .addComponent(jLabel3))
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 261, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("分析", jPanel2);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "なし" }));

        jLabel1.setText("選択中のデータ");

        jMenu1.setText("File");

        jMenu3.setText("インポート");
        jMenu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu3ActionPerformed(evt);
            }
        });

        jMenuItem1.setText("CSVファイル");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenu1.add(jMenu3);

        jMenuFileExit.setText("終了");
        jMenuFileExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuFileExitActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuFileExit);

        FileMenuAllExit.setText("全て終了");
        FileMenuAllExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FileMenuAllExitActionPerformed(evt);
            }
        });
        jMenu1.add(FileMenuAllExit);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuFileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuFileExitActionPerformed
        // TODO add your handling code here:

        toolmain.toFront();
        Tool_Main.setIsRF(false);
        this.dispose();
        //interrupt = true;

    }//GEN-LAST:event_jMenuFileExitActionPerformed

    private void FileMenuAllExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FileMenuAllExitActionPerformed
        // TODO add your handling code here:
        GuiRConsole.setCommand("base::q(save=\"no\")");
    }//GEN-LAST:event_FileMenuAllExitActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here
        File dir = new File(System.getProperty("user.home"), "Desktop");
        JFileChooser filechooser = new JFileChooser(dir);
        String path2 = null;
        StringBuilder text = new StringBuilder();
        int selected = filechooser.showOpenDialog(this);
        if (selected == JFileChooser.APPROVE_OPTION) {
            File file = filechooser.getSelectedFile();
            path2 = file.getAbsolutePath();
        } else if (selected == JFileChooser.CANCEL_OPTION) {

        } else if (selected == JFileChooser.ERROR_OPTION) {

        }
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path2), "Shift_JIS"));

            String str;

            while ((str = br.readLine()) != null) {

                text.append(str + "\n");

            }

            br.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

        String seperator = ",";
        NameForm nm = new NameForm(text, ",");

        nm.setVisible(true);

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String value = "";
        Enumeration<AbstractButton> ite = buttonGroup1.getElements();
        while (ite.hasMoreElements()) {
            AbstractButton button = ite.nextElement();
            if (button.isSelected()) {
                value = button.getText();
                break;
            }
        }
        //System.out.println(value);
        if (value.equals("カイ二乗検定")) {
            try {
                Rcommand.excuteChiSquaretest(jComboBox1.getSelectedItem().toString(),jCheckBox1.isSelected());
            } catch (REngineException ex) {
                Logger.getLogger(RFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (REXPMismatchException ex) {
                Logger.getLogger(RFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(value.equals("フィッシャーの直接確率検定")){
            Rcommand.excuteFishertest(jComboBox1.getSelectedItem().toString(),jCheckBox2.isSelected());
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        final PCMOptionFrame pcm = new PCMOptionFrame();
    String toString = jComboBox1.getSelectedItem().toString();
    
       // System.out.println(element);
        pcm.getSelectVariablesPanel2().setElements(dataRowNames.get(toString));
        pcm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pcm.setDataName(jComboBox1.getSelectedItem().toString());
        pcm.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
        pcm.dispose();
            }
        });
        pcm.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        
          final CAFrame ca = new CAFrame();
    String toString = jComboBox1.getSelectedItem().toString();
    
       // System.out.println(element);
        ca.getSelectVariablesPanel1().setElements(dataRowNames.get(toString));
        ca.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ca.setDataName(jComboBox1.getSelectedItem().toString());
        ca.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
        ca.dispose();
            }
        });
        ca.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
                  final KClusteringFrame cl = new KClusteringFrame();
    String toString = jComboBox1.getSelectedItem().toString();
    
       // System.out.println(element);
        cl.getSelectVariablesPanel1().setElements(dataRowNames.get(toString));
        cl.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        cl.setDataName(jComboBox1.getSelectedItem().toString());
        cl.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
        cl.dispose();
            }
        });
        cl.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String dataname = jComboBox1.getSelectedItem().toString();
        GuiRConsole.setCommand("library(FactoMineR)");
             final FactoPCMOptionFrame pcm = new FactoPCMOptionFrame();
    String toString = jComboBox1.getSelectedItem().toString();
    
       // System.out.println(element);
        pcm.getSelectVariablesPanel2().setElements(dataRowNames.get(toString));
        pcm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pcm.setDataName(jComboBox1.getSelectedItem().toString());
        pcm.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
        pcm.dispose();
            }
        });
        pcm.setVisible(true);
        String comannd ="res.pca <- PCA("+dataname+", scale.unit=F)\n" +
"## plot of the eigenvalues\n" +
"## barplot(res.pca$eig[,1],main=\"Eigenvalues\",names.arg=1:nrow(res.pca$eig))\n" +
"summary(res.pca)\n";
    //    GuiRConsole.setCommand(comannd);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
          String dataname = jComboBox1.getSelectedItem().toString();
        GuiRConsole.setCommand("library(FactoMineR)");
             final FactoCAFrame pcm = new FactoCAFrame();
    String toString = jComboBox1.getSelectedItem().toString();
    
       // System.out.println(element);
        pcm.getSelectVariablesPanel2().setElements(dataRowNames.get(toString));
        pcm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pcm.setDataName(jComboBox1.getSelectedItem().toString());
        pcm.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
        pcm.dispose();
            }
        });
        pcm.setVisible(true);
        GuiRConsole.setCommand("library(FactoMineR)");
        String command = 
"res.ca <- CA ("+dataname+")\n" +
"summary(res.ca)\n" +
               
"ellipseCA(res.ca)" ;
        //GuiRConsole.setCommand(command);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        
        try{
            JRIEngine engine = GuiRConsole.getRengine();
             REXP rdataframe = engine.parseAndEval("t(iris)");
            REXPList attrList = rdataframe._attr(); // ないときは，null
            //ArrayList<String> array = new ArrayList<String>();
            
            Vector<String> vec = new Vector<String>();
            ArrayList<Vector> result = new ArrayList<Vector>();
            if (attrList != null) {
                RList rList = rdataframe.asList();
                System.out.println(rList.names);
            
                
              
                for(int j =0;j<rList.size();j++){
                    REXP get = (REXP)rList.get(j);
                    
                      String[] asStrings = get.asStrings();
                for (int i = 0; i < asStrings.length; i++) {
                    vec.add(asStrings[i]);
                }
                result.add(vec);
                    System.out.println(vec);
                    vec.clear();
                }
                vec.clear();
                REXP get = (REXP) rList.at(4);
                String[] asStrings = get.asStrings();
                for (int i = 0; i < asStrings.length; i++) {
                    vec.add((i + 1) + " " + asStrings[i]);
                }
                
                
                for(int i=0;i<result.size();i++){
                    Vector<String> vect = result.get(i);
                    for(String temp:vect){
                        
                    }
                    
                }
                
            }
            
        } catch (REngineException ex) {
            Logger.getLogger(NameForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (REXPMismatchException ex) {
            Logger.getLogger(NameForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
    try {
        // TODO add your handling code here:
        JRIEngine engine = GuiRConsole.getRengine();
        engine.getRni();
        String re =jComboBox1.getSelectedItem().toString();
        RFrame.setDatanames(re);
        REXP rdataframe = engine.parseAndEval(re);
        REXPList attrList = rdataframe._attr(); // ないときは，null
        ArrayList<String> array = new ArrayList<String>();
        if (attrList != null) {
            try {
                RList rList = attrList.asList();
                REXP get = (REXP) rList.at(2);
                String[] asStrings = get.asStrings();
                for (int i = 0; i < asStrings.length; i++) {
                    array.add((i + 1) + " " + asStrings[i]);
                }     } catch (REXPMismatchException ex) {
                    Logger.getLogger(RFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        
        //  System.out.println(array);
        RFrame.putDataRowNames(re, array);
         ChiSquaretestGraphicsFrame cgf = new ChiSquaretestGraphicsFrame();
        cgf.getSelectVariablesPanel1().setElements(array);
        cgf.setDataname(re);
        cgf.setVisible(true);
    } catch (REngineException ex) {
        Logger.getLogger(RFrame.class.getName()).log(Level.SEVERE, null, ex);
    } catch (REXPMismatchException ex) {
        Logger.getLogger(RFrame.class.getName()).log(Level.SEVERE, null, ex);
    }
            
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
            try {
        // TODO add your handling code here:
        JRIEngine engine = GuiRConsole.getRengine();
        engine.getRni();
        String re =jComboBox1.getSelectedItem().toString();
        RFrame.setDatanames(re);
        REXP rdataframe = engine.parseAndEval(re);
        REXPList attrList = rdataframe._attr(); // ないときは，null
        ArrayList<String> array = new ArrayList<String>();
        if (attrList != null) {
            try {
                RList rList = attrList.asList();
                REXP get = (REXP) rList.at(2);
                String[] asStrings = get.asStrings();
                for (int i = 0; i < asStrings.length; i++) {
                    array.add((i + 1) + " " + asStrings[i]);
                }     } catch (REXPMismatchException ex) {
                    Logger.getLogger(RFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        
        //  System.out.println(array);
        RFrame.putDataRowNames(re, array);
         ChiSquaretestGraphicsFrame cgf = new ChiSquaretestGraphicsFrame();
        cgf.getSelectVariablesPanel1().setElements(array);
        cgf.setDataname(re);
        cgf.setVisible(true);
    } catch (REngineException ex) {
        Logger.getLogger(RFrame.class.getName()).log(Level.SEVERE, null, ex);
    } catch (REXPMismatchException ex) {
        Logger.getLogger(RFrame.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        
                  final ClusteringFrame cl = new ClusteringFrame();
    String toString = jComboBox1.getSelectedItem().toString();
    
       // System.out.println(element);
        cl.getSelectVariablesPanel2().setElements(dataRowNames.get(toString));
        cl.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        cl.setDataName(jComboBox1.getSelectedItem().toString());
        cl.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
        cl.dispose();
            }
        });
        cl.setVisible(true);
    }//GEN-LAST:event_jButton10ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem FileMenuAllExit;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuFileExit;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
    private Tool_Main toolmain;
    private boolean load;
    private boolean NODATA;
    private static boolean interrupt = false;
    private static Vector<String> datanames = new Vector<String>();
   private static   DefaultComboBoxModel combomodel = new DefaultComboBoxModel();

    public static Vector<String> getDatanames() {
        return datanames;
    }

    

    public static void setDatanames(String dataname) {
        HashSet<String> hs = new HashSet<String>();
        hs.addAll(datanames);
        hs.add(dataname);
//        datanames.add(dataname);
        datanames.removeAllElements();
        combomodel.removeAllElements();
        
        for(String temp:hs){
            datanames.add(temp);
        }
        for(String temp:datanames){
            combomodel.addElement(temp);
        }
        combomodel.setSelectedItem(dataname);
        
        
    }

    

    public static boolean isInterrupt() {
        return interrupt;
    }

}
