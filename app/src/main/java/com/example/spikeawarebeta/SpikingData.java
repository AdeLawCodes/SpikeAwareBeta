package com.example.spikeawarebeta;

public class SpikingData {

    // Data taken from the provided file (Jan 2021–Feb 2025).
    // Month,Year,Reported_Incidents
    public static String[] labels() {
        return new String[] {
                "Jan21","Feb21","Mar21","Apr21","May21","Jun21","Jul21","Aug21","Sep21","Oct21","Nov21","Dec21",
                "Jan22","Feb22","Mar22","Apr22","May22","Jun22","Jul22","Aug22","Sep22","Oct22","Nov22","Dec22",
                "Jan23","Feb23","Mar23","Apr23","May23","Jun23","Jul23","Aug23","Sep23","Oct23","Nov23","Dec23",
                "Jan24","Feb24","Mar24","Apr24","May24","Jun24","Jul24","Aug24","Sep24","Oct24","Nov24","Dec24",
                "Jan25","Feb25"
        };
    }

    public static int[] values() {
        return new int[] {
                316,345,412,476,510,650,918,1200,2500,4861,3900,3200,
                2790,2400,2100,2180,2050,1980,2076,2100,2250,2171,1900,2300,
                1813,1750,1690,1746,1600,1550,1620,1580,1850,1900,1650,1700,
                1450,1400,1380,1350,1300,1280,1320,1350,1600,1550,1400,1450,
                1250,1200
        };
    }

    public static String[] yearLabels() {
        return new String[]{"2021","2022","2023","2024","2025"};
    }

    public static int[] yearTotals() {
        int[] monthly = values();
        int y2021 = 0, y2022 = 0, y2023 = 0, y2024 = 0, y2025 = 0;

        // 2021 = first 12
        for (int i = 0; i < 12; i++) y2021 += monthly[i];

        // 2022 = next 12
        for (int i = 12; i < 24; i++) y2022 += monthly[i];

        // 2023 = next 12
        for (int i = 24; i < 36; i++) y2023 += monthly[i];

        // 2024 = next 12
        for (int i = 36; i < 48; i++) y2024 += monthly[i];

        // 2025 = last 2 (Jan–Feb)
        for (int i = 48; i < monthly.length; i++) y2025 += monthly[i];

        return new int[]{y2021, y2022, y2023, y2024, y2025};
    }
}
