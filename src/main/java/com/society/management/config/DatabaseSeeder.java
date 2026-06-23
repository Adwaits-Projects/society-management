package com.society.management.config;

import com.society.management.entity.*;
import com.society.management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private WingRepository wingRepository;

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private FlatRepository flatRepository;

    @Autowired
    private UtilityRepository utilityRepository;

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        long wingCount = wingRepository.count();
        long floorCount = floorRepository.count();
        long flatCount = flatRepository.count();

        // 6 wings, 12 floors per wing, 288 flats total (so 4 flats per floor)
        // If correct, do not reset database
        if (wingCount == 6 && floorCount == 72 && flatCount == 288) {
            return;
        }

        System.out.println("Township data inconsistent (Wings: " + wingCount + ", Floors: " + floorCount + ", Flats: " + flatCount + "). Resetting to standard township: 6 Wings, 12 Floors/Wing, 288 Flats total...");

        // Delete all dependent child tables to prevent foreign key errors
        visitorRepository.deleteAll();
        billRepository.deleteAll();
        utilityRepository.deleteAll();

        // Only delete resident users, keep admin users
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            if ("RESIDENT".equals(user.getRole())) {
                userRepository.delete(user);
            }
        }

        residentRepository.deleteAll();
        flatRepository.deleteAll();
        floorRepository.deleteAll();
        wingRepository.deleteAll();

        // 1. Seed 6 Wings (A, B, C, D, E, F)
        String[] wingNames = {"A", "B", "C", "D", "E", "F"};
        List<Wing> wings = new ArrayList<>();
        for (String name : wingNames) {
            Wing wing = new Wing();
            wing.setWingName("Wing " + name);
            wings.add(wingRepository.save(wing));
        }

        // 2. Seed Floors (12 per Wing)
        List<Floor> floors = new ArrayList<>();
        for (Wing wing : wings) {
            for (int f = 1; f <= 12; f++) {
                Floor floor = new Floor();
                floor.setFloorNum(f);
                floor.setWingId(wing.getWingId());
                floors.add(floorRepository.save(floor));
            }
        }

        // 3. Seed Flats (4 flats per floor)
        // E.g. Wing A, Floor 1 -> A101, A102, A103, A104
        // Wing A, Floor 12 -> A1201, A1202, A1203, A1204
        List<Flat> flats = new ArrayList<>();
        Random rand = new Random();

        for (Floor floor : floors) {
            // Find parent wing name
            Wing parentWing = wings.stream()
                    .filter(w -> w.getWingId().equals(floor.getWingId()))
                    .findFirst()
                    .orElse(null);
            
            if (parentWing == null) continue;
            String wingLetter = parentWing.getWingName().substring(parentWing.getWingName().length() - 1);

            for (int flatIdx = 1; flatIdx <= 4; flatIdx++) {
                Flat flat = new Flat();
                String flatNum = String.format("%s%d%02d", wingLetter, floor.getFloorNum(), flatIdx);
                flat.setFlatNum(flatNum);
                flat.setFloorId(floor.getFloorId());
                flat = flatRepository.save(flat);
                flats.add(flat);

                // Seed Utility for this flat
                Utility utility = new Utility();
                utility.setFlatId(flat.getFlatId());
                utility.setElectricityUsage(200 + rand.nextInt(150));
                utility.setElectricityBillAmt(1000.0 + rand.nextInt(500));
                utility.setWaterUsage(500 + rand.nextInt(500));
                utility.setWaterBillAmt(300.0 + rand.nextInt(200));
                utility.setMaintenanceFee(2500.0);
                utility.setPropertyTax(1200.0);
                utility.setPendingAmt(0.0);
                utilityRepository.save(utility);
            }
        }

        // 4. Create one default resident user "Adwait" in Flat A101 (Flat index 0 of Wing A, Floor 1)
        // Find flat A101
        Flat a101 = flats.stream().filter(f -> "A101".equals(f.getFlatNum())).findFirst().orElse(flats.get(0));

        Resident resident = new Resident();
        resident.setOwnerName("Adwait");
        resident.setContactNum("9876543210");
        resident.setFlatId(a101.getFlatId());
        resident = residentRepository.save(resident);

        User user = new User();
        user.setEmail("adwait@gmail.com");
        user.setPassword("adwait123");
        user.setRole("RESIDENT");
        user.setResidentId(resident.getResidentId());
        userRepository.save(user);

        // Seed utility pending amt to show unpaid details
        Utility a101Util = utilityRepository.findAll().stream()
                .filter(u -> u.getFlatId().equals(a101.getFlatId()))
                .findFirst()
                .orElse(null);
        if (a101Util != null) {
            a101Util.setPendingAmt(3700.0); // electricity + water + maintenance
            utilityRepository.save(a101Util);
        }

        // Seed a sample unpaid bill for Adwait to show in Dashboard
        Bill bill = new Bill();
        bill.setFlatId(a101.getFlatId());
        bill.setBillMonth("June 2026");
        bill.setElectricityBill(1500.0);
        bill.setWaterBill(500.0);
        bill.setMaintenanceFee(2500.0);
        bill.setPropertyTax(1200.0);
        bill.setLateFee(0.0);
        bill.setTotalAmount(5700.0);
        bill.setPaymentStatus("UNPAID");
        bill.setGeneratedOn(LocalDate.parse("2026-06-01"));
        bill.setDueDate(LocalDate.parse("2026-06-25"));
        bill.setReminderStatus("NONE");
        billRepository.save(bill);

        System.out.println("Township database successfully seeded! seeded flat count: " + flats.size());
    }
}
