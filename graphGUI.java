/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tensionalsimulator;
 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.*;
import javax.swing.JOptionPane;

/**
 *
 * @author waheed
 */
public class graphGUI extends javax.swing.JFrame {

    /**
     * Creates new form graphGUI
     */
    public graphGUI() {
        initComponents();
    }
    String[] retJa(){
        String[] k = new String[6];
        try{
        FileReader fr = new FileReader("data.txt");        
        char [] a = new char[50];
        fr.read(a); // reads the content to the array
        int counter = 0;
        String charPart = "";
        for(char c : a)           
            if(c == '\n'){
                k[counter]= charPart;
                charPart = "";
                counter++;
            }
            else{
                //System.out.print(c);
                charPart = charPart+""+c;
            }
        fr.close();
        }
        catch(Exception e){
            k[0] = "";
            k[1] = "";
            k[2] = "";
            k[3] = "";
            k[4] = "";
        }
        return k;
    }
    String sortString(String str){
        int i = str.length();
        int start = 0;
        String nstr;
        if(str.contains("E")){
            int cat = str.indexOf("E");
            nstr = str.substring(0,3)+""+str.substring(cat,i);
        }
        else if(i < 4){
            nstr = str;
        }
        else{
            while(start < i){
                if((str.substring(start,start+1)).equals(".")){
                    break;
                }
                start++;
            }
            start++;
            String ni = Double.toString(start);
            ni = ni.substring(0,ni.length()-2);
            nstr = str.substring(0,1)+"."+str.substring(1,2)+"E"+ni;
        }
        return nstr;
    }
    public void paint(Graphics g){
        String[] inputs = retJa();
        g.setColor(new Color(241,240,240));
        g.fillRect(0, 0, 1200, 600);
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman",Font.PLAIN,18));
        g.drawString("The Stress-Strain Graph for "+inputs[5],50, 50);
        //g.drawString()
        g.drawString("The Load-Extension Graph for "+inputs[5],650, 50);
        g.setFont(new Font("Consolas",Font.PLAIN,10));
        g.setColor(Color.white);
        g.drawLine(650, 100, 650, 500);
        g.drawLine(650, 500, 1050, 500);
        //Getting prepared to plot the graph
        g.fillRect(50,100,400,400);
        g.fillRect(650,100,400,400);
        g.setColor(Color.BLACK);
        Paramcalculator c = new Paramcalculator();
        double yeild = Double.parseDouble(inputs[4]);
        double Tstress = c.Stress(Double.parseDouble(inputs[1]), Double.parseDouble(inputs[2]));
        double Tstrain = c.Strain(Tstress, Double.parseDouble(inputs[0]));
        if (Tstress >= yeild){
            Tstress = (1-0.66)*yeild + 0.66*Double.parseDouble(inputs[0])*Tstrain;
        }
        else{
            Tstress = c.Stress(Double.parseDouble(inputs[1]), Double.parseDouble(inputs[2]));
        }
        int startPixel = 0;
        boolean drawnmarker = false;
        boolean reach = false;
        while(startPixel <= 400){
            if (reach){
                g.setColor(Color.red);
            }
            else{
                g.setColor(Color.BLUE);
            }
            /*double pstrain = (Tstrain*startPixel)/400;
            double pstress = pstrain*inputs[0];
            Double ypixel = new Double(Math.ceil((pstress*400)/Tstress)+100);*/
            //Double ypixel = new Double(Math.ceil((pstress*400)/Tstress)+100);
            /*double pstress = (Tstress*startPixel)/400;
            double pstrain = pstress/Double.parseDouble(inputs[0]);
            Double xpixel = new Double(Math.ceil((pstrain*400)/Tstrain));*/
            /* this is new */
            double pstrain = (Tstrain*startPixel)/400;
            double pstress = pstrain*Double.parseDouble(inputs[0]);
            if(pstress >= yeild){
                pstress = (1-0.66)*yeild + 0.66*Double.parseDouble(inputs[0])*pstrain;
                reach = true;
            }
            else{
                
            }
            Double xpixel = new Double(Math.ceil((pstress*400)/Tstress));
            //Double ypixel = new Double(Math.ceil((pstress*400)/Tstress)+100);
            //g.drawLine(50+startPixel,ypixel.intValue(), 50+startPixel, ypixel.intValue());
            g.drawLine(50+startPixel, 500-xpixel.intValue(),50+startPixel, 500-xpixel.intValue());
            if(!drawnmarker && pstress >= yeild){
                g.setColor(Color.black);
                g.drawOval(50+startPixel-12, 500-xpixel.intValue(), 12, 12);
                g.fillOval(50+startPixel-9, 500-xpixel.intValue()+3, 6, 6);
                drawnmarker = true;
            }
            if((startPixel%50) == 0){
                g.setColor(new Color(241,240,240));
                g.drawLine(50, 500-startPixel, 450, 500-startPixel);
                String pstrstr = Double.toString(pstress);
                String strstrval = Double.toString(pstrain);
                g.setColor(Color.black);
                g.drawLine(50, 500-startPixel, 60, 500-startPixel);
                g.drawString(sortString(pstrstr),8, 500-startPixel);
                g.drawString(sortString(strstrval),startPixel+40, 510);
            }
            startPixel++;
        }
        int iol = 0;
        while(iol <= 400){
            iol = iol +50;
            g.setColor(Color.black);
            g.drawLine(iol, 500, iol, 490);
            g.setColor(new Color(241,240,240));
            g.drawLine(iol, 490, iol, 100);
        }
        g.setColor(Color.BLACK);
        //Drawing the graph axes
        g.drawLine(50,100,50,500);
        g.drawLine(50, 500, 450, 500);
        
        
        //Doing Graph two
        //Horizontal Calibration 
        iol = 0;
        while(iol < 400){
            g.setColor(new Color(241,240,240));
            g.drawLine(650, iol+100, 1050, iol+100);
            g.setColor(Color.black);
            g.drawLine(650, iol+100, 660, iol+100);
            iol = iol +50;
        }
        //Vertical Calibration
        iol = 0;
        while(iol <= 400){
            g.setColor(Color.black);
            g.drawLine(650+iol, 500, 650+iol, 490);
            g.setColor(new Color(241,240,240));
            g.drawLine(650+iol, 490, 650+iol, 100);
            iol = iol +50;
        }
        g.setColor(Color.black);
        g.drawLine(650,100,650,500);
        g.drawLine(650, 500, 1050, 500);
        
        //Program plots the graph
        double loadMax = Double.parseDouble(inputs[1]);
        double Actstrain = c.Strain(Tstress, Double.parseDouble(inputs[0]));
        double maxextension = Double.parseDouble(inputs[3])*0.4;
        g.setColor(Color.BLUE);
        double Chinl;
        int t = 0;
        while (t <= 400){
            double Nload = (t*loadMax)/400;
            double Gstress = c.Stress(Nload, Double.parseDouble(inputs[2]));
            double Strai;
            if(Gstress >= yeild){
                //Strai = (yeild/Double.parseDouble(inputs[0]))+((Gstress-yeild)/0.66);
                Strai = (Gstress - 1 + (0.66*yeild))/(0.66*Double.parseDouble(inputs[0]));
                Chinl = Strai*Double.parseDouble(inputs[3]);
                Double xp = new Double(Math.ceil((Chinl*400)/maxextension));
                g.setColor(Color.red);
                //JOptionPane.showMessageDialog(this,Double.toString(maxextension)+" : "+Double.toString(Chinl));
                g.drawLine(650+xp.intValue(), 500-t,650+xp.intValue(), 500-t);
            }
            else{
                Strai = Gstress/Double.parseDouble(inputs[0]);
                Chinl = Strai*Double.parseDouble(inputs[3]);
                Double xp = new Double(Math.ceil((Chinl*400)/maxextension));
                g.drawLine(650+xp.intValue(), 500-t,650+xp.intValue(), 500-t);
            }
            t++;
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("THE GRAPHS");
        setBackground(new java.awt.Color(241, 240, 240));
        setPreferredSize(new java.awt.Dimension(1200, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 472, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(graphGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(graphGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(graphGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(graphGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new graphGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
