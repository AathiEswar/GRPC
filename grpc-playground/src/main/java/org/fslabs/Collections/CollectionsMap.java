package org.fslabs.Collections;

import com.fslabs.collections.Car;
import com.fslabs.collections.Color;
import com.fslabs.collections.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class CollectionsMap {

    private static final Logger logger = LoggerFactory.getLogger(CollectionsMap.class);

    public static void main(String[] args) {
        Car car1 = Car.newBuilder()
                .setManufacture("Mini Cooper")
                .setModel("Country Man")
                .setModelNumber(20)
                .setColor(Color.RED)
                .build();
        Car car2 = Car.newBuilder()
                .setManufacture("Ferrari")
                .setModel("SF90 Stradale")
                .setModelNumber(1)
                .setColor(Color.GREEN)
                .build();

        Inventory inventory1 = Inventory.newBuilder()
                .setName("Aathi House")
                .putInventory(car1.getModelNumber() , car1)
                .putInventory(car2.getModelNumber() , car2)
                .build();

        HashMap<Integer , Car> map = new HashMap<>();
        map.put(car1.getModelNumber() , car1);
        map.put(car2.getModelNumber() , car2);


        Inventory inventory2 = Inventory.newBuilder()
                .setName("Aathi House")
                .putAllInventory(map)
                .build();

        logger.info("{}" , inventory1);
        logger.info("{}" , inventory2);
    }
}
