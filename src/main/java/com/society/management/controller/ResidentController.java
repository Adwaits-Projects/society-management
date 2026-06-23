package com.society.management.controller;

import com.society.management.entity.Resident;
import com.society.management.entity.User;
import com.society.management.repository.ResidentRepository;
import com.society.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ResidentController {

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/residents")
    public List<Map<String, Object>> getAllResidents(
            @RequestHeader(value = "X-User-Role", required = false) String role) {
        
        if ("RESIDENT".equals(role)) {
            throw new RuntimeException("Unauthorized: Residents cannot view all resident details.");
        }

        String sql = """
            SELECT
                r.resident_id AS id,
                r.owner_name AS ownerName,
                r.contact_num AS contactNum,
                r.flat_id AS flatId,
                f.flat_num AS flat,
                fl.floor_num AS floorNum,
                w.wing_name AS wingName,
                u.pending_amt AS pendingAmt,
                usr.email AS email,
                usr.password AS password
            FROM resident r
            LEFT JOIN flat f ON r.flat_id = f.flat_id
            LEFT JOIN floor fl ON f.floor_id = fl.floor_id
            LEFT JOIN wing w ON fl.wing_id = w.wing_id
            LEFT JOIN utility u ON f.flat_id = u.flat_id
            LEFT JOIN users usr ON r.resident_id = usr.resident_id
            """;
        return jdbcTemplate.queryForList(sql);
    }

    @PostMapping("/residents")
    public Map<String, Object> addResident(
            @RequestBody Map<String, Object> payload,
            @RequestHeader(value = "X-User-Role", required = false) String role) {

        if ("RESIDENT".equals(role)) {
            throw new RuntimeException("Unauthorized");
        }

        String ownerName = (String) payload.get("ownerName");
        String contactNum = (String) payload.get("contactNum");
        Integer flatId = Integer.parseInt(payload.get("flatId").toString());
        String email = (String) payload.get("email");
        String password = (String) payload.get("password");

        Resident resident = new Resident();
        resident.setOwnerName(ownerName);
        resident.setContactNum(contactNum);
        resident.setFlatId(flatId);
        resident = residentRepository.save(resident);

        if (email != null && !email.isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password != null && !password.isEmpty() ? password : "password123");
            user.setRole("RESIDENT");
            user.setResidentId(resident.getResidentId());
            userRepository.save(user);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("residentId", resident.getResidentId());
        return response;
    }

    @PutMapping("/residents/{id}")
    public Map<String, Object> editResident(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> payload,
            @RequestHeader(value = "X-User-Role", required = false) String role) {

        if ("RESIDENT".equals(role)) {
            throw new RuntimeException("Unauthorized");
        }

        Resident resident = residentRepository.findById(id).orElseThrow(() -> new RuntimeException("Resident not found"));
        
        String ownerName = (String) payload.get("ownerName");
        String contactNum = (String) payload.get("contactNum");
        Integer flatId = Integer.parseInt(payload.get("flatId").toString());
        String email = (String) payload.get("email");
        String password = (String) payload.get("password");

        resident.setOwnerName(ownerName);
        resident.setContactNum(contactNum);
        resident.setFlatId(flatId);
        residentRepository.save(resident);

        User user = userRepository.findByResidentId(id);
        if (user == null) {
            user = new User();
            user.setResidentId(id);
            user.setRole("RESIDENT");
        }
        user.setEmail(email);
        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        }
        userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }

    @DeleteMapping("/residents/{id}")
    public Map<String, Object> deleteResident(
            @PathVariable Integer id,
            @RequestHeader(value = "X-User-Role", required = false) String role) {

        if ("RESIDENT".equals(role)) {
            throw new RuntimeException("Unauthorized");
        }

        residentRepository.deleteById(id);
        User user = userRepository.findByResidentId(id);
        if (user != null) {
            userRepository.delete(user);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return response;
    }
}
