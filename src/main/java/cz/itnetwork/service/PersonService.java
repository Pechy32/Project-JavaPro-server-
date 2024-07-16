package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticsDTO;

import java.util.List;

public interface PersonService {

    /**
     * Creates a new person
     *
     * @param personDTO Person to create
     * @return newly created person
     */
    PersonDTO addPerson(PersonDTO personDTO);

    /**
     * <p>Sets hidden flag to true for the person with the matching [id]</p>
     * <p>In case a person with the passed [id] isn't found, the method <b>silently fails</b></p>
     *
     * @param id Person to delete
     */
    void removePerson(long id);

    /**
     * Fetches all non-hidden persons
     *
     * @return List of all non-hidden persons
     */
    List<PersonDTO> getAll();

    /**
     * Fetch person by id
     * @param id person id
     * @return person by id
     */
    PersonDTO getPersonById(Long id);

    /**
     * Update person and save it to the database as new entity (original will be set hidden)
     * @param id id of person to be updated
     * @param personDTO data to update
     * @return updated person
     */
    PersonDTO updatePerson(Long id, PersonDTO personDTO);

    /**
     * Get all accepted invoices by person
     * @param identificationNumber Person identification number
     * @return a list of accepted invoices ordered by issued Date descended
     */
    List<InvoiceDTO> getAllPurchasesByPerson(String identificationNumber);

    /**
     * Get all issued invoices by person
     * @param identificationNumber Person identification number
     * @return a list of issued invoices ordered by issued Date descended
     */
    List<InvoiceDTO> getAllSalesByPerson(String identificationNumber);

    /**
     * get statistics for each person in the database
     * @return List of statistics for each person
     */
    List<PersonStatisticsDTO> getPersonStatistics();
}
