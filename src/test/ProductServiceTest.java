package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import dao.ProductDAOImpl;
import model.Product;

/**
 * @author hugog31 oct 2025
 */
public class ProductServiceTest {
	public static void main(String[] args) {
		Product product = new Product(1, "1242", 4, "ikea");
		Product product2 = new Product(1, "12452", 5, "ikeas");

		// 1. Crea un ExecutorService: Un pool de 4 hilos fijos.
		ExecutorService executor = Executors.newFixedThreadPool(4);

		// 2. Define las operaciones de base de datos como tareas Runnable (se
		// ejecutarán en los hilos del pool)

		// Tarea 1: Agregar Producto
		Runnable addTask = () -> {
			ProductDAOImpl addProduct = new ProductDAOImpl();
			System.out.println("Hilo " + Thread.currentThread().getName() + ": Ejecutando addProduct...");
			addProduct.addProduct(product);
		};

		// Tarea 2: Obtener Producto por ID
		Runnable getTask = () -> {
			ProductDAOImpl getProductById = new ProductDAOImpl();
			System.out.println("Hilo " + Thread.currentThread().getName() + ": Ejecutando getProductById...");
			getProductById.getProductById(2);
		};

		// Tarea 3: Actualizar Producto
		Runnable updateTask = () -> {
			ProductDAOImpl updateProduct = new ProductDAOImpl();
			System.out.println("Hilo " + Thread.currentThread().getName() + ": Ejecutando updateProduct...");
			updateProduct.updateProduct(product2);
		};

		// Tarea 4: Deducir Stock
		Runnable deductTask = () -> {
			ProductDAOImpl deductStock = new ProductDAOImpl();
			System.out.println("Hilo " + Thread.currentThread().getName() + ": Ejecutando deductStock...");
			// Usamos un ID diferente para evitar colisiones con las otras tareas de ID 1,
			// si es posible.
			deductStock.deductStock(2, 20);
		};

		// 3. Envía las tareas al ejecutor. El ExecutorService las asignará a sus hilos.
		executor.submit(addTask);
		executor.submit(getTask);
		executor.submit(updateTask);
		executor.submit(deductTask);

		// (Opcional) Tarea 5: Borrar Producto
		/*
		 * Runnable deleteTask = () -> { ProductDAOImpl deleteProduct = new
		 * ProductDAOImpl(); System.out.println("Hilo " +
		 * Thread.currentThread().getName() + ": Ejecutando deleteProduct...");
		 * deleteProduct.deleteProduct(1); }; executor.submit(deleteTask);
		 */

		// 4. Detén el ejecutor y espera a que todas las tareas terminen
		executor.shutdown();
		try {
			// Espera hasta 60 segundos para que terminen todas las tareas
			if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
				System.err.println("¡Algunas tareas no terminaron a tiempo!");
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.err.println("La espera fue interrumpida.");
		}

		System.out.println("Todas las operaciones concurrentes han sido enviadas/completadas.");
	}

}
