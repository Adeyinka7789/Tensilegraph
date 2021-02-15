/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tensionalsimulator;
import java.io.*;
/**
 *
 * @author waheed
 */
public class Paramcalculator {
    double pii = 3.142;
    double Stress(double force, double diameter){
        double stressValue = force/area(diameter);
        return stressValue;
    }
    double Diameter(double force, double stress){
        double diameterValue = Math.sqrt((4*force)/(pii*stress));
        return diameterValue;
    }
    double Force(double stress, double diameter){
        double forceValue = stress*area(diameter);
        return forceValue;
    }
    double LateralStrain(double Diameter, double newDiameter){
        double vi = newDiameter/Diameter;
        return vi;
    }
    double Poisson(double lateralStrain, double axialStrain){
        double v = lateralStrain/axialStrain;
        return v;
    }
    double Strain(double stress, double E){
        double strainValue = stress/E;
        return strainValue;
    }
    double[] FinalLength(double Olength, double strain){
        double[] outs = new double[3];
        double changeinlength = (strain*Olength);
        outs[0] = changeinlength;
        outs[1] = changeinlength + Olength;
        outs[2] = (changeinlength/Olength)*100;
        return outs;
    }
    double area(double diam){
        double reArea = (pii/4)*diam*diam;
        return reArea;
    }
    double surfaceRed(double L, double D, double nL){
        double ORVol = 2*pii*(D/2)*L;
        double r = ORVol/(2*pii*nL);
        return 2*r;
    }
    void filewriter(double E, double F, double Diam, double initl, double ultstress, String someString) throws IOException{
         File file = new File("data.txt");       // creates the file       
         file.createNewFile();       // creates a FileWriter Object       
         FileWriter writer = new FileWriter(file);        // Writes the content to the file       
         writer.write(Double.toString(E)+"\n"+Double.toString(F)+"\n"+Double.toString(Diam)+"\n"+Double.toString(initl)+"\n"+Double.toString(ultstress)+"\n"+someString+"\n"+someString);        
         writer.flush();       
         writer.close();   //Creates a FileReader Object       
         /*FileReader fr = new FileReader(file);        
         char [] a = new char[50];       
         fr.read(a); // reads the content to the array       
         for(char c : a)           
             System.out.print(c); //prints the characters one by one       
         fr.close(); */
    }
}
