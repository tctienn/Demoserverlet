package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Category;
import model.Product;
import service.CategoryService;
import service.ProductService;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/products")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService = new ProductService();
	private CategoryService categoryService = new CategoryService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}
		switch(action) {
			case "createGet":
				createGet(request, response);
				break;
			case "updateGet":
				updateGet(request, response);
				break;
			case "deleteProduct":
				deleteProduct(request, response);
				break;
			case "findByCategory":
				findByCategory(request, response);
				break;
			default:
				displayAllProduct(request, response);
				break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}
		switch(action) {
			case "createPost":
				createPost(request, response);
				break;
			case "updatePost":
				updatePost(request, response);
				break;
		}
	}
	
	private void displayAllProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Product> products = productService.getProducts();
		request.setAttribute("products", products);
		request.getRequestDispatcher("product/list.jsp").forward(request, response);
	}
	
	private void findByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int categoryId = Integer.parseInt(request.getParameter("cId"));
		List<Product> products = productService.findByCateogory(categoryId);
		request.setAttribute("products", products);
		request.getRequestDispatcher("product/list.jsp").forward(request, response);
	}

	private void createGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Product product = new Product();
		List<Category> categories = categoryService.getCategories();
		request.setAttribute("product", product);
		request.setAttribute("categories", categories);
		request.getRequestDispatcher("product/create.jsp").forward(request, response);
	}

	private void createPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		double price = Double.parseDouble(request.getParameter("price"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		String image = request.getParameter("image");
		int categoryId = Integer.parseInt(request.getParameter("cId"));
		Category category = categoryService.getById(categoryId);
		productService.createProduct(new Product(id, name, price, quantity, image, category));
		response.sendRedirect("/demo_tom9/products");
	}

	private void updateGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Product product = productService.getById(id);
		List<Category> categories = categoryService.getCategories();
		request.setAttribute("product", product);
		request.setAttribute("categories", categories);
		request.getRequestDispatcher("product/update.jsp").forward(request, response);
	}

	private void updatePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		double price = Double.parseDouble(request.getParameter("price"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		String image = request.getParameter("image");
		int categoryId = Integer.parseInt(request.getParameter("cId"));
		Category category = categoryService.getById(categoryId);
		productService.updateProduct(id, new Product(id, name, price, quantity, image, category));
		response.sendRedirect("/demo_tom9/products");
	}

	private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		productService.deleteProduct(id);
		response.sendRedirect("/demo_tom9/products");
	}

}
