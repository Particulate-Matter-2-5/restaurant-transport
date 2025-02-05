package ku.cs.restaurant.controller;

import ku.cs.restaurant.dto.ApiResponse;
import ku.cs.restaurant.entity.Receipt;
import ku.cs.restaurant.service.ReceiptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ReceiptController {
    private final ReceiptService service;

    public ReceiptController(ReceiptService service) {
        this.service = service;
    }

    @GetMapping("/receipt")
    public ResponseEntity<ApiResponse<List<Receipt>>> getReceipts() {
        List<Receipt> receipts = service.getAllReceipts();
        return ResponseEntity.ok(new ApiResponse<>(true, "Receipts retrieved successfully.", receipts));
    }

    @GetMapping("/receipt/{id}")
    public ResponseEntity<ApiResponse<Receipt>> getReceiptById(@PathVariable UUID id) {
        Optional<Receipt> optionalReceipt = service.getReceiptById(id);
        return optionalReceipt.map(receipt ->
                        ResponseEntity.ok(new ApiResponse<>(true, "Receipt retrieved successfully.", receipt)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Receipt not found.", null)));
    }
}
