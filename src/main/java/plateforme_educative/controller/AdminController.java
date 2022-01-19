package plateforme_educative.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import plateforme_educative.exception.ResourceNotFoundException;
import plateforme_educative.models.Admin;
import plateforme_educative.models.Client;
import plateforme_educative.models.DBFile;
import plateforme_educative.models.PlaceOrder;
import plateforme_educative.models.Product;
import plateforme_educative.models.ViewOrder;
import plateforme_educative.repositorys.CartRepository;
import plateforme_educative.repositorys.DBFileRepository;
import plateforme_educative.repositorys.PlaceOrderRepository;
import plateforme_educative.repositorys.ProductRepository;
import plateforme_educative.repositorys.ThematicsRepository;
import plateforme_educative.response.MessageResponse;
import plateforme_educative.response.PlaceOrderResponse;
import plateforme_educative.response.ProductResponse;
import plateforme_educative.response.Response;

import plateforme_educative.response.UploadFileResponse;
import plateforme_educative.response.ViewOrderResponse;
import plateforme_educative.services.AdminService;
import plateforme_educative.services.DBFileStorageService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    
    @Autowired
    private DBFileStorageService dbFileStorageService;
    @Autowired
    private DBFileRepository dbFileRepository;
  
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private PlaceOrderRepository placeOrderRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private AdminService adminService;
    //FILES AND TOPICS MANAGEMENT
    
   
	@PostMapping("/uploadFile/{id}")
    public DBFile uploadFile(@RequestParam("file") MultipartFile file,@PathVariable(value="id") Long id) throws ResourceNotFoundException {
        DBFile dbFile = dbFileStorageService.storeFile(file,id);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return dbFile;
    }

  /*  @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }*/

  
    
    @GetMapping("/files")
    public List<DBFile> getAll(){
    	return dbFileRepository.findAll();
    }
    
    @DeleteMapping("/delFile")
	@Transactional
	public ResponseEntity<?> delFile(@RequestParam (name="id") String id){
    	dbFileRepository.deleteById(id);
		return ResponseEntity.ok(new Response("deleted"));		
	}
    
   /* @GetMapping("/files/{thematicId}")
    public List<DBFile> getfilesByThematicId(@PathVariable(value="thematicId") Long thematicId){
    	return dbFileStorageService.getFileByThematicId(thematicId);
    }*/
     @GetMapping("/files/{subthematicId}")
    public List<DBFile> getfilesBySubThematicId(@PathVariable(value="subthematicId") Long subthematicId){
    	return dbFileStorageService.getFileBySubThematicId(subthematicId);
    	 
    }
    
    
    
    
    
    //STORE MANAGEMENT
    
    @PostMapping("/addProduct")
	public ResponseEntity<?> addProduct( 
			   @RequestParam(name="file") MultipartFile prodImage,
			   @RequestParam(name="productname") String productname,
			   @RequestParam(name="price") String price,
			   @RequestParam(name="description") String description,
			   @RequestParam(name="quantity") String quantity) throws IOException{
		Product product=new Product();
		product.setProductname(productname);
		product.setDescription(description);
		product.setPrice(Double.parseDouble(price));
		product.setQuantity(Integer.parseInt(quantity));
		product.setProductimage(prodImage.getBytes());
		productRepository.save(product);
		return ResponseEntity.ok(new ProductResponse("Product Saved Succeffuly",productRepository.findAll()));	
	}
	@GetMapping("/getProducts")
	public ResponseEntity<?> getProducts(){
		return ResponseEntity.ok(new ProductResponse("Your Products are in the List Below",productRepository.findAll()));
	}
	@DeleteMapping("/delProduct")
	@Transactional
	public ResponseEntity<?> delProduct(@RequestParam (name="productid") int productid){
		productRepository.deleteByProductid(productid);
		return ResponseEntity.ok(new ProductResponse("Your Product with ID"+ productid + " deleted succeffuly"
				,productRepository.findAll()));		
	}
	@PutMapping("/updProduct")
	public ResponseEntity<?> updProduct(@RequestParam(name="file",required = false) MultipartFile prodImage,
			   @RequestParam(name="productname") String productname,
			   @RequestParam(name="price") String price,
			   @RequestParam(name="description") String description,
			   @RequestParam(name="quantity") String quantity,
			   @RequestParam (name="productid") int productid) throws IOException{
		Product p1=productRepository.findByProductid(productid);
		Product p2=productRepository.findByProductid(productid);
		if(prodImage!=null){
			p1.setProductname(productname);
			p1.setDescription(description);
			p1.setPrice(Double.parseDouble(price));
			p1.setQuantity(Integer.parseInt(quantity));
			p1.setProductimage(prodImage.getBytes());}
		else{
			p1.setProductname(productname);
			p1.setDescription(description);
			p1.setPrice(Double.parseDouble(price));
			p1.setQuantity(Integer.parseInt(quantity));
			p1.setProductimage(p2.getProductimage());}
		productRepository.save(p1);
		return ResponseEntity.ok(new ProductResponse("Your Product with ID " + productid + " updated succeffuly",p1));
	}
	@PostMapping("/updOrder")
	public ResponseEntity<?> updOrder(@RequestParam(name="orderId") int orderid, @RequestParam(name="orderStatus") String orderstatus){
		PlaceOrder po=placeOrderRepository.findByOrderId(orderid);
		po.setOrderStatus(orderstatus);
		placeOrderRepository.save(po);
		return ResponseEntity.ok(new PlaceOrderResponse("the Order with ID "+orderid+"updated succeffuly", po));	
	}
	@GetMapping("/getOrders")
	public ResponseEntity<?> getOrders(){
		List<ViewOrder> viewOrders=new ArrayList<>();
		List<PlaceOrder> placeOrders=placeOrderRepository.findAll();
		placeOrders.forEach((po) ->{
			ViewOrder vo=new ViewOrder();
			vo.setOrderId(po.getOrderId());
			vo.setOrderBy(po.getEmail());
			vo.setOrderStatus(po.getOrderStatus());
			vo.setProducts(cartRepository.findAllByOrderId(po.getOrderId()));
			viewOrders.add(vo);
		});
		return ResponseEntity.ok(new ViewOrderResponse("this is all the Orders", viewOrders));		
	}
	
	
	
	
	
	
	@GetMapping("/getclientsinactifs")
	public List<Client> getallclientsinactives() {
		return adminService.clientsinactifs();
	}
	 @PostMapping("/adduserclient/{idclient}")
	  public List<Client> adduserclient(@PathVariable(value = "idclient") long idclient)throws IOException {	
		return adminService.Accepter_Client(idclient) ; 
	}
	 @DeleteMapping("/deleteuserclient/{idclient}")	
	  public String removeuserclient(@PathVariable(value = "idclient") long idclient) {  
		return adminService.refuser_client(idclient);
				
			}
	 @GetMapping("/getnbrdemandesinscriptionsnotifications")
	 public int getnbrdemandesinscriptionsnotifications() {
	 return adminService.nbrdemandesinscriptionsnotif();

	 }
	 @PutMapping("/setdemandesnotifications")
	 public int setdemandesnotifications() {
	 return adminService.Demandesnotificationsaffiches();
	 }
	 
	 @GetMapping("/Findadmin")
		public Admin FindadminById() {
		   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				String userName = ((UserDetails) principal).getUsername();
				//System.err.println(clientservice.Afficher_client_by_name(userName).getClient());
				return adminService.Afficher_admin_by_name(userName).getAdmin();
				
			}
			return null;
			
		}
	 @GetMapping("/getAllClient")

	   public List<Client> getAllClients() {
	   return adminService.getAllClient();

	   }
	 
	 @DeleteMapping("/deleteclient/{idclient}")
		
		public String deleteclient(@PathVariable(value = "idclient") long idclient) {
		 
		  
				return adminService.deleteClient(idclient);
				
			}
	 @GetMapping(value = "/apii/image/logo/{image}")
	   public ResponseEntity<InputStreamResource> getclientImage(@PathVariable(value = "image") String image ) throws IOException {

	       ClassPathResource imgFile = new ClassPathResource(image);

	       return ResponseEntity
	               .ok()
	               .contentType(MediaType.IMAGE_PNG)
	               .body(new InputStreamResource(imgFile.getInputStream()));
	   }	

}
