package ee.fujitsu.delivery.app;

import ee.fujitsu.delivery.app.dto.Observation;
import ee.fujitsu.delivery.app.dto.Station;
import ee.fujitsu.delivery.app.entity.WeatherEntity;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<WeatherEntity> getMockWeatherEntities() {
        WeatherEntity entity1 = new WeatherEntity();
        WeatherEntity entity2 = new WeatherEntity();
        WeatherEntity entity3 = new WeatherEntity();
        WeatherEntity entity4 = new WeatherEntity();

        entity1.setStationName("Tallinn-Harku");
        entity1.setWmoCode(26038);
        entity1.setWindSpeed(8F);
        entity1.setAirTemperature(25F);
        entity1.setPhenomenon("Rain");
        entity1.setTimeStamp(12345L);

        entity2.setStationName("Tartu-Tõravere");
        entity2.setWmoCode(26242);
        entity2.setWindSpeed(19F);
        entity2.setAirTemperature(0F);
        entity2.setPhenomenon("Mist");
        entity2.setTimeStamp(12345L);

        entity3.setStationName("Pärnu");
        entity3.setWmoCode(41803);
        entity3.setWindSpeed(11F);
        entity3.setAirTemperature(-5F);
        entity3.setPhenomenon("Light Snow");
        entity3.setTimeStamp(12345L);

        entity4.setStationName("Tallinn-Harku");
        entity4.setWmoCode(26038);
        entity4.setWindSpeed(22F);
        entity4.setAirTemperature(-11F);
        entity4.setPhenomenon("Light Snow");
        entity4.setTimeStamp(12350L);

        List<WeatherEntity> entities = new ArrayList<>();
        entities.add(entity1);
        entities.add(entity2);
        entities.add(entity3);
        entities.add(entity4);
        return entities;
    }
}
