package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import org.utils.Bike;
import org.utils.Car;
import org.utils.Truck;
import org.utils.Vehicle;

import java.io.File;
import java.io.IOException;

/**
 * Testing Inheritance in Jackson!
 */
public class App {

    public static void main(String[] args) {
        String filePath = "/Users/nikhil/IdeaProjects/jackson_inheritance/src/main/java/org/example/";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        Truck truck = new Truck();
        truck.setBrand("Ford");
        truck.setModel("F40");
        truck.setPayload(500);

        Bike bike = new Bike();
        bike.setHasPillionSeat(true);
        bike.setBrand("Yamaha");
        bike.setModel("R15");

        Car car = new Car();
        car.setBrand("Maruti");
        car.setModel("800");
        car.setBodyType("Hatchback");

        Fleet vehicles = new Fleet();
        vehicles.addVehicles(car);
        vehicles.addVehicles(truck);
        vehicles.addVehicles(bike);

        // Global Default Typing
        PolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType(Bike.class)
                .allowIfSubType(Car.class)
                .allowIfSubType(Truck.class)
                .allowIfSubType(Fleet.class)
                .allowIfSubType("java.util.ArrayList")
                .build();
        PolymorphicTypeValidator typeValidatorWithBaseType = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Vehicle.class)
                .allowIfSubType(Fleet.class)
                .allowIfSubType("java.util.ArrayList")
                .build();


        // Mapping Subtypes without Annotations
        /*mapper.registerSubtypes(new NamedType(Truck.class, "Truck"));
        mapper.registerSubtypes(new NamedType(Car.class, "Car"));
        mapper.registerSubtypes(new NamedType(Bike.class, "Bike"));*/

        mapper.activateDefaultTyping(typeValidator, ObjectMapper.DefaultTyping.NON_FINAL);

        try {
            mapper.writeValue(new File(filePath + "data.json"), vehicles);
            Fleet returnedVehicles = mapper.readValue(new File(filePath + "data.json"), Fleet.class);
            mapper.writeValue(new File(filePath + "deserialized_data.json"), returnedVehicles);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}

