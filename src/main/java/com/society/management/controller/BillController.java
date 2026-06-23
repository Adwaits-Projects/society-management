package com.society.management.controller;

import java.time.LocalDate;
import com.society.management.entity.Bill;
import com.society.management.entity.Resident;
import com.society.management.repository.BillRepository;
import com.society.management.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BillController {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ResidentRepository residentRepository;

    @GetMapping("/bills")
    public List<Bill> getAllBills(
            @RequestHeader(value = "X-User-Role", required = false) String role) {

        if ("RESIDENT".equals(role)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: Residents cannot view all bills.");
        }
        return billRepository.findAll();
    }

    @GetMapping("/bills/flat/{flatId}")
    public List<Bill> getBillsByFlatId(
            @PathVariable Integer flatId,
            @RequestHeader(value = "X-User-Role", required = false) String role,
            @RequestHeader(value = "X-User-Resident-Id", required = false) Integer residentId) {

        if ("RESIDENT".equals(role)) {
            Resident resident = residentRepository.findById(residentId).orElse(null);
            if (resident == null || !resident.getFlatId().equals(flatId)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            }
        }
        return billRepository.findByFlatId(flatId);
    }

    @PostMapping("/bills")
    public Bill addBill(@RequestBody Bill bill) {
        return billRepository.save(bill);
    }

    @PutMapping("/bills/{id}/notify")
    public Bill notifyOwner(@PathVariable Integer id) {

        Bill bill = billRepository.findById(id).orElse(null);

        if (bill != null) {

            bill.setReminderStatus("SENT");

            return billRepository.save(bill);
        }

        return null;
    }

    @PutMapping("/bills/{id}/mark-paid")
    public Bill markPaid(@PathVariable Integer id) {

        Bill bill = billRepository.findById(id).orElse(null);

        if (bill != null) {

            bill.setPaymentStatus("PAID");

            return billRepository.save(bill);
        }

        return null;
    }

    @PutMapping("/bills/{id}/mark-unpaid")
    public Bill markUnpaid(@PathVariable Integer id) {

        Bill bill = billRepository.findById(id).orElse(null);

        if (bill != null) {

            bill.setPaymentStatus("UNPAID");

            return billRepository.save(bill);
        }

        return null;
    }
    @PutMapping("/bills/{id}/pay")
    public Bill payBill(@PathVariable Integer id) {

        Bill bill = billRepository.findById(id).orElse(null);

        if (bill != null) {

            bill.setPaymentStatus("PAID");
            bill.setPaymentDate(LocalDate.now());

            bill.setTransactionId(
                    "TXN" + System.currentTimeMillis()
            );

            bill.setReceiptNumber(
                    "RCP" + System.currentTimeMillis()
            );

            return billRepository.save(bill);
        }

        return null;
    }
}

