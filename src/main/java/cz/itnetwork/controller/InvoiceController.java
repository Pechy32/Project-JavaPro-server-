package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceFilter;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.service.InvoiceService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("invoices")
    public List<InvoiceDTO> getAllInvoices(InvoiceFilter filter){
        return invoiceService.getAll(filter);
    }

    @PostMapping("/invoices")
    public InvoiceDTO addInvoice(@RequestBody InvoiceDTO invoiceDTO){
        return invoiceService.create(invoiceDTO);
    }

    @GetMapping("invoices/{invoiceId}")
    public InvoiceDTO getInvoiceById(@PathVariable Long invoiceId){
        return invoiceService.getInvoiceById(invoiceId);
    }

    @PutMapping("/invoices/{invoiceId}")
    public InvoiceDTO updateInvoice(@PathVariable Long invoiceId, @RequestBody InvoiceDTO invoiceDTO){
        return invoiceService.updateInvoice(invoiceId, invoiceDTO);
    }

    @DeleteMapping("/invoices/{invoiceId}")
    public void deleteInvoice(@PathVariable Long invoiceId, HttpServletResponse response){
        invoiceService.delete(invoiceId);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @GetMapping("invoices/statistics")
    public List<InvoiceStatisticsDTO> getInvoiceStatistics(){
        return invoiceService.getStatsForEachYear();
    }


    // for testing purposes
    @PostMapping("invoices/generate")
    public List<InvoiceDTO> generateInvoices(@RequestParam("count") int count){
        return invoiceService.generateInvoices(count);
    }
}
