package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.ApiResponse;
import ku.cs.restaurant.entity.Financial;
import ku.cs.restaurant.service.FinancialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FinancialController {
    private final FinancialService financialService;

    public FinancialController(FinancialService financialService) {
        this.financialService = financialService;
    }

    @GetMapping("/financial")
    public ResponseEntity<ApiResponse<List<Financial>>> getAll() {
        try {
            List<Financial> financials = financialService.getAll();
            return ResponseEntity.ok(new ApiResponse<>(true, "Financials retrieved successfully.", financials));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, "An error occurred: " + e.getMessage(), null));
        }
    }
}
