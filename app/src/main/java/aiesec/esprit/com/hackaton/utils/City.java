package aiesec.esprit.com.hackaton.utils;

import java.util.HashMap;

/**
 * Created by Ch on 11/02/2017.
 */

public class City {


    public class LangLat {
        public double Lang;
        public double Lat;
        public LangLat(double Lang,double Lat){
            this.Lang=Lang;
            this.Lat = Lat;
        }
    }
    public static HashMap<String,LangLat> Tunisia = new HashMap<>();

    public City(){

        Tunisia.put("Tunis",new LangLat(10.183105,36.811484));
        Tunisia.put("Bizerte",new LangLat(9.862724299999968,37.2746124));
        Tunisia.put("Mahdia",new LangLat(11.045720999999958,35.5024461));
        Tunisia.put("Monastir",new LangLat(10.811288500000046,35.7642515));
        Tunisia.put("Manouba",new LangLat(10.086326900000017,36.8093284));
        Tunisia.put("Ariana",new LangLat(10.164723299999991,36.86653669999999));
        Tunisia.put("Sousse",new LangLat(10.608394999999973,35.825603));
        Tunisia.put("Sfax", new LangLat(10.766163000000006,34.7478469));
        Tunisia.put("Gafsa",new LangLat(8.775655599999936,34.4311398));
        Tunisia.put("Tataouine", new LangLat(10.450895599999967,32.9210902));
        Tunisia.put("Tozeur", new LangLat(8.122932900000023,33.918534));
        Tunisia.put("Medenine", new LangLat(10.495867800000042,33.3399221));
        Tunisia.put("Sidi Bouzid", new LangLat(9.483939200000009,35.03543859999999));
        Tunisia.put("Kairouan",new LangLat(9.723267299999975,35.6956033));
        Tunisia.put("Jendouba", new LangLat(8.775655599999936,36.5072263));
        Tunisia.put("Kef",new LangLat(8.709578899999997,36.1679648));
        Tunisia.put("Kasserine",new LangLat(8.830762600000071,35.17227159999999));
        Tunisia.put("Beja",new LangLat(9.10134979999998,36.8625526));
        Tunisia.put("Nabeul",new LangLat(10.715422799999942,36.4550658));
        Tunisia.put("Ben Arous",new LangLat(10.23197570000002,36.7435003));
        Tunisia.put("Gabes", new LangLat(10.097522099999992,33.888077));
        Tunisia.put("Kebili",new LangLat(8.836275500000056,33.1245286));
        Tunisia.put("Siliana",new LangLat(9.364533499999993,36.0887208));
        Tunisia.put("Zaghouan",new LangLat(10.14231719999998,36.4091188));



    }



}
