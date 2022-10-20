import Algorithm.*;

import java.util.ArrayList;

public class Experiment {
    private static final String tdPath = "data/fuel/fuel_time_series_data.csv";  // complete time series
    private static final String mdPath = "data/fuel/master.csv";
    private static final String testTdPath = "data/fuel/fuel_test_data.csv";
    private static final int columnCnt = 3;
    private static final int td_len = 200000;
    private static final int md_len = 15000;
    private static final double toleranceRate = 0.5;
    private static final String method = "pt";  // pt/seg
    private static final int thr = 8;  // 0-10
    private static final double noise_rate = 12.0;
    private static double[] std;

    private static final double[] lower_bound = {0.0, 500.0, 0.0};
    private static final double[] upper_bound = {2600.0, 1800.0, 100.0};

    public static Analysis masterRepair(ArrayList<Long> td_time, ArrayList<ArrayList<Double>> td, ArrayList<ArrayList<Double>> test_td, ArrayList<ArrayList<Double>> md) throws Exception {
        System.out.println("\nGlobalMaster");
        MasterRepair masterRepair = new MasterRepair(td_time, test_td, md, columnCnt, std);
        ArrayList<ArrayList<Double>> td_cleaned = masterRepair.getTd_cleaned();
        return new Analysis(columnCnt, td, md, test_td, td_cleaned, toleranceRate);
    }

    public static Analysis knnRepair(ArrayList<Long> td_time, ArrayList<ArrayList<Double>> td, ArrayList<ArrayList<Double>> test_td, ArrayList<ArrayList<Double>> md) {
        System.out.println("\nKNN");
        KNNRepair knnRepair = new KNNRepair(td_time, test_td, md, columnCnt, std);
        ArrayList<ArrayList<Double>> td_cleaned = knnRepair.getTd_cleaned();
        return new Analysis(columnCnt, td, md, test_td, td_cleaned, toleranceRate);
    }

    public static Analysis erRepair(ArrayList<Long> td_time, ArrayList<ArrayList<Double>> td, ArrayList<ArrayList<Double>> test_td, ArrayList<ArrayList<Double>> md) {
        System.out.println("\nERRepair");
        EditingRuleRepair editingRuleRepair = new EditingRuleRepair(td_time, test_td, md, columnCnt);
        ArrayList<ArrayList<Double>> td_cleaned = editingRuleRepair.getTd_cleaned();
        return new Analysis(columnCnt, td, md, test_td, td_cleaned, toleranceRate);
    }

    public static Analysis screenRepair(ArrayList<Long> td_time, ArrayList<ArrayList<Double>> td, ArrayList<ArrayList<Double>> test_td, ArrayList<ArrayList<Double>> md) throws Exception {
        System.out.println("\nSCREEN");
        SCREEN screen = new SCREEN(td_time, test_td, columnCnt);
        ArrayList<ArrayList<Double>> td_cleaned = screen.getTd_cleaned();
        return new Analysis(columnCnt, td, md, test_td, td_cleaned, toleranceRate);
    }

    public static Analysis lsgreedyRepair(ArrayList<Long> td_time, ArrayList<ArrayList<Double>> td, ArrayList<ArrayList<Double>> test_td, ArrayList<ArrayList<Double>> md) throws Exception {
        System.out.println("\nLsGreedy");
        Lsgreedy lsgreedy = new Lsgreedy(td_time, test_td, columnCnt);
        ArrayList<ArrayList<Double>> td_cleaned = lsgreedy.getTd_cleaned();
        return new Analysis(columnCnt, td, md, test_td, td_cleaned, toleranceRate);
    }

    public static Analysis ewmaRepair(ArrayList<Long> td_time, ArrayList<ArrayList<Double>> td, ArrayList<ArrayList<Double>> test_td, ArrayList<ArrayList<Double>> md) {
        System.out.println("\nEWMA");
        EWMARepair ewmaRepair = new EWMARepair(td_time, test_td, columnCnt);
        ArrayList<ArrayList<Double>> td_cleaned = ewmaRepair.getTd_cleaned();
        return new Analysis(columnCnt, td, md, test_td, td_cleaned, toleranceRate);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("start load data");
        LoadData loadData = new LoadData(columnCnt, tdPath, mdPath, td_len, md_len);
        ArrayList<Long> td_time = loadData.getTd_time();
        ArrayList<ArrayList<Double>> td = loadData.getTd();
        ArrayList<ArrayList<Double>> md = loadData.getMd();
        System.out.println("td.size() = " + td.size());
        System.out.println("md.size() = " + md.size());
        System.out.println("finish load data");

        CalStd calStd = new CalStd(columnCnt, td);
        std = calStd.getStd();

        System.out.println("start add noise");
        AddNoise addNoise = new AddNoise(columnCnt, tdPath, testTdPath, lower_bound, upper_bound, method, thr, td_len, noise_rate);
        ArrayList<ArrayList<Double>> test_td = addNoise.getTest_td();
        System.out.println("end add noise");

//        loadData.loadTestTimeSeriesData(testTdPath);
//        ArrayList<ArrayList<Double>> test_td = loadData.getTest_td();

        Analysis gm = masterRepair(td_time, td, test_td, md);
        Analysis knn = knnRepair(td_time, td, test_td, md);
        Analysis er = erRepair(td_time, td, test_td, md);
        Analysis screen = screenRepair(td_time, td, test_td, md);
        Analysis lsgreedy = lsgreedyRepair(td_time, td, test_td, md);
        Analysis ewmaRepair = ewmaRepair(td_time, td, test_td, md);

        gm.writeRepairResultToFile("data/fuel/repair_results/fuel_" + td_len + "_gm.csv");
        knn.writeRepairResultToFile("data/fuel/repair_results/fuel_" + td_len + "_knn.csv");
        er.writeRepairResultToFile("data/fuel/repair_results/fuel_" + td_len + "_er.csv");
        screen.writeRepairResultToFile("data/fuel/repair_results/fuel_" + td_len + "_screen.csv");
        lsgreedy.writeRepairResultToFile("data/fuel/repair_results/fuel_" + td_len + "_lsgreedy.csv");
        ewmaRepair.writeRepairResultToFile("data/fuel/repair_results/fuel_" + td_len + "_ewma.csv");

        System.out.println(
                "\n" + "gm\t\tknn\t\ter\t\tscreen\tlsgreedy\tewma\n" +
                        gm.getRMSE() + "\t" +
                        knn.getRMSE() + "\t" +
                        er.getRMSE() + "\t" +
                        screen.getRMSE() + "\t" +
                        lsgreedy.getRMSE() + "\t\t" +
                        ewmaRepair.getRMSE() +
                        ""
        );
    }
}