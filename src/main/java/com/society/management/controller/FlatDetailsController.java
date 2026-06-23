package com.society.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class FlatDetailsController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/flat/{flatNum}")
    public List<Map<String, Object>> getFlatDetails(
            @PathVariable String flatNum,
            @RequestHeader(value = "X-User-Role", required = false) String role,
            @RequestHeader(value = "X-User-Resident-Id", required = false) Integer residentId) {

        if ("RESIDENT".equals(role)) {
            // Check if flatNum matches the resident's flat
            String checkSql = """
                SELECT COUNT(*) FROM flat f
                JOIN resident r ON f.flat_id = r.flat_id
                WHERE f.flat_num = ? AND r.resident_id = ?
                """;
            Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, flatNum, residentId);
            if (count == null || count == 0) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: Residents can only view their own flat details.");
            }
        }

        String sql = """
                SELECT
                    f.flat_id,
                    f.flat_num,
                    r.owner_name,
                    r.contact_num,
                    fl.floor_num,
                    w.wing_name,
                    usr.email,
                    u.electricity_usage,
                    u.electricity_bill_amt,
                    u.water_usage,
                    u.water_bill_amt,
                    u.maintenance_fee,
                    u.property_tax,
                    u.pending_amt,
                    (SELECT b.payment_status FROM bill b WHERE b.flat_id = f.flat_id ORDER BY b.bill_id DESC LIMIT 1) AS bill_status
                FROM flat f
                LEFT JOIN resident r ON f.flat_id = r.flat_id
                LEFT JOIN floor fl ON f.floor_id = fl.floor_id
                LEFT JOIN wing w ON fl.wing_id = w.wing_id
                LEFT JOIN utility u ON f.flat_id = u.flat_id
                LEFT JOIN users usr ON r.resident_id = usr.resident_id
                WHERE f.flat_num = ?
                """;

        return jdbcTemplate.queryForList(sql, flatNum);
    }

    @GetMapping("/flat/resident/{residentId}")
    public List<Map<String, Object>> getFlatDetailsByResidentId(
            @PathVariable Integer residentId,
            @RequestHeader(value = "X-User-Role", required = false) String role,
            @RequestHeader(value = "X-User-Resident-Id", required = false) Integer reqResidentId) {

        if ("RESIDENT".equals(role) && !residentId.equals(reqResidentId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        String sql = """
                SELECT
                    f.flat_id,
                    f.flat_num,
                    r.owner_name,
                    r.contact_num,
                    fl.floor_num,
                    w.wing_name,
                    usr.email,
                    u.electricity_usage,
                    u.electricity_bill_amt,
                    u.water_usage,
                    u.water_bill_amt,
                    u.maintenance_fee,
                    u.property_tax,
                    u.pending_amt,
                    (SELECT b.payment_status FROM bill b WHERE b.flat_id = f.flat_id ORDER BY b.bill_id DESC LIMIT 1) AS bill_status
                FROM flat f
                LEFT JOIN resident r ON f.flat_id = r.flat_id
                LEFT JOIN floor fl ON f.floor_id = fl.floor_id
                LEFT JOIN wing w ON fl.wing_id = w.wing_id
                LEFT JOIN utility u ON f.flat_id = u.flat_id
                LEFT JOIN users usr ON r.resident_id = usr.resident_id
                WHERE r.resident_id = ?
                """;

        return jdbcTemplate.queryForList(sql, residentId);
    }
}