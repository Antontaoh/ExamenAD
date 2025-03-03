package examenAD.exercise1.repository;

import examenAD.exercise1.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {

    /**
     * Encuentra un ítem por su identificador numérico (itemId).
     * @param itemId El ID numérico del ítem.
     * @return El ítem encontrado o null si no existe.
     */
    Item findByItemId(int itemId);

    /**
     * Encuentra todos los ítems que pertenecen a una categoría específica.
     * @param category La categoría de los ítems.
     * @return Lista de ítems en la categoría dada.
     */
    List<Item> findByCategory(String category);
}
