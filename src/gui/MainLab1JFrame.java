/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainLab1JFrame.java
 *
 * Created on Nov 14, 2011, 8:12:31 PM
 */
package gui;

import la.Lexeme;
import la.LexemeBuilder;
import la.LexemeType;
import la.LexicalAnalyzerException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileNotFoundException;
import java.util.Scanner;

import sa.LR1Analyzer;

/**
 * @author Taras
 */
public class MainLab1JFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainLab1JFrame
     */
    public MainLab1JFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lexicalAnalyzer = new la.LexicalAnalyzer();
        loadFileChooser = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sourceCodeArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        lexemeTypeComboBox = new javax.swing.JComboBox();
        getLexemeButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        logPane = new javax.swing.JTextPane();
        exitButton = new javax.swing.JButton();
        cursorPositionLabel = new javax.swing.JLabel();
        syntaxAnalysButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        mainMenuBar = new javax.swing.JMenuBar();
        mainMenu = new javax.swing.JMenu();
        getLexemeMenuItem = new javax.swing.JMenuItem();
        loadTextMenuItem = new javax.swing.JMenuItem();
        clearLogMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();

        loadFileChooser.setFileFilter(new FileNameExtensionFilter("Source files (*.jts)", "jts"));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Lexical Analyzer");

        sourceCodeArea.setColumns(20);
        sourceCodeArea.setLineWrap(true);
        sourceCodeArea.setRows(5);
        sourceCodeArea.setTabSize(4);
        sourceCodeArea.setText("class ClassBefore{\n   public String field1 = \"str\";\n   public int method1(byte b, char c, String str){\n       b = 'c';\n       if (true){\n           String str = \"\";\n           int i = System.in.read();\n       }\n       System.out.println(1);\n       return b;\n   }\n}\npublic class MainClass{\n   public boolean f1 = -3.4028235E+38;\n   public String f1 = true||false;\n   public static void main (String[] args){\n       int c = -15;\n       System.out.println(\"Hello, world!\");\n   }\n}\nclass ClassAfter1{\n   public boolean field1 = true;\n}\nclass ClassAfter2{\n   public void method1(ClassAfter2 arg){\n       String str;\n       boolean b2 = 1;\n       int i = System.in.read();\n       while(1>0){\n           char c = -2147483647;\n           if((true && false) || false){\n               System.out.println(\"Hello, world!\");\n           }\n       }\n       return;\n   }\n}");
        sourceCodeArea.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                sourceCodeAreaCaretUpdate(evt);
            }
        });
        jScrollPane1.setViewportView(sourceCodeArea);

        jLabel1.setText("Select lexeme type:");

        lexemeTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(LexemeType.getValues()));

        getLexemeButton.setText("Get lexeme");
        getLexemeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getLexemeButtonActionPerformed(evt);
            }
        });

        logPane.setEditable(false);
        jScrollPane3.setViewportView(logPane);
        logPane.setContentType("text/html");
        logPane.setText("<html>Log info:</html>");

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        cursorPositionLabel.setText("Cursor position:");

        syntaxAnalysButton.setText("Syntax check");
        syntaxAnalysButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                syntaxAnalysButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(476, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(exitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(syntaxAnalysButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(getLexemeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lexemeTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
                            .addContainerGap())
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                            .addGap(172, 172, 172))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(cursorPositionLabel)
                            .addContainerGap(369, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {exitButton, getLexemeButton, jLabel1, lexemeTypeComboBox, syntaxAnalysButton});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lexemeTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(getLexemeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(syntaxAnalysButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(179, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                    .addGap(18, 18, 18)
                    .addComponent(cursorPositionLabel)
                    .addGap(18, 18, 18)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        jScrollPane2.setViewportView(jTree1);

        mainMenu.setText("Menu");

        getLexemeMenuItem.setText("Get lexeme");
        getLexemeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getLexemeMenuItemActionPerformed(evt);
            }
        });
        mainMenu.add(getLexemeMenuItem);

        loadTextMenuItem.setText("Load text");
        loadTextMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTextMenuItemActionPerformed(evt);
            }
        });
        mainMenu.add(loadTextMenuItem);

        clearLogMenuItem.setText("Clear log");
        clearLogMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearLogMenuItemActionPerformed(evt);
            }
        });
        mainMenu.add(clearLogMenuItem);
        mainMenu.add(jSeparator1);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        mainMenu.add(exitMenuItem);

        mainMenuBar.add(mainMenu);

        setJMenuBar(mainMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void getLexemeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getLexemeButtonActionPerformed
        analyzeCode();
    }//GEN-LAST:event_getLexemeButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void getLexemeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getLexemeMenuItemActionPerformed
        analyzeCode();
    }//GEN-LAST:event_getLexemeMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void loadTextMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadTextMenuItemActionPerformed
        loadFile();
    }

    private void loadFile() {
        int result = loadFileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                Scanner scanner = new Scanner(loadFileChooser.getSelectedFile());
                sourceCodeArea.setText("");
                while (scanner.hasNextLine()) {
                    sourceCodeArea.append(scanner.nextLine());
                    if (scanner.hasNext()) {
                        sourceCodeArea.append("\n");
                    }
                }
            } catch (FileNotFoundException ex) {
                logError(ex.getMessage());
            }
        }
    }//GEN-LAST:event_loadTextMenuItemActionPerformed

    private void clearLogMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearLogMenuItemActionPerformed
        clearLog();
    }//GEN-LAST:event_clearLogMenuItemActionPerformed

    private void sourceCodeAreaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_sourceCodeAreaCaretUpdate
        cursorPositionLabel.setText("Cursor position:" + sourceCodeArea.getCaretPosition());
    }//GEN-LAST:event_sourceCodeAreaCaretUpdate

    private void syntaxAnalysButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_syntaxAnalysButtonActionPerformed
        checkSyntax();
    }//GEN-LAST:event_syntaxAnalysButtonActionPerformed

    private void clearLog() {
        logPane.setText("<html>Log info:</html>");
    }

    private void analyzeCode() {
        try {
            String sourceCode = sourceCodeArea.getText();
            int currentCaretPosition = sourceCodeArea.getCaretPosition();
            LexemeType lexemeType = LexemeType.valueOf(
                    lexemeTypeComboBox.getSelectedItem().toString());
            Lexeme requestedLexeme = lexicalAnalyzer.getLexeme(sourceCode, currentCaretPosition, lexemeType);
            sourceCodeArea.requestFocus();
            int newCaretPosition = sourceCodeArea.getCaretPosition() + requestedLexeme.getLength();
            if (newCaretPosition < sourceCode.length()) {
                sourceCodeArea.setCaretPosition(newCaretPosition);
            } else {
                sourceCodeArea.setCaretPosition(sourceCode.length());
            }
            logLexemeMatch(requestedLexeme);
        } catch (LexicalAnalyzerException ex) {
            sourceCodeArea.requestFocus();
            sourceCodeArea.setCaretPosition(sourceCodeArea.getCaretPosition());
            logError(ex.getMessage() + " at position " + sourceCodeArea.getCaretPosition());
        }
    }

    private void logLexemeMatch(Lexeme lexeme) {
        String info = "Lexeme matched: " + lexeme + " at position "
                + (sourceCodeArea.getCaretPosition() - lexeme.getLength());
        logInfo(info);
    }

    public void logInfo(String info) {
        StringBuffer logText = new StringBuffer(logPane.getText());
        int insertionIndex = logText.indexOf("</body>");
        logText.insert(insertionIndex, "<div color='green'>"
                + info + "</div>");
        logPane.setText(logText.toString());
    }

    public void logError(String message) {
        StringBuffer logText = new StringBuffer(logPane.getText());
        int insertionIndex = logText.indexOf("</body>");
        logText.insert(insertionIndex, "<div color='red'>Error: \"" + message + "\"</div>");
        logPane.setText(logText.toString());
    }

    public JTextArea getSourceCodeArea() {
        return sourceCodeArea;
    }


    private void checkSyntax() {
        lexicalAnalyzer.setSourceCodeText(sourceCodeArea.getText());
        LR1Analyzer syntaxAnalyzer = new LR1Analyzer(lexicalAnalyzer);
        try {
            syntaxAnalyzer.parse();
            logInfo("The syntax is correct");
        } catch (Exception ex) {
            logError(ex.getMessage());
        }
    }

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
            java.util.logging.Logger.getLogger(MainLab1JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainLab1JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainLab1JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainLab1JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainLab1JFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem clearLogMenuItem;
    private javax.swing.JLabel cursorPositionLabel;
    private javax.swing.JButton exitButton;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JButton getLexemeButton;
    private javax.swing.JMenuItem getLexemeMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTree jTree1;
    private javax.swing.JComboBox lexemeTypeComboBox;
    private la.LexicalAnalyzer lexicalAnalyzer;
    private javax.swing.JFileChooser loadFileChooser;
    private javax.swing.JMenuItem loadTextMenuItem;
    private javax.swing.JTextPane logPane;
    private javax.swing.JMenu mainMenu;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JTextArea sourceCodeArea;
    private javax.swing.JButton syntaxAnalysButton;
    // End of variables declaration//GEN-END:variables
}
