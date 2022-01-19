package plateforme_educative.controller;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import plateforme_educative.configuration.JwtUtils;
import plateforme_educative.models.Address;
import plateforme_educative.models.Cart;
import plateforme_educative.models.Client;
import plateforme_educative.models.DBFile;
import plateforme_educative.models.PlaceOrder;
import plateforme_educative.models.Product;
import plateforme_educative.models.Thematics;
import plateforme_educative.models.User;
import plateforme_educative.repositorys.AddressRepository;
import plateforme_educative.repositorys.CartRepository;
import plateforme_educative.repositorys.DBFileRepository;
import plateforme_educative.repositorys.PlaceOrderRepository;
import plateforme_educative.repositorys.ProductRepository;
import plateforme_educative.repositorys.ThematicsRepository;
import plateforme_educative.repositorys.UserRepository;
import plateforme_educative.response.CartResponse;
import plateforme_educative.response.PlaceOrderResponse;
import plateforme_educative.response.ProductResponse;
import plateforme_educative.response.UserResponse;
import plateforme_educative.services.ClientService;
import plateforme_educative.services.DBFileStorageService;
import plateforme_educative.services.UserDetailsServiceImpl;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private PlaceOrderRepository placeOrderRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	ClientService clientservice ;
	@Autowired
	UserDetailsServiceImpl userService;
	@Autowired
    private DBFileRepository dbFileRepository;
	@Autowired
    private DBFileStorageService dbFileStorageService;
	   @Autowired
		private ThematicsRepository thematicsRepository;
	
	public User getCurrentUser(Principal p){
		String username = p.getName();
		User u =new User();
		if(u!=null){
			u=userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		}
		return u;
	}
	@GetMapping("/getProducts")
	public ResponseEntity<?> getProduct(){
		return ResponseEntity.ok(new ProductResponse("Your Products are in the List Below",productRepository.findAll()));
	}
	@GetMapping("/addProductToCart")
	public ResponseEntity<?> addProductToCart(@RequestParam("productid") int productid,Principal p){
		User user=getCurrentUser(p);
		Product product=productRepository.findByProductid(productid);
		if(cartRepository.existsByProductId(product.getProductid())){
			return ResponseEntity.ok(new CartResponse("the product you attempt to add to your cart exists already"));
		}
		Cart cart=new Cart();
		cart.setProductId(productid);
		cart.setProductname(product.getProductname());
		cart.setPrice(product.getPrice());
		cart.setQuantity(1);
		cart.setEmail(user.getEmail());
		Date date=new Date();
		cart.setDateAdded(date);
		cartRepository.save(cart);
		return ResponseEntity.ok(new CartResponse("the product has been saved in your cart",cartRepository.findByEmail(user.getEmail())));		
	}
	@GetMapping("/viewCart")
	public ResponseEntity<?> getCart(Principal p){
		User user=getCurrentUser(p);
		return ResponseEntity.ok(new CartResponse("cart view",cartRepository.findByEmail(user.getEmail())));		
	}
	@GetMapping("/updCart")
	public ResponseEntity<?> updateCart(@RequestParam("cartId") int cartId,@RequestParam("quantity")int quantity,Principal p){
		User user=getCurrentUser(p);
		Cart cart=cartRepository.findByCartIdAndEmail(cartId, user.getEmail());
		cart.setQuantity(quantity);
		cartRepository.save(cart);
		return ResponseEntity.ok(new CartResponse("the cart has been updated succeffuly",cartRepository.findByEmail(user.getEmail())));
	}
	@DeleteMapping("/delCart")
	@Transactional
	public ResponseEntity<?> deleteCart(@RequestParam("cartId") int cartId,Principal p){
		User user=getCurrentUser(p);
		cartRepository.deleteByCartIdAndEmail(cartId, user.getEmail());
		return ResponseEntity.ok(new CartResponse("the cart has been deleted succeffuly",cartRepository.findByEmail(user.getEmail())));

	}
	@GetMapping("/placeOrder")
	public ResponseEntity<?> placeOrder(Principal p){
		User user=getCurrentUser(p);
		PlaceOrder po=new PlaceOrder();
		po.setEmail(user.getEmail());
		po.setOrderStatus("pending");
		Date date=new Date();
		po.setOrderDate(date);
		double total=0;
		List<Cart> carts=cartRepository.findAllByEmail(user.getEmail());
		for(Cart cart: carts){
			total=cart.getQuantity()*cart.getPrice();
		}
		po.setTotalCost(total);
		PlaceOrder PO=placeOrderRepository.save(po);
		carts.forEach(c->{
			c.setOrderId(PO.getOrderId());
			cartRepository.save(c);
		});
		
		return ResponseEntity.ok(new PlaceOrderResponse("your order is sent"));
		
	}
	
	@PostMapping("/addAddress")
	public ResponseEntity<?> addAddress(@Valid @RequestBody Address address,Principal p){
		User user=getCurrentUser(p);
		user.setAddress(address);
		address.setUser(user);
		Address adr=addressRepository.saveAndFlush(address);
		return ResponseEntity.ok(new UserResponse("Address saved Succeffuly"));
	}
	@GetMapping("/getAddress")
	public ResponseEntity<?> getAddress(Principal p){
		User user=getCurrentUser(p);
		Address adr=addressRepository.findByUser(user);
		HashMap<String,String> map=new HashMap<>();
		if(adr!=null) {
			map.put("address", adr.getAddress());
			map.put("city", adr.getCity());
			map.put("state", adr.getState());
			map.put("country", adr.getCountry());
			map.put("zipcode", String.valueOf(adr.getZipcode()));
			map.put("phonenumber", adr.getPhonenumber());
			return ResponseEntity.ok(new UserResponse("the address saved succeffuly",map));
		}
		else {
			return ResponseEntity.ok(new UserResponse("aaa",map));
		}
					
	}
	
	
	
	
	
	
	@GetMapping("/FinduserById")
	public User FindclientById() {
		   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   if (principal instanceof UserDetails) {
				String userName = ((UserDetails) principal).getUsername();
				System.err.println(clientservice.Afficher_client_by_name(userName).getClient());
				return clientservice.Afficher_client_by_name(userName);

		   }
		   return null;
	}
	
	   @GetMapping("/FindclientById")
	   public Client FinduserById() {
		   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   if (principal instanceof UserDetails) {
				String userName = ((UserDetails) principal).getUsername();
				System.err.println(clientservice.Afficher_client_by_name(userName).getClient());
				return clientservice.Afficher_client_by_name(userName).getClient();

		   }
		   return null;
	}
	   private static String UPLOADED_FOLDER = System.getProperty("user.dir")+"/src/main/resources";	

	   @PostMapping("/ajoutclt_sansverif/{firstname}/{lastname}/{email}/{adresse}/{telnum}")
	   public Client ajouter_client(@PathVariable(value = "firstname") String firstname ,@PathVariable(value = "lastname") String lastname ,@PathVariable(value = "email") String email ,
				@PathVariable(value = "adresse") String adresse , @PathVariable(value = "telnum") int telnum, @RequestParam("file") MultipartFile file) throws IOException {
		   Client clt = new Client();
		      File dir = new File(UPLOADED_FOLDER);
		      if (!dir.exists())
					dir.mkdirs();
		      System.out.println("c bnsssssssssssssssssssaaaaaas ");
		      File fileToImport = null;
		      if (dir.isDirectory()) {
		    	  System.out.println("c bnssssssssssssssssssssssssssssssssss ");
		    	  try {
			        	
			        	System.out.println("c bn ");
			        	
			            fileToImport = new File(dir + File.separator + file.getOriginalFilename());
			            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileToImport));
			            stream.write(file.getBytes());
			            stream.close();
			        } catch (Exception e) {
			            System.out.println("nnnnnnnnn");
			        }
		      }
		   clt.setAdresseclt(adresse);
		   clt.setEmailclt(email);
		   clt.setTelclt(telnum);
		   clt.setFirstNameclt(firstname);
		   clt.setLastNameclt(lastname);
		   clt.setPhotoclt(file.getOriginalFilename());		   
		   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   if (principal instanceof UserDetails) {
			   String userName = ((UserDetails) principal).getUsername();
			   if (clientservice.Afficher_client_by_name(userName).getClient()!= null) {
				   return (Client) clientservice.edit_client(clientservice.Afficher_client_by_name(userName).getClient(),clt);
			   }
			   else {
					return (Client) clientservice.ajouterclient_sansverif(clientservice.Afficher_client_by_name(userName).getId(),clt);			
			   }
		   }
					return null;
		   
	   }
	   @PostMapping("/ajoutclt_sansverif2/{firstname}/{lastname}/{email}/{adresse}/{telnum}")
		
	 	public Client ajouter_client2(@PathVariable(value = "firstname") String firstname ,@PathVariable(value = "lastname") String lastname ,@PathVariable(value = "email") String email ,
	 			@PathVariable(value = "adresse") String adresse , @PathVariable(value = "telnum") int telnum
	 			 )
	    {
	 	   Client clt = new Client();
	 	   
	 	      
	 	    
	 	   clt.setAdresseclt(adresse);clt.setEmailclt(email);
	 	   clt.setTelclt(telnum);clt.setFirstNameclt(firstname);clt.setLastNameclt(lastname);
	 	   
	 	  
	 	
	 	   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	 		if (principal instanceof UserDetails) {
	 			String userName = ((UserDetails) principal).getUsername();
	 		//	System.err.println(clientservice.Afficher_client_by_name(userName).getClient());
	 			Client c = clientservice.Afficher_client_by_name(userName).getClient();
	 			 clt.setPhotoclt(c.getPhotoclt());
	 			 if (clientservice.Afficher_client_by_name(userName).getClient()!= null) {
					   return (Client) clientservice.edit_client(clientservice.Afficher_client_by_name(userName).getClient(),clt);
				   }
				   else {
						return (Client) clientservice.ajouterclient_sansverif(clientservice.Afficher_client_by_name(userName).getId(),clt);			
				   }
			   }
	 		return null;
	 		
	 	}

	  /* @PutMapping("/edit_client")
	   public Client editClient(@RequestBody Client clt) {
		   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   if (principal instanceof UserDetails) {
				String userName = ((UserDetails) principal).getUsername();
				return clientservice.edit_client(clientservice.Afficher_client_by_name(userName).getClient(),clt);
		   }
		   return null;
	   }*/
	   @PostMapping("/edit_client12/{firstname}/{lastname}/{email}/{adresse}/{telnum}")
	   public Client edit_client(@PathVariable(value = "firstname") String firstname ,@PathVariable(value = "lastname") String lastname ,@PathVariable(value = "email") String email ,
	  			@PathVariable(value = "adresse") String adresse , @PathVariable(value = "telnum") int telnum)
	     {
	  	   Client clt = new Client();
	  	 clt.setAdresseclt(adresse);
	  	 clt.setEmailclt(email);
	  	 clt.setTelclt(telnum);
	  	 clt.setFirstNameclt(firstname);
	  	 clt.setLastNameclt(lastname);
	  	   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	  	 if (principal instanceof UserDetails) {
	  			String userName = ((UserDetails) principal).getUsername();
	  			Client c = clientservice.Afficher_client_by_name(userName).getClient();
	  			return clientservice.edit_client(clientservice.Afficher_client_by_name(userName).getClient(),clt);

	  	 }
		   return null;
	     }
	   
	/*   @GetMapping("/downloadFile/{fileId}")
	    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
	        // Load file from database
	        DBFile dbFile = dbFileStorageService.getFile(fileId);

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
	                .body(new ByteArrayResource(dbFile.getData()));
	    }*/
	   
	   @GetMapping("/files")
	    public List<DBFile> find(){
	    	return dbFileRepository.findAll();
	    }
	   @GetMapping("/productss")
	    public List<Product> findd(){
	    	return productRepository.findAll();
	    }
	   @GetMapping("/thematics")
	    public List<Thematics> getThematics(){
	    	return thematicsRepository.findAll();
	    }
	/*   @GetMapping("files/{filename}")
	   @ResponseBody
	    public ResponseEntity<Resource> downloadFiles(@PathVariable String filename) throws IOException {
	        Resource file = dbFileStorageService.download(filename);
	        Path path = file.getFile().toPath();

	        return ResponseEntity.ok()
	                             .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
	                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
	                             .body(file);
	    }*/
	   @GetMapping("/files/{id}")
	   public ResponseEntity<Resource> getFile(@PathVariable String id){
		   DBFile dbfile = dbFileStorageService.getFile(id);
		   return ResponseEntity.ok().contentType(MediaType.parseMediaType(dbfile.getFileType()))
			        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbfile.getFileName() + "\"")
			        .body(new ByteArrayResource(dbfile.getData()));
	   }
	   
	
}
