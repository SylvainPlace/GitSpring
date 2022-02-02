package monprojet.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import monprojet.entity.Country;

// This will be AUTO IMPLEMENTED by Spring 
//

public interface CountryRepository extends JpaRepository<Country, Integer> {
    /**
     * Calcule la poulation d'un pays quand son id est entré
     * 
     * @param id id du pays
     * @return la population du pays
     */
    @Query("SELECT SUM(population) AS pop FROM City GROUP BY country_id HAVING country_id = :id ")
    public Integer populationDuPays(Integer id);

    /**
     * Renvoie la liste des pays
     * 
     * @return les pays
     */
    @Query(value = "SELECT name FROM Country country", nativeQuery = true)
    public List<String> listePays();


    /**
     * Renvoie la liste des pays et leur population
     * 
     * @return La population des pays
     */
    public default Map<String, Integer> listePopPays() {
        Map<String, Integer> m = new HashMap<String, Integer>();
        List<String> liste = listePays();
        for (int i = 0; i < liste.size(); i++) {
            m.put(liste.get(i), populationDuPays(i+1));
        }
        return m;
    }
}
