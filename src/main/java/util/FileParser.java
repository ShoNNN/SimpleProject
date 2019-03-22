package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FileParser {

    private static String serverNameFile = "ServerName.txt";

    private static ArrayList<HashMap<String, double []>> listOftestInfo = new ArrayList<HashMap<String, double []>>();

    public static ArrayList<HashMap<String, double[]>> getListOftestInfo() {
        return listOftestInfo;
    }

    public static void clearListOftestInfo(){
        getListOftestInfo().clear();
    }

    private static ArrayList<String []> listCsvInfo = new ArrayList<String []>();

    public static ArrayList<String[]> getListCsvInfo() {
        return listCsvInfo;
    }

    public static void clearListCsvInfo(){
        getListCsvInfo().clear();
    }

    public static String[] getServerNameArray() {
        BufferedReader reader;
        String [] serverNameArray = null;
        try {
            reader = new BufferedReader(new FileReader(serverNameFile));
            String line = reader.readLine();
            serverNameArray = line.split(":");
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File " + serverNameFile + " not found!!!");
            ex.printStackTrace();
        }catch (IOException ex) {
            System.out.println("IOException!!!");
            ex.printStackTrace();
        }
        return serverNameArray;
    }

    public void fillListOfTestInfo(String [] args){
        for(int i = 0; i < args.length; i++){
            getListOftestInfo().add(getVarMap(args[i]));
        }
    }

    public void parseCSV(String args){

        String csvFile = args;
        BufferedReader reader = null;
        String line = null;

        try {
            reader = new BufferedReader(new FileReader(csvFile));
            while((line = reader.readLine()) != null){
                String [] array = line.split(";");
                listCsvInfo.add(array);
            }
            reader.close();
        } catch (FileNotFoundException ex){
            System.out.println("File " + csvFile + " not found!!!");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("IOException!!!");
            ex.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException ex){
            ex.printStackTrace();
        }

    }

    public double[] getSystemVarCSV(CharSequence serverName){
        double [] array = new double [4];
        int line = 0;
        try {
            for(int i = 0; i < getListCsvInfo().size() - 1; i++){
                if(getListCsvInfo().get(i)[2].contains(serverName)){
                    if(FileParser.getListCsvInfo().get(i)[2].contains(
                            "Memory\\% Committed Bytes In Use")){
                        array[3] = Double.parseDouble(FileParser.getListCsvInfo().get(i)[4]);
                    } else if (FileParser.getListCsvInfo().get(i)[2].contains(
                            "PhysicalDisk\\_Total\\% Disk Time")){
                        array[1] =  Double.parseDouble(FileParser.getListCsvInfo().get(i)[4]);
                    } else if (FileParser.getListCsvInfo().get(i)[2].contains(
                            "PhysicalDisk\\_Total\\Avg. Disk Queue Length")){
                        array[2] = Double.parseDouble(FileParser.getListCsvInfo().get(i)[4]);
                    } else if (FileParser.getListCsvInfo().get(i)[2].contains(
                            "Processor\\_Total\\% Processor Time")){
                        array[0] = Double.parseDouble(FileParser.getListCsvInfo().get(i)[4]);
                    }
                }
                line++;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Metric value not found at line " + line);
            ex.printStackTrace();
        }
        return array;
    }

    public HashMap<String, double []> getVarMap(CharSequence serverName){
        HashMap<String, double[]> map = new HashMap<String, double []>();
        map.put((String) serverName, getSystemVarCSV(serverName));
        return map;
    }

}
