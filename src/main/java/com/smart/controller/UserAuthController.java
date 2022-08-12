package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.StatusMessage;

@Controller
@RequestMapping("/user")
public class UserAuthController {

	@Autowired
	private UserRepository userRepository;
	private User user;

	@Autowired
	private ContactRepository contcatRepository;

	@ModelAttribute
	public void commonData(Model model, Principal principal) {
		String userName = principal.getName();
		System.out.println("User name " + userName);
		// get User data
		user = this.userRepository.getUserByUserName(userName);
		System.out.println("USer " + user);
		model.addAttribute("user", user);
	}

	@GetMapping("/index")
	public String userDashboard(Model model, Principal principal) {
		model.addAttribute("title", "User Dashboard-smart contact manager");

		return "normal/user_dashboard";
	}

	// redirecting to add contact page
	@GetMapping("/add-contact")
	public String addContactPage(Model model) {

		model.addAttribute("title", "Add Contact-smart contact manager");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_page";
	}

	// Adding contact handler
	@PostMapping("/process-contact")
	public String addContactProcess(@Valid @ModelAttribute Contact contact, BindingResult result,
			@RequestParam("contactImage") MultipartFile file, HttpSession session) {
		try {

			if (result.hasErrors()) {
				System.out.println(result);
				return "normal/add_contact_page";
			}
			if (file.isEmpty()) {
				contact.setContactImageUrl("contact.png");
			} else {
				// uploading file
				contact.setContactImageUrl(file.getOriginalFilename());

				File saveFile = new ClassPathResource("static/image").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				System.out.println("image is uploaded");
			}

			System.out.println("data" + contact);
			this.user.getContacts().add(contact);
			contact.setUser(user);
			this.userRepository.save(user);

			// message for success
			session.setAttribute("message", new StatusMessage("Your conatct is added successfully", "success"));

			System.out.println("Contact is added");

		} catch (Exception e) {
			System.out.println("error" + e.getMessage());
			e.printStackTrace();
			// message for error
			session.setAttribute("message", new StatusMessage("There is some problem, Please Try AGain!!", "danger"));
		}
		return "normal/add_contact_page";
	}

	// show contact handler with pagination
	@GetMapping("/show-contact/{page}")
	public String viewContacts(@PathVariable("page") int page, Model model) {
		model.addAttribute("title", "view contacts-smart contact manager");
		// List<Contact> contacts = user.getContacts();

		Pageable pageable = PageRequest.of(page, 8);
		Page<Contact> contacts = this.contcatRepository.findConatctsByUser(user.getUserId(), pageable);

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", contacts.getTotalPages());
		model.addAttribute("contacts", contacts);

		return "normal/show_contacts";
	}

	// contact detail page(when a contact is selected from the list
	@GetMapping("/contact-detail/id-{contactId}")
	public String viewContact(@PathVariable("contactId") int contactId, Model model) {

		System.out.println("cid" + contactId);
		model.addAttribute("title", "contact detail-smart contact manager");

		Optional<Contact> optionalContact = this.contcatRepository.findById(contactId);
		Contact contact = optionalContact.get();

		if (user.getUserId() == contact.getUser().getUserId()) {

			model.addAttribute("contact", contact);
		}

		return "normal/contact_detail";
	}

	// contact delete handler
	@GetMapping("/delete/{contactId}")
	public String deleteContact(@PathVariable("contactId") Integer contactId, HttpSession session) {

		Contact contact = this.contcatRepository.findById(contactId).get();

		if (user.getUserId() == contact.getUser().getUserId()) {

			contact.setUser(null);
			this.contcatRepository.deleteById(contactId);
			session.setAttribute("message", new StatusMessage("Contact deleted successfully...", "success"));
		}

		return "redirect:/user/show-contact/0";
	}

	// redirecting to update contact page

	@PostMapping("/update-contact/{contactId}")
	public String updateContact(@PathVariable("contactId") int contactId, Model model) {

		Contact contact = this.contcatRepository.findById(contactId).get();
		model.addAttribute("title", "Update Contact-smart contact manager");
		model.addAttribute("contact", contact);
		return "normal/update_contact";
	}

	// Update process handler

	@PostMapping("/process-update")
	public String updateContactProcess(@ModelAttribute("contact") Contact contact,
			@RequestParam("contactImage") MultipartFile file, HttpSession session) {

		try {

			Contact oldContact = this.contcatRepository.findById(contact.getContactId()).get();

			if (!file.isEmpty()) {
				// deleting old file

				File deleteFile = new ClassPathResource("static/image").getFile();
				File file2 = new File(deleteFile, file.getOriginalFilename());

				file2.delete();

				// Adding new file, if uplo0aded
				File saveFile = new ClassPathResource("static/image").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				contact.setContactImageUrl(file.getOriginalFilename());
			} else {

				contact.setContactImageUrl(oldContact.getContactImageUrl());
			}

			contact.setUser(user);
			this.contcatRepository.save(contact);
			session.setAttribute("message", new StatusMessage("Contact updated Successfully", "success"));

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new StatusMessage("There is some problem.\nPlease Try Again", "danger"));
		}

		return "redirect:/user/contact-detail/id-" + contact.getContactId();
	}

	// User profile handler

	@GetMapping("/profile")
	public String userProfile() {

		return "normal/user_profile";
	}

}
