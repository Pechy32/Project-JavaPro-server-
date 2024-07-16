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
package cz.itnetwork.entity.repository;

import cz.itnetwork.dto.PersonStatisticsDTO;
import cz.itnetwork.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonEntity, Long>, PagingAndSortingRepository<PersonEntity, Long> {

    List<PersonEntity> findByHidden(boolean hidden);
    List<PersonEntity> findByIdentificationNumber(String identificationNumber);

    @Query("SELECT NEW cz.itnetwork.dto.PersonStatisticsDTO("
            + "p.id, " // id
            + "p.name, " // name
            + "COALESCE(SUM(CASE WHEN i.seller = p THEN i.price ELSE 0 END), 0), " // revenue
            + "COALESCE(SUM(CASE WHEN YEAR(i.issued) = YEAR(CURRENT_DATE) AND i.seller = p THEN i.price ELSE 0 END), 0), " // current year revenue
            + "COALESCE(SUM(CASE WHEN i.seller = p THEN i.price ELSE 0 END), 0) - COALESCE(SUM(CASE WHEN i.buyer = p THEN i.price ELSE 0 END), 0), " //profit
            + "COALESCE(SUM(CASE WHEN YEAR(i.issued) = YEAR(CURRENT_DATE) AND i.seller = p THEN i.price ELSE 0 END), 0) - COALESCE(SUM(CASE WHEN YEAR(i.issued) = YEAR(CURRENT_DATE) AND i.buyer = p THEN i.price ELSE 0 END), 0), " // current year profit
            + "(SELECT pr.name FROM product pr JOIN pr.productInvoices pi WHERE (pi.seller = p) GROUP BY pr.name ORDER BY SUM(pi.price) DESC LIMIT 1), " // all-time top product
            + "(SELECT SUM(pi.price) FROM product pr JOIN pr.productInvoices pi WHERE pi.seller = p GROUP BY pr.name ORDER BY SUM(pi.price) DESC LIMIT 1), " // all-time top product sum
            + "(SELECT pr.name FROM product pr JOIN pr.productInvoices pi WHERE pi.seller = p AND YEAR(pi.issued) = YEAR(CURRENT_DATE) GROUP BY pr.name ORDER BY SUM(pi.price) DESC LIMIT 1), " // current year top product
            + "(SELECT SUM(pi.price) FROM product pr JOIN pr.productInvoices pi WHERE pi.seller = p AND YEAR(pi.issued) = YEAR(CURRENT_DATE) GROUP BY pr.name ORDER BY SUM(pi.price) DESC LIMIT 1)) " // current year top product sum

            + "FROM person p "
            + "LEFT JOIN invoice i ON p = i.seller OR p = i.buyer "
            + "WHERE p.hidden = false " // hidden persons are excluded
            + "GROUP BY p.id, p.name")
    List<PersonStatisticsDTO> getPersonStatistics();
}
