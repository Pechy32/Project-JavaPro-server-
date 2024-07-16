/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */
package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticsDTO;
import cz.itnetwork.service.PersonService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/persons")
    public PersonDTO addPerson(@RequestBody PersonDTO personDTO) {
        return  personService.addPerson(personDTO);
    }

    @GetMapping("/persons")
    public List<PersonDTO> getPersons() {
        return personService.getAll();
    }

    @DeleteMapping("/persons/{personId}")
    public void deletePerson(@PathVariable Long personId, HttpServletResponse response) {
        personService.removePerson(personId);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @GetMapping("persons/{personId}")
    public PersonDTO getPersonById(@PathVariable Long personId){
        return personService.getPersonById(personId);
    }

    @PutMapping("/persons/{personId}")
    public PersonDTO updatePerson(@PathVariable Long personId, @RequestBody PersonDTO personDTO){
        return personService.updatePerson(personId, personDTO);
    }

    @GetMapping("identification/{identificationNumber}/purchases")
    public List<InvoiceDTO> getAllPurchasesByPerson(@PathVariable String identificationNumber){
        return personService.getAllPurchasesByPerson(identificationNumber);
    }

    @GetMapping("identification/{identificationNumber}/sales")
    public List<InvoiceDTO> getAllSalesByPerson(@PathVariable String identificationNumber){
        return personService.getAllSalesByPerson(identificationNumber);
    }

    @GetMapping("/persons/statistics")
    public List<PersonStatisticsDTO> getPersonsStatistics(){
        return personService.getPersonStatistics();
    }

}

