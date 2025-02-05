package ku.cs.restaurant.service;

import ku.cs.restaurant.entity.Receipt;
import ku.cs.restaurant.repository.ReceiptRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReceiptService {
    private final ReceiptRepository receiptRepository;

    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public Receipt createReceipt(double total) {
        Receipt newReceipt = new Receipt();
        newReceipt.setTotal(total);

        return receiptRepository.save(newReceipt);
    }

    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }

    public Optional<Receipt> getReceiptById(UUID id) {
        return receiptRepository.findById(id);
    }
}

