package ru.petrowich.TestTask;

import ru.petrowich.TestTask.models.Part;
import ru.petrowich.TestTask.services.PartServiceImpl;

public class App {
    public static void main(String[] args) {

        PartServiceImpl userService = new PartServiceImpl();
        for (Part part : userService.findAll()){
            System.out.println(part);
        }

        System.out.println(userService.maxPcNumber());
        System.out.println(userService.getPartById(0));
        System.out.println(userService.getPartById(20));
        System.out.println(userService.getPartById(35));


    }
}
