package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import dao.ProductDAOImpl;
import model.Product;

/**
 * Prueba concurrente y simple para estresar las operaciones del ProductDAO.
 */
public class ProductServiceTest {
	
    // Contador para generar IDs únicos para la inserción. Empieza en 100.
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(100);

	public static void main(String[] args) {
		
        // Creamos una única instancia del DAO.
        final ProductDAOImpl productDAO = new ProductDAOImpl();
        
        // Creamos un pool de 20 hilos para simular alta concurrencia.
		ExecutorService executor = Executors.newFixedThreadPool(20); 
		
        int numeroIteraciones = 100;
        System.out.println("Enviando " + (numeroIteraciones * 4) + " operaciones concurrentes...");

        // --- BUCLE DE 100 ITERACIONES ---
		for (int i = 0; i < numeroIteraciones; i++) {
            
            final int uniqueId = ID_GENERATOR.incrementAndGet(); 
            
            // Tarea de Inserción (ADD)
            executor.submit(() -> {
                Product newProduct = new Product(uniqueId, "CODE_" + uniqueId, 10, "Warehouse A");
                productDAO.addProduct(newProduct);
            });
            
            // Tarea de Actualización (UPDATE)
            executor.submit(() -> {
                Product productToUpdate = new Product(1, "UPDATED_CODE", 5, "Central"); 
                productDAO.updateProduct(productToUpdate);
            });

            // Tarea de Consulta (GET)
            executor.submit(() -> {
                productDAO.getProductById(1);
            });

            // Tarea de Deducción de Stock (DEDUCT)
            executor.submit(() -> {
                // Deduce 1 unidad del ID 2
                productDAO.deductStock(2, 1); 
            });
		}
        // --- FIN DEL BUCLE ---
        
		// Detener el ejecutor y esperar a que las tareas finalicen.
		executor.shutdown();
		try {
			if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
				System.err.println("¡ALERTA! Algunas tareas no terminaron a tiempo. Forzando el cierre.");
                executor.shutdownNow(); 
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.err.println("La espera fue interrumpida.");
		}

		System.out.println("✅ Tareas completadas/detenidas.");
	}
}

