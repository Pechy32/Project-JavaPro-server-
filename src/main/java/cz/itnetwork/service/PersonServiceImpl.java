package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticsDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonMapper personMapper;
    private final PersonRepository personRepository;
    private final InvoiceMapper invoiceMapper;

    @Autowired
    public PersonServiceImpl(PersonMapper personMapper, PersonRepository personRepository, InvoiceMapper invoiceMapper) {
        this.personMapper = personMapper;
        this.personRepository = personRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public PersonDTO addPerson(PersonDTO personDTO) {
        PersonEntity entity = personMapper.toEntity(personDTO);
        entity = personRepository.save(entity);
        return personMapper.toDTO(entity);
    }

    @Override
    public void removePerson(long personId) {
        try {
            PersonEntity person = fetchPersonById(personId);
            person.setHidden(true);
            personRepository.save(person);
        } catch (NotFoundException ignored) {
            // The contract in the interface states that no exception is thrown if the entity is not found.
        }
    }

    @Override
    public List<PersonDTO> getAll() {
        return personRepository.findByHidden(false)
                .stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PersonDTO getPersonById(Long id) {
        PersonEntity fetchedEntity = fetchPersonById(id);
        return personMapper.toDTO(fetchedEntity);
    }

    @Override
    public PersonDTO updatePerson(Long id, PersonDTO personDTO) {
        PersonEntity fetchedPerson = fetchPersonById(id);
        fetchedPerson.setHidden(true);
        personRepository.save(fetchedPerson);

        personDTO.setId(null);
        PersonEntity updatedEntity = personMapper.toEntity(personDTO);
        personRepository.save(updatedEntity);

        return personMapper.toDTO(updatedEntity);
    }

    @Override
    public List<InvoiceDTO> getAllPurchasesByPerson(String identificationNumber) {
        List<PersonEntity> persons = personRepository.findByIdentificationNumber(identificationNumber);
        return persons.stream()
                .map(PersonEntity::getPurchases)
                .flatMap(List::stream)
                .map(invoiceMapper::toDTO)
                .sorted(Comparator.comparing(InvoiceDTO::getIssued).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> getAllSalesByPerson(String identificationNumber) {
        List<PersonEntity> persons = personRepository.findByIdentificationNumber(identificationNumber);
        return persons.stream()
                .map(PersonEntity::getSales)
                .flatMap(List::stream)
                .map(invoiceMapper::toDTO)
                .sorted(Comparator.comparing(InvoiceDTO::getIssued).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonStatisticsDTO> getPersonStatistics() {
        return personRepository.getPersonStatistics();
    }

    private PersonEntity fetchPersonById(long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with id " + id + " wasn't found in the database."));
    }
}