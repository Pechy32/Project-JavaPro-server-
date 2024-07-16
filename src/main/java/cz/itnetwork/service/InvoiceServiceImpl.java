package cz.itnetwork.service;

import cz.itnetwork.dto.*;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.dto.mapper.ProductMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.ProductEntity;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import cz.itnetwork.entity.repository.ProductRepository;
import cz.itnetwork.entity.repository.specification.InvoiceSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final PersonRepository personRepository;
    private final InvoiceMapper invoiceMapper;
    private final ProductMapper productMapper;
    private final PersonMapper personMapper;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, ProductRepository productRepository,
                              PersonRepository personRepository, InvoiceMapper invoiceMapper,
                              ProductMapper productMapper, PersonMapper personMapper) {
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
        this.personRepository = personRepository;
        this.invoiceMapper = invoiceMapper;
        this.productMapper = productMapper;
        this.personMapper = personMapper;
    }

    @Override
    public InvoiceDTO create(InvoiceDTO invoiceDTO) {
        PersonEntity buyer = personRepository.findById(invoiceDTO.getBuyer().getId())
                .orElseThrow(EntityNotFoundException::new);
        PersonEntity seller = personRepository.findById(invoiceDTO.getSeller().getId())
                .orElseThrow(EntityNotFoundException::new);
        ProductEntity product = productRepository.findById(invoiceDTO.getProduct().getId())
                .orElseThrow(EntityNotFoundException::new);

        invoiceDTO.setBuyer(personMapper.toDTO(buyer));
        invoiceDTO.setSeller(personMapper.toDTO(seller));
        invoiceDTO.setProduct(productMapper.toDTO(product));

        InvoiceEntity invoiceEntity = invoiceMapper.toEntity(invoiceDTO);
        invoiceRepository.saveAndFlush(invoiceEntity);

        return invoiceMapper.toDTO(invoiceEntity);
    }

    @Override
    public InvoiceDTO getInvoiceById(Long invoiceId) {
        InvoiceEntity fetchedInvoice = fetchInvoiceById(invoiceId);
        return invoiceMapper.toDTO(fetchedInvoice);
    }

    @Override
    public List<InvoiceDTO> getAll(InvoiceFilter filter) {
        InvoiceSpecification invoiceSpecification = new InvoiceSpecification(filter);
        Page<InvoiceEntity> invoiceList = invoiceRepository.findAll(invoiceSpecification,
                PageRequest.of(0, filter.getLimit(), Sort.by(Sort.Direction.DESC, "issued")));

        return invoiceList.stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDTO updateInvoice(Long invoiceId, InvoiceDTO invoiceDTO) {
        InvoiceEntity invoiceEntity = fetchInvoiceById(invoiceId);

        PersonEntity buyer = personRepository.findById(invoiceDTO.getBuyer().getId())
                .orElseThrow(EntityNotFoundException::new);
        PersonEntity seller = personRepository.findById(invoiceDTO.getSeller().getId())
                .orElseThrow(EntityNotFoundException::new);
        ProductEntity product = productRepository.findById(invoiceDTO.getProduct().getId())
                .orElseThrow(EntityNotFoundException::new);

        invoiceEntity.setBuyer(buyer);
        invoiceEntity.setSeller(seller);
        invoiceEntity.setProduct(product);

        invoiceMapper.updateInvoiceEntity(invoiceDTO, invoiceEntity);
        invoiceRepository.saveAndFlush(invoiceEntity);

        InvoiceDTO updatedInvoiceDTO = invoiceMapper.toDTO(invoiceEntity);
        updatedInvoiceDTO.setId(invoiceId);
        updatedInvoiceDTO.setSeller(personMapper.toDTO(seller));
        updatedInvoiceDTO.setBuyer(personMapper.toDTO(buyer));
        updatedInvoiceDTO.setProduct(productMapper.toDTO(product));

        return updatedInvoiceDTO;
    }

    @Override
    public void delete(Long invoiceId) {
        InvoiceEntity invoiceEntity = fetchInvoiceById(invoiceId);
        invoiceRepository.delete(invoiceEntity);
    }

    @Override
    public List<InvoiceStatisticsDTO> getStatsForEachYear() {
        Map<Integer, Long> sumPerYear = new HashMap<>();
        Map<Integer, Map<String, Long>> productSumsPerYear = new HashMap<>();
        Map<Integer, Integer> invoiceCountPerYear = new HashMap<>();

        List<InvoiceEntity> invoiceEntities = invoiceRepository.findAll();

        for (InvoiceEntity invoiceEntity : invoiceEntities) {
            Long price = invoiceEntity.getPrice();
            Integer year = invoiceEntity.getIssued().getYear();
            ProductEntity product = invoiceEntity.getProduct();
            String productName = product.getName();

            sumPerYear.put(year, sumPerYear.getOrDefault(year, 0L) + price);

            productSumsPerYear.putIfAbsent(year, new HashMap<>());
            Map<String, Long> productSums = productSumsPerYear.get(year);
            productSums.put(productName, productSums.getOrDefault(productName, 0L) + price);

            invoiceCountPerYear.put(year, invoiceCountPerYear.getOrDefault(year, 0) + 1);
        }

        List<InvoiceStatisticsDTO> statisticsList = new ArrayList<>();
        for (Integer year : sumPerYear.keySet()) {
            Map<String, Long> productSums = productSumsPerYear.get(year);
            String topProduct = null;
            Long maxProductSum = 0L;

            for (Map.Entry<String, Long> productEntry : productSums.entrySet()) {
                if (productEntry.getValue() > maxProductSum) {
                    maxProductSum = productEntry.getValue();
                    topProduct = productEntry.getKey();
                }
            }

            InvoiceStatisticsDTO dto = new InvoiceStatisticsDTO();
            dto.setYear(year);
            dto.setInvoicesCount((long) invoiceCountPerYear.getOrDefault(year, 0));
            dto.setInvoicesSum(sumPerYear.get(year));
            dto.setTopProduct(topProduct);
            dto.setTopProductSum(maxProductSum);

            statisticsList.add(dto);
        }

        return statisticsList.stream()
                .sorted(Comparator.comparingInt(InvoiceStatisticsDTO::getYear).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> generateInvoices(int count) {
        RandomGenerator generator = new RandomGenerator();
        Random random = new Random();
        List<InvoiceDTO> generatedInvoices = new ArrayList<>();

        List<PersonDTO> persons = personRepository.findByHidden(false)
                .stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());

        List<ProductDTO> products = productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());

        if (persons.size() > 1 && !products.isEmpty()) {
            for (int i = 0; i < count; i++) {
                InvoiceDTO generatedInvoice = new InvoiceDTO();

                PersonDTO buyer = persons.get(random.nextInt(persons.size()));
                PersonDTO seller = buyer;
                while (buyer.equals(seller)) {
                    seller = persons.get(random.nextInt(persons.size()));
                }

                generatedInvoice.setInvoiceNumber(Integer.parseInt(generator.generateRandomNumber(6)));
                generatedInvoice.setIssued(generator.generateRandomDate(LocalDate.of(2013, 9, 1), LocalDate.now()));
                generatedInvoice.setDueDate(generatedInvoice.getIssued().plusDays(14));
                generatedInvoice.setPrice((long) random.nextInt(1000000));
                generatedInvoice.setVat(21);
                generatedInvoice.setProduct(products.get(random.nextInt(products.size())));
                generatedInvoice.setNote("Generovaná faktura");
                generatedInvoice.setBuyer(buyer);
                generatedInvoice.setSeller(seller);

                InvoiceEntity newEntity = invoiceMapper.toEntity(generatedInvoice);
                invoiceRepository.saveAndFlush(newEntity);

                generatedInvoice.setId(newEntity.getId());
                generatedInvoices.add(generatedInvoice);
            }
        }
        return generatedInvoices;
    }

    private InvoiceEntity fetchInvoiceById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(EntityNotFoundException::new);
    }
}
