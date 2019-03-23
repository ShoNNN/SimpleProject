package util;

public class Main {

    public static void main(String[] args) {

        FileParser parser = new FileParser();
        ExcelBuilder builder = new ExcelBuilder();

        for(int n = 0; n < args.length; n++){
            parser.parseCSV(args[n]);
            parser.fillListOfTestInfo(FileParser.getServerNameArray());

            builder.fillSheetLoad(n);
            FileParser.clearListCsvInfo();
            FileParser.clearListOftestInfo();
            System.out.println(args[n] + " ok!");
        }

        builder.write("Parsing_results.xlsx");
        System.out.println("Look \"Parsing_results.xlsx\"");
    }


}
