package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceFilter;
import cz.itnetwork.dto.InvoiceStatisticsDTO;

import java.util.List;

public interface InvoiceService {

    /**
     * Create invoice and save it to the database
     * @param invoiceDTO data send to be saved
     * @return created invoice
     */
    InvoiceDTO create(InvoiceDTO invoiceDTO);

    /**
     * Get invoice by id
     * @param invoiceId invoice id
     * @return invoice by id
     */
    InvoiceDTO getInvoiceById(Long invoiceId);

    /**
     * Get all invoices from the database
     * @return a List of Invoice entities
     */
    List<InvoiceDTO> getAll(InvoiceFilter filter);

    /**
     * update invoice and save in the database
     * @param invoiceId invoice id to be updated
     * @param invoiceDTO invoice data send by client
     * @return updated invoice
     */
    InvoiceDTO updateInvoice(Long invoiceId, InvoiceDTO invoiceDTO);

    /**
     * delete invoice from the database
     * @param invoiceId invoice id to be deleted
     */
    void delete(Long invoiceId);

    /**
     * Get invoices statistics
     * @return invoices statistics per year as a List
     */
    List<InvoiceStatisticsDTO> getStatsForEachYear();

    /**
     * generate a number of random invoices (for testing purposes)
     * @param count - number of invoices to generate
     * @return a List of generated invoices as DTOs
     */
    List<InvoiceDTO> generateInvoices(int count);
}
