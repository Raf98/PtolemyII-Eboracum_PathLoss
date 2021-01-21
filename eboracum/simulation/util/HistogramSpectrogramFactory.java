package eboracum.simulation.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public final class HistogramSpectrogramFactory {
	
	public static void newHistogram(int range, String outFile){
		File file = new File("eboracum/wsn/event/util/"+outFile);  
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fw);
			double avg = range/2;
			double stdev = range/6;
			double[] histogram = new double[range+1];
			for (int i=0; i<=range; i++){
				histogram[i] = (1/(stdev*Math.sqrt(2*Math.PI)))*Math.exp(-(((i-avg)*(i-avg))/(2*stdev*stdev)));
				DecimalFormat df = new DecimalFormat("#.#######");
				writer.write(i+";"+df.format(histogram[i]));writer.newLine();
				//System.out.println(i+";"+df.format(histogram[i]));
			}
			writer.flush();
			writer.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void newHistogram(double avg, double stdev, int range, String outFile){
		File file = new File("eboracum/wsn/event/util/"+outFile);  
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fw);
			double[] histogram = new double[range+1];
			for (int i=0; i<=range; i++){
				histogram[i] = (1/(stdev*Math.sqrt(2*Math.PI)))*Math.exp(-(((i-avg)*(i-avg))/(2*stdev*stdev)));
				DecimalFormat df = new DecimalFormat("#.#######");
				writer.write(i+";"+df.format(histogram[i]));writer.newLine();
				//System.out.println(i+";"+df.format(histogram[i]));
			}
			writer.flush();
			writer.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void newPoissonHistogram(int range, String outFile){
		File file = new File("eboracum/wsn/event/util/"+outFile);  
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fw);
			double avg = range/6;
			double stdev = range/6;
			double[] histogram = new double[range+1];
			for (int i=0; i<=range; i++){
				histogram[i] = (1/(stdev*Math.sqrt(2*Math.PI)))*Math.exp(-(((i-avg)*(i-avg))/(2*stdev*stdev)));
				DecimalFormat df = new DecimalFormat("#.#######");
				writer.write(i+";"+df.format(histogram[i]));writer.newLine();
				//System.out.println(i+";"+df.format(histogram[i]));
			}
			writer.flush();
			writer.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void newUniformHistogram(int range, String outFile){
		File file = new File("eboracum/wsn/event/util/"+outFile);  
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fw);
			double[] histogram = new double[range+1];
			for (int i=0; i<=range; i++){
				histogram[i] = 1.0/range;
				DecimalFormat df = new DecimalFormat("#.#######");
				writer.write(i+";"+df.format(histogram[i]));writer.newLine();
				//System.out.println(i+";"+df.format(histogram[i]));
			}
			writer.flush();
			writer.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void newNormalSpectrogram(int rangeX, int rangeY, String outFile){
		File file = new File("eboracum/wsn/event/util/"+outFile);  
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fw);
			double avgX = rangeX/2;
			double stdevX = rangeX/6;
			double avgY = rangeY/2;
			double stdevY = rangeY/6;
			double[][] histogram = new double[rangeX+1][rangeY+1];
			String line = new String();;
			for (int i=0; i<=rangeX; i++){
				if (i == 0) {
					for (int j=0; j<=rangeY; j++){
						writer.write(";"+j);
					}
					writer.newLine();
				}
				for (int j=0; j<=rangeY; j++){
					if (j == 0) line+=i;
					histogram[i][j] = (1/((stdevX+stdevY)*Math.sqrt(2*Math.PI)))*Math.exp(-(   (((i-avgX)*(i-avgX))/(2*stdevX*stdevX))+(((j-avgY)*(j-avgY))/(2*stdevY*stdevY))));
					DecimalFormat df = new DecimalFormat("#.#######");
					line+=";"+df.format(histogram[i][j]);
				}
				writer.write(line);
				writer.newLine();
				line="";
			}
			writer.flush();
			writer.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void newUniformSpectrogram(int rangeX, int rangeY, String outFile){
		File file = new File("eboracum/wsn/event/util/"+outFile);  
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fw);
			String line = new String();
			for (int i=0; i<=rangeX; i++){
				if (i == 0) {
					for (int j=0; j<=rangeY; j++){
						writer.write(";"+j);
					}
					writer.newLine();
				}
				for (int j=0; j<=rangeY; j++){
					if (j == 0) line+=i;
					line+=";"+1;
				}
				writer.write(line);
				writer.newLine();
				line="";
			}
			writer.flush();
			writer.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void newInvertNormalSpectrogram(int rangeX, int rangeY, String outFile){
		File file = new File("eboracum/wsn/event/util/"+outFile);  
		try {
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fw);
			double avgX = rangeX/2;
			double stdevX = rangeX/6;
			double avgY = rangeY/2;
			double stdevY = rangeY/6;
			double[][] histogram = new double[rangeX+1][rangeY+1];
			String line = new String();;
			for (int i=0; i<=rangeX; i++){
				if (i == 0) {
					for (int j=0; j<=rangeY; j++){
						writer.write(";"+j);
					}
					writer.newLine();
				}
				for (int j=0; j<=rangeY; j++){
					if (j == 0) line+=i;
					histogram[i][j] = 1-(1/((stdevX+stdevY)*Math.sqrt(2*Math.PI)))*Math.exp(-(   (((i-avgX)*(i-avgX))/(2*stdevX*stdevX))+(((j-avgY)*(j-avgY))/(2*stdevY*stdevY))));
					DecimalFormat df = new DecimalFormat("#.#######");
					line+=";"+df.format(histogram[i][j]);
				}
				writer.write(line);
				writer.newLine();
				line="";
			}
			writer.flush();
			writer.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BigDecimal factorial(int n)
    {
        if (n == 0) return BigDecimal.ONE;
        return factorial(n-1).multiply(BigDecimal.valueOf(n));
    }
	
	public static void main(String[] args){
		//HistogramSpectrogramFactory.newPoissonHistogram(100, "poissonHist.csv");
		//HistogramSpectrogramFactory.newHistogram(100, "normalHist.csv");
		//HistogramSpectrogramFactory.newUniformHistogram(100, "uniformHist.csv");
	//	HistogramSpectrogramFactory.newHistogram(21.9, 1.5667, 30, "spectSimpleTest.csv");
		HistogramSpectrogramFactory.newHistogram(43.13, 2.95, 100,"spectSimpleTest.csv");
	}

}
