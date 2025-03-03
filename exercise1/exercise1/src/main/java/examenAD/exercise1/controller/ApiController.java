package examenAD.exercise1.controller;

import examenAD.exercise1.model.Item;
import examenAD.exercise1.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar los ítems de la tienda.
 * Proporciona endpoints para agregar, eliminar, obtener, listar y actualizar ítems,
 * así como para obtener estadísticas generales de la tienda.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ItemRepository itemRepository;

    /**
     * Lista todos los ítems de la tienda.
     * @return Lista de todos los ítems almacenados en la base de datos.
     */
    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * Agrega un nuevo ítem a la base de datos.
     * @param item El ítem a agregar.
     * @return El ítem creado junto con el código de estado 201 (CREATED).
     */
    @PostMapping("/items")
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        itemRepository.save(item);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    /**
     * Elimina un ítem por su ID numérico.
     * @param itemId El ID numérico del ítem a eliminar.
     * @return Código de estado 204 (NO CONTENT) si se eliminó correctamente,
     * o 404 (NOT FOUND) si el ítem no existe.
     */
    @DeleteMapping("/items/delete/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable int itemId) {
        Item item = itemRepository.findByItemId(itemId);
        if (item != null) {
            itemRepository.delete(item);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Obtiene los detalles de un ítem por su ID numérico.
     * @param itemId El ID numérico del ítem a buscar.
     * @return El ítem encontrado o código de estado 404 (NOT FOUND) si no existe.
     */
    @GetMapping("/items/{itemId}")
    public ResponseEntity<Item> findItemById(@PathVariable int itemId) {
        Item item = itemRepository.findByItemId(itemId);
        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Lista los ítems que pertenecen a una categoría específica.
     * @param category La categoría de los ítems.
     * @return Lista de ítems en la categoría proporcionada.
     */
    @GetMapping("/items/category/{category}")
    public ResponseEntity<List<Item>> findItemsByCategory(@PathVariable String category) {
        List<Item> items = itemRepository.findByCategory(category);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * Cambia la categoría de todos los ítems en una categoría específica mediante GET.
     * @param category Categoría actual de los ítems.
     * @param newCategory Nueva categoría para los ítems.
     * @return Lista de ítems actualizados o código de estado 404 si no se encontraron ítems.
     */
    @GetMapping("/items/updateCategory")
    public ResponseEntity<List<Item>> updateCategory(@RequestParam String category, @RequestParam String newCategory) {
        List<Item> items = itemRepository.findByCategory(category);
        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (Item item : items) {
            item.setCategory(newCategory);
            itemRepository.save(item);
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * Muestra estadísticas generales de la tienda.
     * @param minRate (Opcional) Filtra ítems con una puntuación superior a un valor especificado.
     * @return Número total de ítems almacenados y una lista de ítems con puntuación superior al valor proporcionado.
     */
    @GetMapping("/items/stats")
    public ResponseEntity<StoreStats> getStoreStats(@RequestParam(required = false) Double minRate) {
        long totalItems = itemRepository.count();
        List<Item> highRatedItems = itemRepository.findAll().stream()
                .filter(item -> minRate == null || item.getRate() > minRate)
                .toList();

        return new ResponseEntity<>(new StoreStats(totalItems, highRatedItems), HttpStatus.OK);
    }

    /**
     * Clase interna para representar las estadísticas de la tienda.
     */
    static class StoreStats {
        public long totalItems;
        public List<Item> highRatedItems;

        public StoreStats(long totalItems, List<Item> highRatedItems) {
            this.totalItems = totalItems;
            this.highRatedItems = highRatedItems;
        }
    }
}
