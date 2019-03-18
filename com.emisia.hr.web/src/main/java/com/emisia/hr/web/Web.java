package com.emisia.hr.web;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.servlet.annotation.WebServlet;

import com.emisia.hr.model.EmployeeDto;
import com.emisia.hr.model.Employee_Exit;
import com.emisia.hr.model.JobCandidate;
import com.emisia.hr.model.JobPost;
import com.emisia.hr.model.Project;
import com.emisia.hr.model.User;
import com.emisia.hr.service.user.HRService;
import com.emisia.hr.service.userImp.AdminServiceImp;
import com.emisia.hr.service.userImp.HRServiceImp;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
@Theme("vvv")
public class Web extends UI {

	private Button _addNew;
	private Button _employeeExit;

	private Button _save;
	private Button _cancel;
	private Button _saveExitEmplyee;
	private Button _cancelExitEmployee;

	private Button _btnLogOut;

	Window popUpWindow;
	Window _windowEdit;

	Table table;

	com.vaadin.ui.TextField tf;
	com.vaadin.ui.TextField tfHR;

	VerticalLayout adminLayout;
	HorizontalLayout adminHorizontalLayout1;
	HorizontalLayout adminHorizontalLayout2;

	VerticalLayout HRLayout;
	HorizontalLayout HRHorizontalLayout1;
	HorizontalLayout HRHorizontalLayout2;
	HorizontalLayout HRHorizontalLayout3;

	VerticalLayout employeExitLayout;
	HorizontalLayout employeeExitHorizontalLayout;

	VerticalLayout loginForm;
	FormLayout addNewForm;

	com.vaadin.ui.Panel panelSuccess = new com.vaadin.ui.Panel("Successfully added user!");
	com.vaadin.ui.Panel panelFailure = new com.vaadin.ui.Panel("Failure to add user!");

	Label labelSuccess;
	Label labelFailure;

	HashSet<EmployeeDto> possibleEmployees;
	ArrayList<Integer> selectedEmployees;

	static String _role = null;
	private com.vaadin.ui.TextField _tfUsername = new com.vaadin.ui.TextField();
	private PasswordField _pfPassword = new PasswordField();

	private TextField tfFirstName;
	private TextField tfLastName;
	private DateField dfStartDate;
	private TextField tfEmail;
	private TextField tfAddress;
	private TextField tfPhone;
	private TextField tfEducation;
	private TextField tfWorkExpiriance;
	private TextField tfInterests;
	private TextField tfMobile;
	private TextField tfHobbies;
	private TextField tfPosition;	
	private ComboBox cbGender;
	private TextArea _taLeaving;
	private Upload upload;

	private static com.vaadin.ui.TextField _firstName;
	private static com.vaadin.ui.TextField _lastName;
	private static com.vaadin.ui.TextField _userName;
	private static PasswordField _password;
	private static ListSelect _selectRole;
	private static com.vaadin.ui.TextField _passEdit;
	private static com.vaadin.ui.TextField _email;
	private String address;	

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = Web.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		showLoginForm();
	}

	private FormLayout getLoginForm() {

		FormLayout _form = new FormLayout();
		HorizontalLayout _vlButton = new HorizontalLayout();
		VerticalLayout _vlText = new VerticalLayout();
		Panel _panelSign = new Panel("Please sign into your HrTracking account.");

		_panelSign.setWidth("340px");
		_panelSign.setStyleName(DESIGN_ATTR_PLAIN_TEXT);
		_tfUsername = new com.vaadin.ui.TextField("Username:");
		_tfUsername.setHeight("30");
		_tfUsername.setNullRepresentation("");
		_tfUsername.setIcon(FontAwesome.USER);
		_vlText.addComponent(_tfUsername);

		_pfPassword = new PasswordField("Password:");

		_pfPassword.setNullRepresentation("");
		_pfPassword.setIcon(FontAwesome.LOCK);

		_vlText.addComponent(_pfPassword);

		Button _btnLogin = new Button("Sign In", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				final AdminServiceImp AdminServiceImp = new AdminServiceImp();
				String _username = (String) _tfUsername.getConvertedValue();
				String _password = (String) _pfPassword.getConvertedValue();
				String _role = null;
				if (AdminServiceImp.loginFormFindUser(_username, _password) == true) {

					Notification.show("WELCOME " + _username);
					try {
						_role = AdminServiceImp.getRole(_username, _password);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if (_role.equals("HR") || _role.equals("hr")) {
						_role = "hr";
						showHRPage();
					} else if (_role.equals("ADMIN") || _role.equals("admin")) {
						_role = "admin";
						showAdminPage();
					} else if (_role.equals("TEAM_LEADER") || _role.equals("team_leader")) {
						_role = "team_leader";
					} else if (_role.equals("EMPLOYEE") || _role.equals("employee")) {
						_role = "empolyee";
					}
				}

				else if (AdminServiceImp.loginFormFindUser(_username, _password) == false) {
					Notification.show("WARNING This account does not exist ! ");
					_tfUsername.clear();
					_pfPassword.clear();
				}

			}
		});
		_btnLogin.setClickShortcut(KeyCode.ENTER);
		_btnLogin.setIcon(FontAwesome.SIGN_IN);
		_vlButton.addComponent(_btnLogin);

		_form.addComponents(_panelSign, _vlText, _vlButton);

		_form.setStyleName("logIn");
		return _form;

	}

	private void showLoginForm() {
		FormLayout _formlogin = getLoginForm();
		_formlogin.setMargin(true);
		_formlogin.setSizeFull();
		setContent(_formlogin);
	}

	private void showAdminPage() {

		adminLayout = new VerticalLayout();
		adminLayout.setMargin(true);
		adminLayout.setSizeFull();
		setContent(adminLayout);

		addNewUser();
		addLogOutToAdmin();
		showAllUsers();
	}

	private void showEmployeeExitPage() {
		employeExitLayout = new VerticalLayout();
		employeExitLayout.setMargin(true);
		employeExitLayout.setSizeFull();
		setContent(employeExitLayout);
		showAllUsersExit();
	}

	private void addNewUser() {

		_addNew = new Button("Add New User");
		_addNew.setEnabled(true);

		popUpWindow = new Window("Please enter all necessary things for adding new user");

		adminHorizontalLayout1 = new HorizontalLayout();

		addNewForm = getAddUserPanel();

		_addNew.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// newUser.setDisableOnClick(false);
				getSession().setAttribute("_userName", null);
				refresh();

				adminHorizontalLayout1.addComponent(addNewForm);
				popUpWindow.setHeight("500px");
				popUpWindow.setWidth("400px");
				popUpWindow.center();
				popUpWindow.setIcon(FontAwesome.USERS);
				popUpWindow.setContent(addNewForm);
				popUpWindow.setModal(true);
				addWindow(popUpWindow);
			}
		});

		adminHorizontalLayout1.addComponent(_addNew);
		adminHorizontalLayout1.setExpandRatio(_addNew, 1);
	}

	private void addLogOutToAdmin() {

		_btnLogOut = new Button("Log Out");
		_btnLogOut.setEnabled(true);
		_btnLogOut.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				showLogOut();
			}
		});

		adminHorizontalLayout1.addComponent(_btnLogOut);
		adminHorizontalLayout1.setExpandRatio(_btnLogOut, 1);
	}

	public void refresh() {
		_firstName.clear();
		_lastName.clear();
		_userName.clear();
		_password.clear();
		_selectRole.clear();
	}

	private FormLayout getAddUserPanel() {
		HorizontalLayout layoutButton = new HorizontalLayout();
		FormLayout _form = new FormLayout();
		_email = new com.vaadin.ui.TextField("Email: ");
		_email.setHeight("30");
		_email.setRequired(false);
		_firstName = new com.vaadin.ui.TextField("First name: ");
		_firstName.setHeight("30");
		_firstName.setRequired(true);
		_lastName = new com.vaadin.ui.TextField("Last name: ");
		_lastName.setHeight("30");
		_lastName.setRequired(true);
		_userName = new com.vaadin.ui.TextField("Username: ");
		_userName.setHeight("30");
		_userName.setRequired(true);
		_password = new PasswordField("Password: ");
		_password.setRequired(true);
		_password.setValue("");
		_password.setNullRepresentation("");
		_selectRole = new ListSelect("Select role");
		_selectRole.addItems("ADMIN", "HR", "TEAM_LEADER", "EMPLOYEE");
		_selectRole.setNullSelectionAllowed(false);
		_selectRole.setRows(1);
		_selectRole.setRequired(true);
		_save = new Button("Save", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				String _firstNameA = (String) _firstName.getConvertedValue();
				String _lastNameA = (String) _lastName.getConvertedValue();
				String _userNameA = (String) _userName.getConvertedValue();
				String _passwordA = (String) _password.getConvertedValue();
				String _emailA = (String) _email.getConvertedValue();
				if (_firstName.isEmpty() || _lastName.isEmpty() || _userName.isEmpty() || _password.isEmpty()) {
					Notification.show("Please enter all fields marked ", "", Type.ERROR_MESSAGE);
				}

				else

					try {
						if (new AdminServiceImp().addUser(_firstNameA, _lastNameA, _userNameA, _passwordA, _emailA,
								_selectRole.getValue().toString(), true) == true) {
								Notification.show("NEW USER IS ADDED: " + "First name: " + _firstNameA
										+ " Last name: " + _lastNameA + " Username: " + _userNameA, "", Type.HUMANIZED_MESSAGE);
						} else if (new AdminServiceImp().addUser(_firstNameA, _lastNameA, _userNameA, _passwordA,
								"test@live.com", "hr", true) == false) {
							Notification.show(_userNameA + " already exists, please enter a new username: ","",Type.WARNING_MESSAGE);
							
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

				refresh();
				_firstNameA = null;
				_lastNameA = null;
				_userNameA = null;
				_emailA = null;
				searchRefresh("");

			}
		});
		_save.setIcon(FontAwesome.SAVE);
		_cancel = new Button("Cancel", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				refresh();
				popUpWindow.close();
				UI.getCurrent().removeWindow(popUpWindow);
				_addNew.setEnabled(true);

			}
		});

		layoutButton.addComponents(_save, _cancel);
		_form.addComponents(_firstName, _lastName, _userName, _password, _email, _selectRole, layoutButton);

		return _form;
	}

	private FormLayout getEditUserPanel(final int id, String password, String role) {
		HorizontalLayout layoutButton = new HorizontalLayout();
		FormLayout _form = new FormLayout();
		_passEdit = new com.vaadin.ui.TextField("Password: ");
		_passEdit.setRequired(true);
		_passEdit.setValue(password);
		_passEdit.setNullRepresentation("");
		_selectRole = new ListSelect("Select role: ");
		_selectRole.addItems("ADMIN", "HR", "TEAM_LEADER", "EMPLOYEE");
		_selectRole.setNullSelectionAllowed(false);
		_selectRole.setRows(1);
		_selectRole.setValue(role);

		_save = new Button("Save", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				String _passwordA = _passEdit.getConvertedValue().toString();
				new AdminServiceImp().editUsers(id, _passwordA, _selectRole.getValue().toString());
				popUpWindow.close();
				UI.getCurrent().removeWindow(popUpWindow);
				if (tf != null) {
					tf.setValue("");
				}
				if (tfHR != null) {
					tfHR.setValue("");
				}
				searchRefresh("");
			}
		});

		_save.setIcon(FontAwesome.SAVE);
		layoutButton.addComponents(_save);
		_form.addComponents(_passEdit, _selectRole, layoutButton);
		return _form;
	}

	public void refreshEmp() {
		tfFirstName.clear();
		tfLastName.clear();
		dfStartDate.clear();
		tfEmail.clear();
		tfAddress.clear();
		tfPhone.clear();
		tfEducation.clear();
		tfWorkExpiriance.clear();
		tfInterests.clear();
	}

	private FormLayout getAddEmployeePanel() {
		HorizontalLayout layoutButton = new HorizontalLayout();
		FormLayout form = new FormLayout();

		tfFirstName = new TextField("First Name");
		form.addComponent(tfFirstName);

		tfLastName = new TextField("Last Name");
		form.addComponent(tfLastName);

		dfStartDate = new DateField("Start Date");
		form.addComponent(dfStartDate);

		tfEmail = new TextField("Email");
		tfEmail.addValidator(new EmailValidator("The email address isn't valid."));
		form.addComponent(tfEmail);

		tfAddress = new TextField("Address");
		form.addComponent(tfAddress);

		tfPhone = new TextField("Phone");
		form.addComponent(tfPhone);

		tfEducation = new TextField("Education");
		form.addComponent(tfEducation);

		tfWorkExpiriance = new TextField("Work Expiriance");
		form.addComponent(tfWorkExpiriance);

		tfInterests = new TextField("Interests");
		form.addComponent(tfInterests);

		upload = new Upload("Upload CV", new Receiver() {
			File file;

			public FileOutputStream receiveUpload(String filename, String mimeType) {
				FileOutputStream fos = null;
				try {
					new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/cv/").mkdir();
					file = new File(
							VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/cv/" + filename);
					fos = new FileOutputStream(file);
					address = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/cv/" + filename;
				} catch (final java.io.FileNotFoundException e) {
					System.out.println(e.getMessage());
					return null;
				}
				return fos;
			}
		});

		upload.setCaption("Upload CV");
		upload.setButtonCaption("Upload CV");
		upload.setImmediate(true);
		upload.addSucceededListener(new SucceededListener() {
			public void uploadSucceeded(SucceededEvent event) {
				Notification.show("Upload succeeded!");
			}
		});

		upload.addFailedListener(new FailedListener() {
			public void uploadFailed(FailedEvent event) {
				Notification.show("Upload failed!");
			}
		});

		form.addComponent(upload);

		_save = new Button("Save", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				new HRServiceImp().addNewEmployee(tfFirstName.getValue(), tfLastName.getValue(), dfStartDate.getValue(),
						tfEmail.getValue(), tfPhone.getValue(), tfEducation.getValue(), tfWorkExpiriance.getValue(),
						tfInterests.getValue(), "", null, address, true);
				popUpWindow.close();
				UI.getCurrent().removeWindow(popUpWindow);
				refreshEmp();
				searchEmpRefresh("");

			}
		});

		_cancel = new Button("Cancel", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				popUpWindow.close();
				UI.getCurrent().removeWindow(popUpWindow);
				_addNew.setEnabled(true);
				refreshEmp();
			}
		});

		layoutButton.addComponents(_save, _cancel);
		form.addComponents(layoutButton);
		return form;
	}

	private void showAllUsersExit() {
		employeeExitHorizontalLayout = new HorizontalLayout();
		VerticalLayout _verticalLayout = new VerticalLayout();
		Button _btnBack = new Button("Back", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				showHRPage();
			}
		});
		_btnBack.setIcon(FontAwesome.BACKWARD);
		employeeExitHorizontalLayout.setSizeFull();
		Panel pan = new Panel("EMPLOYEE EXIT TABLE");
		pan.setWidth("350px");
		pan.setIcon(FontAwesome.TABLE);
		table = new Table(" ", generateContentExit());
		table.sort();
		table.setSizeFull();
		table.setPageLength(10);
		table.setSelectable(true);
		table.setImmediate(true);
		table.refreshRowCache();
		table.setContainerDataSource(table.getContainerDataSource());

		table.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {

			}

			@SuppressWarnings("unused")
			public void valueChange1(ValueChangeEvent event) {
				// TODO Auto-generated method stub

			}
		});

		employeeExitHorizontalLayout.addComponent(table);
		employeeExitHorizontalLayout.setExpandRatio(table, 1);
		_verticalLayout.addComponents(_btnBack, pan);

		employeExitLayout.addComponent(_verticalLayout);
		employeExitLayout.addComponent(employeeExitHorizontalLayout);
		employeExitLayout.setExpandRatio(employeeExitHorizontalLayout, 1);

	}

	private IndexedContainer generateContentExit() {

		IndexedContainer cont = new IndexedContainer();
		cont.addContainerProperty("Id", String.class, null);
		cont.addContainerProperty("First Name", String.class, null);
		cont.addContainerProperty("Last Name", String.class, null);
		// cont.addContainerProperty("Username", String.class, null);
		cont.addContainerProperty("Time of disabling", String.class, null);
		cont.addContainerProperty("Reason leaving", String.class, null);
		cont.addContainerProperty("Edit reason leaving", Button.class, null);

		addContentExit(cont);
		return cont;
	}

	@SuppressWarnings("unchecked")
	private void addContentExit(IndexedContainer cont) {

		HashSet<Employee_Exit> empolyeeExit = new HRServiceImp().showAllDisabledEmployees();

		Iterator<Employee_Exit> iterator = empolyeeExit.iterator();

		String generate;
		int data = 0;
		while (iterator.hasNext()) {
			data++;
			final Employee_Exit empolyeExit = iterator.next();
			Object itemId = cont.addItem();

			generate = empolyeExit.getId() + "";
			cont.getItem(itemId).getItemProperty("Id").setValue(generate);

			generate = empolyeExit.getFirstName();
			cont.getItem(itemId).getItemProperty("First Name").setValue(generate);

			generate = empolyeExit.getLastName();
			cont.getItem(itemId).getItemProperty("Last Name").setValue(generate);

			// generate = empolyeExit.getUserName();
			// cont.getItem(itemId).getItemProperty("Username").setValue(generate);

			generate = empolyeExit.getTimeOfDisable() + "";
			cont.getItem(itemId).getItemProperty("Time of disabling").setValue(generate);

			generate = empolyeExit.getReasonLeaving();
			cont.getItem(itemId).getItemProperty("Reason leaving").setValue(generate);
			Button button = new Button("Edit reason leaving");
			button.setData(data);
			button.addClickListener(new ClickListener() {
				public void buttonClick(ClickEvent event) {
					int id = Integer.parseInt(
							(String) table.getContainerProperty(event.getButton().getData(), "Id").getValue());

					String _reason = table.getContainerProperty(event.getButton().getData(), "Reason leaving")
							.getValue().toString();

					FormLayout _formlogin = getEditEmployeExitResaonLeaving(id, _reason);
					_formlogin.setMargin(true);
					_formlogin.setSizeFull();
					setContent(_formlogin);
					System.out.println("ID JE " + id);
					System.out.println("Reason JE " + _reason);
				}
			});

			cont.getItem(itemId).getItemProperty("Edit reason leaving").setValue(button);

		}
	}

	private FormLayout getEditEmployeExitResaonLeaving(final int id, String reasonLeaving) {
		HorizontalLayout layoutButton = new HorizontalLayout();
		FormLayout _form = new FormLayout();
		_taLeaving = new TextArea("Enter reason leaving :");
		_taLeaving.setRequired(true);
		_taLeaving.setValue(reasonLeaving);
		_taLeaving.setNullRepresentation("");
		_taLeaving.setWidth("300px");
		_taLeaving.setHeight("300px");
		_saveExitEmplyee = new Button("Save", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				String _leaving = _taLeaving.getConvertedValue().toString();
				new AdminServiceImp().editEmployeExit(id, _leaving);
				showEmployeeExitPage();
			}
		});

		_saveExitEmplyee.setIcon(FontAwesome.SAVE);

		_cancelExitEmployee = new Button("Cancel", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				showEmployeeExitPage();

			}
		});
		layoutButton.addComponents(_saveExitEmplyee, _cancelExitEmployee);
		_form.addComponents(_taLeaving, layoutButton);
		return _form;
	}

	private void showAllUsers() {
		adminHorizontalLayout2 = new HorizontalLayout();
		adminHorizontalLayout2.setSizeFull();

		table = new Table("Users", generateSearchedContent(""));
		table.sort();

		tf = new com.vaadin.ui.TextField();
		tf.setInputPrompt("Search  ");

		tf.addTextChangeListener(new TextChangeListener() {
			public void textChange(TextChangeEvent event) {
				searchRefresh(event.getText());
			}
		});
		tf.setTextChangeEventMode(TextChangeEventMode.EAGER);

		table.setSizeFull();
		table.setPageLength(10);
		table.setSelectable(true);
		table.setImmediate(true);

		table.addValueChangeListener(new ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				/*
				 * if(event.getProperty().getValue()!=null) { if
				 * (event.getProperty().getValue() == null) {
				 * form.setVisible(false); return; }
				 * 
				 * // Bind the form to the selected item //
				 * form.setItemDataSource(table.getItem(table.getValue()));
				 * 
				 * // table.getItem(table.getValue());
				 * form.setItemDataSource(table.getItem(table.getValue()));
				 * form.setVisible(true);
				 * 
				 * // The form was opened for editing an existing item
				 * table.setData(null);
				 * 
				 * 
				 * }
				 */

			}

			@SuppressWarnings("unused")
			public void valueChange1(ValueChangeEvent event) {
				// TODO Auto-generated method stub

			}
		});

		adminHorizontalLayout2.addComponent(table);
		adminHorizontalLayout2.setExpandRatio(table, 1);

		adminHorizontalLayout1.addComponent(tf);
		adminLayout.addComponent(adminHorizontalLayout1);
		adminLayout.addComponent(adminHorizontalLayout2);
		adminLayout.setExpandRatio(adminHorizontalLayout2, 1);
	}

	private void searchRefresh(String term) {
		adminHorizontalLayout2.removeComponent(table);
		table = new Table("Users", generateSearchedContent(term));
		table.setSizeFull();
		table.setPageLength(10);
		table.setSelectable(true);
		table.setImmediate(true);
		adminHorizontalLayout2.addComponent(table);
	}

	private IndexedContainer generateSearchedContent(String term) {

		IndexedContainer cont = new IndexedContainer();

		cont.addContainerProperty("Id", String.class, null);
		cont.addContainerProperty("First Name", String.class, null);
		cont.addContainerProperty("Last Name", String.class, null);
		cont.addContainerProperty("Username", String.class, null);
		cont.addContainerProperty("Password", String.class, null);
		cont.addContainerProperty("Email", String.class, null);
		cont.addContainerProperty("Role", String.class, null);
		cont.addContainerProperty("Enable", CheckBox.class, null);
		cont.addContainerProperty("Edit", Button.class, null);

		addSearchedContent(cont, term);
		return cont;
	}

	@SuppressWarnings("unchecked")
	private void addSearchedContent(IndexedContainer cont, String term) {

		HashSet<User> users = new AdminServiceImp().showSearchedUsers(term);

		Iterator<User> iterator = users.iterator();

		String generate;

		int data = 0;

		while (iterator.hasNext()) {
			data++;
			final User user = iterator.next();
			Object itemId = cont.addItem();

			generate = user.getId() + "";
			cont.getItem(itemId).getItemProperty("Id").setValue(generate);

			generate = user.getFirstName();
			cont.getItem(itemId).getItemProperty("First Name").setValue(generate);

			generate = user.getLastName();
			cont.getItem(itemId).getItemProperty("Last Name").setValue(generate);

			generate = user.getUserName();
			cont.getItem(itemId).getItemProperty("Username").setValue(generate);

			generate = user.getPassword();
			cont.getItem(itemId).getItemProperty("Password").setValue(generate);

			generate = user.getEmail();
			cont.getItem(itemId).getItemProperty("Email").setValue(generate);

			generate = user.getRole().toString();
			cont.getItem(itemId).getItemProperty("Role").setValue(generate);

			Boolean enabled = user.isEnabled();
			CheckBox checkBox = new CheckBox();
			checkBox.setValue(enabled);
			checkBox.addValueChangeListener(new ValueChangeListener() {
				public void valueChange(ValueChangeEvent event) {

					AdminServiceImp AdminServiceImp = new AdminServiceImp();

					boolean value = (Boolean) event.getProperty().getValue();

					if (value)
						AdminServiceImp.enableUser(user.getId());
					else
						AdminServiceImp.disableUser(user.getId());
				}
			});
			cont.getItem(itemId).getItemProperty("Enable").setValue(checkBox);

			Button button = new Button("Edit User");
			button.setData(data);
			button.addClickListener(new ClickListener() {
				public void buttonClick(ClickEvent event) {
					int id = Integer.parseInt(
							(String) table.getContainerProperty(event.getButton().getData(), "Id").getValue());
					String password = table.getContainerProperty(event.getButton().getData(), "Password").getValue()
							.toString();
					String role = (String) table.getContainerProperty(event.getButton().getData(), "Role").getValue();
					FormLayout page = getEditUserPanel(id, password, role);
					adminHorizontalLayout1.addComponent(page);
					popUpWindow.center();
					popUpWindow.setContent(page);
					addWindow(popUpWindow);
				}
			});

			cont.getItem(itemId).getItemProperty("Edit").setValue(button);
		}
	}

	protected void showHRPage() {

		HRLayout = new VerticalLayout();
		HRLayout.setSizeFull();
		HRLayout.setMargin(true);
		HRLayout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		setContent(HRLayout);

		HRHorizontalLayout1 = new HorizontalLayout();
		HRHorizontalLayout1.setSizeFull();
		HRHorizontalLayout1.setDefaultComponentAlignment(Alignment.TOP_CENTER);

		HRHorizontalLayout2 = new HorizontalLayout();
		HRHorizontalLayout2.setSizeFull();
		HRHorizontalLayout2.setDefaultComponentAlignment(Alignment.TOP_CENTER);

		HRHorizontalLayout3 = new HorizontalLayout();
		HRHorizontalLayout3.setSizeFull();
		HRHorizontalLayout3.setDefaultComponentAlignment(Alignment.TOP_CENTER);

		createMenuActionBarForHR();
	}

	private void createMenuActionBarForHR() {
		MenuBar menuBar = new MenuBar();
		
		HRHorizontalLayout1.addComponent(menuBar);
		HRHorizontalLayout1.setExpandRatio(menuBar, 1);

		MenuBar.Command command = new MenuBar.Command() {
			MenuItem previous = null;
			public void menuSelected(MenuItem selectedItem) {

				if (previous != null)
					previous.setStyleName(null);
				selectedItem.setStyleName("highlight");
				previous = selectedItem;

				if (selectedItem.getText().equals("Projects"))
					showHRProjects();
				else if (selectedItem.getText().equals("Employees"))
					showHREmployees();

				else if (selectedItem.getText().equals("Job Posts"))
					showJobPosts();
				else if (selectedItem.getText().equals("Job Candidates"))
					showJobCandidates();
				else if (selectedItem.getText().equals("Job Interviews"))
					showJobInterviews();

				else if (selectedItem.getText().equals("Log Out"))
					showLogOut();

			}
		};

		menuBar.addItem("Projects", null, command);
		menuBar.addItem("Employees", null, command);

		MenuItem recruitment = menuBar.addItem("Recruitment", null, null);
		recruitment.addItem("Job Posts", null, command);
		recruitment.addSeparator();
		recruitment.addItem("Job Candidates", null, command);
		recruitment.addSeparator();
		recruitment.addItem("Job Interviews", null, command);

		menuBar.addItem("Log Out", null, command);

		Label spacer = new Label("&nbsp;", ContentMode.HTML);
		spacer.setHeight("100px");
		HRHorizontalLayout1.addComponent(spacer);

		HRLayout.addComponent(HRHorizontalLayout1);
		HRLayout.setExpandRatio(HRHorizontalLayout1, (float) 0.4);
	}

	private void showHRProjects() {
		if (tfHR != null)
			HRLayout.removeComponent(tfHR);
		if (_addNew != null)
			HRLayout.removeComponent(_addNew);
		if (_employeeExit != null)
			HRLayout.removeComponent(_employeeExit);

		HRLayout.removeComponent(HRHorizontalLayout2);
		HRLayout.removeComponent(HRHorizontalLayout3);

		HRHorizontalLayout2 = new HorizontalLayout();
		HRHorizontalLayout2.setSizeFull();
		HRHorizontalLayout2.setMargin(true);
		HRHorizontalLayout3 = new HorizontalLayout();
		HRHorizontalLayout3.setWidth("100%");
		HRHorizontalLayout3.setStyleName("jobProjectMenu");
		HRHorizontalLayout3.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

		table = new Table("Projects", generateContentForSearchedProjects(""));
		table.setSizeFull();
		table.setPageLength(10);
		table.setSelectable(true);
		table.setImmediate(true);

		tfHR = new com.vaadin.ui.TextField();
		tfHR.setInputPrompt("Search");
		tfHR.setWidth("100%");

		tfHR.addTextChangeListener(new TextChangeListener() {
			public void textChange(TextChangeEvent event) {
				searchProjectsRefresh(event.getText());
			}
		});
		tfHR.setTextChangeEventMode(TextChangeEventMode.EAGER);

		_addNew = new Button("Add New Project");
		_addNew.setWidth("70%");
		
		popUpWindow = new Window("Please enter all necessary things for adding new project");

		_addNew.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				
				addNewForm = getAddProjectPanel();
				getSession().setAttribute("_userName", null);
				popUpWindow.setHeight("600px");
				popUpWindow.setWidth("600px");
				popUpWindow.center();
				popUpWindow.setContent(addNewForm);
				popUpWindow.setModal(true);
				addWindow(popUpWindow);
			}
		});

		_addNew.setEnabled(true);

		HRHorizontalLayout2.addComponent(table);
		HRHorizontalLayout2.setExpandRatio(table, 1);

		HRHorizontalLayout3.addComponent(tfHR);
		HRHorizontalLayout3.addComponent(_addNew);

		HRLayout.addComponents(HRHorizontalLayout3, HRHorizontalLayout2);
		HRLayout.setExpandRatio(HRHorizontalLayout2, 1);
	}

	private FormLayout getAddProjectPanel() {
		HorizontalLayout layoutButton = new HorizontalLayout();
		FormLayout form = new FormLayout();

		final TextField projectName = new TextField("Project Name");
		form.addComponent(projectName);

		final TextField clientName = new TextField("Client Name");
		form.addComponent(clientName);

		final DateField startDate = new DateField("Start Date");
		form.addComponent(startDate);

		final DateField endDate = new DateField("End Date");
		form.addComponent(endDate);

		final TextField projectDescription = new TextField("Project Description");
		form.addComponent(projectDescription);

		final ComboBox projectEmployees = new ComboBox();

		projectEmployees.setFilteringMode(FilteringMode.CONTAINS);
		projectEmployees.setPageLength(5);
		projectEmployees.setInputPrompt("Select Employees");
		projectEmployees.setNullSelectionAllowed(false);

		final HRServiceImp hrService = new HRServiceImp();

		possibleEmployees = hrService.showPossibleEmployees();

		System.out.println(possibleEmployees.toString());

		selectedEmployees = new ArrayList<Integer>();
		selectedEmployees = new ArrayList<Integer>();

		Iterator<EmployeeDto> iterator = possibleEmployees.iterator();

		while (iterator.hasNext()) {
			EmployeeDto employee = iterator.next();

			projectEmployees.addItem(employee.getId() + " | " + employee.getFirstName() + " " + employee.getLastName());
		}

		projectEmployees.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -5188369735622627751L;

			public void valueChange(ValueChangeEvent event) {
				if (projectEmployees.getValue() != null) {
					String str = (String) projectEmployees.getValue();
					int id = Integer.parseInt(str.split(" ")[0].trim());
					selectedEmployees.add(id);

					projectEmployees.setValue(null);
					projectEmployees.setInputPrompt("Select another");
				}
			}
		});
		projectEmployees.setImmediate(true);

		form.addComponent(projectEmployees);

		_save = new Button("Save", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				hrService.addNewProjects(projectName.getValue(), clientName.getValue(), startDate.getValue(),
						endDate.getValue(), projectDescription.getValue());

				Iterator<Integer> iterator = selectedEmployees.iterator();

				while (iterator.hasNext()) {
					int employeeID = iterator.next();
					hrService.addToTeam(projectName.getValue(), employeeID);
				}

				hrService.editPossibleEmployees(selectedEmployees, projectName.getValue());

				popUpWindow.close();
				UI.getCurrent().removeWindow(popUpWindow);
				searchProjectsRefresh("");
			}
		});

		_cancel = new Button("Cancel", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				popUpWindow.close();
				UI.getCurrent().removeWindow(popUpWindow);
			}
		});

		layoutButton.addComponents(_save, _cancel);
		form.addComponents(layoutButton);
		return form;
	}


	private void searchProjectsRefresh(String term) {
		HRHorizontalLayout2.removeComponent(table);
		table = new Table("Projects", generateContentForSearchedProjects(term));
		table.setSizeFull();
		table.setPageLength(10);
		table.setSelectable(true);
		table.setImmediate(true);
		HRHorizontalLayout2.addComponent(table);
	}

	private IndexedContainer generateContentForSearchedProjects(String term) {

		IndexedContainer container = new IndexedContainer();

		container.addContainerProperty("Id", String.class, null);
		container.addContainerProperty("Project Name", String.class, null);
		container.addContainerProperty("Client Name", String.class, null);
		container.addContainerProperty("Starting Date", String.class, null);
		container.addContainerProperty("Ending Date", String.class, null);
		container.addContainerProperty("Description", String.class, null);
		container.addContainerProperty("Employees", Button.class, null);

		addSearchedContentForProjectsTable(container, term);

		return container;
	}

	@SuppressWarnings("unchecked")
	private void addSearchedContentForProjectsTable(IndexedContainer cont, String term) {

		HashSet<Project> projects = new HRServiceImp().showSearchedProjects(term);

		Iterator<Project> iterator = projects.iterator();

		String generate;

		while (iterator.hasNext()) {
			final Project project = iterator.next();
			Object itemId = cont.addItem();

			generate = project.getId() + "";
			cont.getItem(itemId).getItemProperty("Id").setValue(generate);

			generate = project.getProjectName();
			cont.getItem(itemId).getItemProperty("Project Name").setValue(generate);

			generate = project.getClientName();
			cont.getItem(itemId).getItemProperty("Client Name").setValue(generate);

			generate = project.getProjectStartDate() + "";
			cont.getItem(itemId).getItemProperty("Starting Date").setValue(generate);

			generate = project.getProjectEndDate() + "";
			cont.getItem(itemId).getItemProperty("Ending Date").setValue(generate);

			generate = project.getProjectDescription();
			cont.getItem(itemId).getItemProperty("Description").setValue(generate);

			final Button button = new Button("Employees");
			button.setData(project.getId());

			button.addClickListener(new ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					showAllProjectEmployeesTable((int) event.getButton().getData());
				}
			});

			cont.getItem(itemId).getItemProperty("Employees").setValue(button);
		}
	}

	protected void showAllProjectEmployeesTable(int id) {
		HashSet<EmployeeDto> employees;
		employees = new HRServiceImp().showAllProjectEmployees(id);

		Table employeeTable = new Table();
		employeeTable.addContainerProperty("FirstName", String.class, null);
		employeeTable.addContainerProperty("LastName", String.class, null);
		employeeTable.setPageLength(20);
		Iterator<EmployeeDto> iterator = employees.iterator();

		int j = 0;
		while (iterator.hasNext()) {
			EmployeeDto employeeDto = iterator.next();
			employeeTable.addItem(new Object[] { employeeDto.getFirstName(), employeeDto.getLastName() }, j++);
		}

		Window popUpTable = new Window();
		popUpTable.setSizeUndefined();
		VerticalLayout verticalLayout = new VerticalLayout();

		verticalLayout.addComponent(employeeTable);
		popUpTable.setContent(verticalLayout);
		popUpTable.setModal(true);
		UI.getCurrent().addWindow(popUpTable);
	}

	private void showHREmployees() {

		if (_addNew != null)
			HRLayout.removeComponent(_addNew);
		if (_employeeExit != null)
			HRLayout.removeComponent(_employeeExit);
		if (tfHR != null)
			HRLayout.removeComponent(tfHR);

		HRLayout.removeComponent(HRHorizontalLayout2);
		HRLayout.removeComponent(HRHorizontalLayout3);

		HRHorizontalLayout2 = new HorizontalLayout();
		HRHorizontalLayout2.setSizeFull();
		HRHorizontalLayout2.setMargin(true);
		HRHorizontalLayout2.setImmediate(true);
		HRHorizontalLayout3 = new HorizontalLayout();
		HRHorizontalLayout3.setWidth("100%");
		HRHorizontalLayout3.setStyleName("employeeMenu");
		HRHorizontalLayout3.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

		tfHR = new com.vaadin.ui.TextField();
		tfHR.setInputPrompt("Search");
		tfHR.setWidth("100%");

		tfHR.addTextChangeListener(new TextChangeListener() {
			public void textChange(TextChangeEvent event) {
				searchEmpRefresh(event.getText());
			}
		});
		tfHR.setTextChangeEventMode(TextChangeEventMode.EAGER);

		_employeeExit = new Button("Employee Exit", new ClickListener() {
			public void buttonClick(ClickEvent event) {
				showEmployeeExitPage();

			}
		});
		
		_employeeExit.setWidth("70%");

		table = new Table("Employees", generateContentForSearchedEmployees(""));

		table.setSizeFull();
		table.setPageLength(10);
		table.setSelectable(true);
		table.setImmediate(true);

		addNewForm = getAddEmployeePanel();
		_addNew = new Button("Add New Employee");
		_addNew.setWidth("90%");
		
		popUpWindow = new Window("Please enter all necessary things for adding a new employee");

		_addNew.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				getSession().setAttribute("_userName", null);
				popUpWindow.setHeight("500px");
				popUpWindow.setWidth("400px");
				popUpWindow.center();
				popUpWindow.setIcon(FontAwesome.USERS);
				popUpWindow.setContent(addNewForm);
				popUpWindow.setModal(true);
				addWindow(popUpWindow);

			}
		});

		_addNew.setEnabled(true);

		HRHorizontalLayout2.addComponent(table);
		HRHorizontalLayout2.setExpandRatio(table, 1);

		HRHorizontalLayout3.addComponents(tfHR, _addNew, _employeeExit);
		HRLayout.addComponents(HRHorizontalLayout3, HRHorizontalLayout2);
		HRLayout.setExpandRatio(HRHorizontalLayout2, 1);
	}

	private void searchEmpRefresh(String term) {
		HRHorizontalLayout2.removeComponent(table);
		table = new Table("Employees", generateContentForSearchedEmployees(term));
		table.setSizeFull();
		table.setPageLength(10);
		table.setSelectable(true);
		table.setImmediate(true);
		HRHorizontalLayout2.addComponent(table);
	}

	private IndexedContainer generateContentForSearchedEmployees(String term) {

		IndexedContainer container = new IndexedContainer();

		container.addContainerProperty("Id", String.class, null);
		container.addContainerProperty("First Name", String.class, null);
		container.addContainerProperty("Last Name", String.class, null);
		container.addContainerProperty("Project Name", String.class, null);
		container.addContainerProperty("Position", String.class, null);
		container.addContainerProperty("Enabled", CheckBox.class, null);

		addContentForSearchedEmployeesTable(container, term);

		return container;
	}

	@SuppressWarnings("unchecked")
	private void addContentForSearchedEmployeesTable(IndexedContainer cont, String term) {

		HashSet<EmployeeDto> employees = new HRServiceImp().showSearchedEmployees(term);

		Iterator<EmployeeDto> iterator = employees.iterator();

		String generate;

		while (iterator.hasNext()) {
			final EmployeeDto employee = iterator.next();
			Object itemId = cont.addItem();

			generate = employee.getId() + "";
			cont.getItem(itemId).getItemProperty("Id").setValue(generate);

			generate = employee.getFirstName();
			cont.getItem(itemId).getItemProperty("First Name").setValue(generate);

			generate = employee.getLastName();
			cont.getItem(itemId).getItemProperty("Last Name").setValue(generate);

			generate = employee.getProjectName();
			cont.getItem(itemId).getItemProperty("Project Name").setValue(generate);

			generate = employee.getPositon();
			cont.getItem(itemId).getItemProperty("Position").setValue(generate);

			Boolean enabled = employee.getEnabled();
			CheckBox checkBox = new CheckBox();
			checkBox.setValue(enabled);
			checkBox.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(ValueChangeEvent event) {

					HRService hrService = new HRServiceImp();

					boolean value = (Boolean) event.getProperty().getValue();

					if (value)
						hrService.enableEmployee(employee.getId());
					else
						hrService.disableEmployee(employee.getId());
				}
			});
			cont.getItem(itemId).getItemProperty("Enabled").setValue(checkBox);
		}

	}

	private void showLogOut() {
		showLoginForm();
	}

	protected void showJobInterviews() {
		if (tfHR != null)
			HRLayout.removeComponent(tfHR);
		if (_addNew != null)
			HRLayout.removeComponent(_addNew);
		if (_employeeExit != null)
			HRLayout.removeComponent(_employeeExit);

		HRLayout.removeComponent(HRHorizontalLayout2);
		HRLayout.removeComponent(HRHorizontalLayout3);

		HRHorizontalLayout2 = new HorizontalLayout();
		HRHorizontalLayout2.setSizeFull();
		HRHorizontalLayout3 = new HorizontalLayout();
		HRHorizontalLayout3.setSizeFull();
	}

	protected void showJobCandidates() {
		if (tfHR != null)
			HRLayout.removeComponent(tfHR);
		if (_addNew != null)
			HRLayout.removeComponent(_addNew);
		if (_employeeExit != null)
			HRLayout.removeComponent(_employeeExit);

		HRLayout.removeComponent(HRHorizontalLayout2);
		HRLayout.removeComponent(HRHorizontalLayout3);

		HRHorizontalLayout2 = new HorizontalLayout();
		HRHorizontalLayout2.setSizeFull();
		HRHorizontalLayout2.setMargin(true);
		HRHorizontalLayout2.setImmediate(true);
		HRHorizontalLayout3 = new HorizontalLayout();
		HRHorizontalLayout3.setWidth("100%");
		HRHorizontalLayout3.setStyleName("jobProjectMenu");
		HRHorizontalLayout3.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		
		table = new Table("Job Candidate", generateContentForSearchedJobCandidate(""));

		table.setSizeFull();
		table.setPageLength(10);
		table.setSelectable(true);
		table.setImmediate(true);

		tfHR = new com.vaadin.ui.TextField();
		tfHR.setInputPrompt("Search ");
		tfHR.setWidth("100%");

		tfHR.addTextChangeListener(new TextChangeListener() {
			public void textChange(TextChangeEvent event) {
				searchJobCandidateRefresh(event.getText());
			}
		});

		tfHR.setTextChangeEventMode(TextChangeEventMode.EAGER);

		_addNew = new Button("Add New Job Candidate");
		_addNew.setWidth("80%");
		addNewForm = getAddJobCandidatePanel();

		popUpWindow = new Window("Please enter all necessary things for adding new job candidate");

		_addNew.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {

				getSession().setAttribute("_userName", null);
				popUpWindow.setHeight("600px");
				popUpWindow.setWidth("600px");
				popUpWindow.center();
				popUpWindow.setContent(addNewForm);
				popUpWindow.setModal(true);
				addWindow(popUpWindow);
			}
		});

		_addNew.setEnabled(true);

		HRHorizontalLayout2.addComponent(table);
		HRHorizontalLayout2.setExpandRatio(table, 1);

		HRHorizontalLayout3.addComponents(tfHR, _addNew);
		HRLayout.addComponents(HRHorizontalLayout3, HRHorizontalLayout2);
		HRLayout.setExpandRatio(HRHorizontalLayout2, 1);

	}

	private void refreshCand() {
		tfFirstName.clear();
		tfLastName.clear();		
		tfEmail.clear();
		cbGender.clear();
		tfAddress.clear();
		tfPhone.clear();
		tfMobile.clear();
		tfEducation.clear();
		tfWorkExpiriance.clear();
		tfHobbies.clear();
		tfPosition.clear();
	}

	private FormLayout getAddJobCandidatePanel() {
		HorizontalLayout layoutButton = new HorizontalLayout();
		FormLayout form = new FormLayout();

		tfFirstName = new TextField("First Name");
		form.addComponent(tfFirstName);

		tfLastName = new TextField("Last Name");
		form.addComponent(tfLastName);
				
		cbGender = new ComboBox("Gender");
		cbGender.setFilteringMode(FilteringMode.CONTAINS);
		cbGender.setPageLength(5);
		cbGender.setInputPrompt("Select Gender");
		cbGender.setNullSelectionAllowed(false);
		
		cbGender.addItem("Male");
		cbGender.addItem("Female");
		form.addComponent(cbGender);

		tfEmail = new TextField("Email");
		tfEmail.addValidator(new EmailValidator("The email address isn't valid."));
		form.addComponent(tfEmail);

		tfAddress = new TextField("Address");
		form.addComponent(tfAddress);

		tfPhone = new TextField("Phone");
		form.addComponent(tfPhone);

		tfMobile = new TextField("Mobile");
		form.addComponent(tfMobile);

		tfEducation = new TextField("Education");
		form.addComponent(tfEducation);

		tfWorkExpiriance = new TextField("Work Expiriance");
		form.addComponent(tfWorkExpiriance);

		tfHobbies = new TextField("Hobbies");
		form.addComponent(tfHobbies);

		tfPosition = new TextField("Position");
		form.addComponent(tfPosition);

		upload = new Upload("Upload CV", new Receiver() {
			File file;

			public FileOutputStream receiveUpload(String filename, String mimeType) {
				FileOutputStream fos = null;
				try {
					new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/cv/").mkdir();
					file = new File(
							VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/cv/" + filename);
					fos = new FileOutputStream(file);
					address = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/cv/" + filename;
				} catch (final java.io.FileNotFoundException e) {
					return null;
				}
				return fos;
			}
		});

		upload.setCaption("Upload CV");
		upload.setButtonCaption("Upload CV");
		upload.setImmediate(true);
		upload.addSucceededListener(new SucceededListener() {
			public void uploadSucceeded(SucceededEvent event) {
				Notification.show("Upload succeeded!");
			}
		});

		upload.addFailedListener(new FailedListener() {
			public void uploadFailed(FailedEvent event) {
				Notification.show("Upload failed!");
			}
		});

		form.addComponent(upload);

		_save = new Button("Save", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				new HRServiceImp().addNewJobCandidate(tfFirstName.getValue(), tfLastName.getValue(),
						(String) cbGender.getValue(), tfAddress.getValue(), tfEmail.getValue(), tfPhone.getValue(),
						tfMobile.getValue(), tfEducation.getValue(), tfWorkExpiriance.getValue(), tfHobbies.getValue(),
						tfPosition.getValue(), address);
				popUpWindow.close();
				UI.getCurrent().removeWindow(popUpWindow);
				refreshCand();
				searchJobCandidateRefresh("");
			}
		});

		_cancel = new Button("Cancel", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				popUpWindow.close();
				UI.getCurrent().removeWindow(popUpWindow);
				_addNew.setEnabled(true);
				refreshCand();
			}
		});

		layoutButton.addComponents(_save, _cancel);
		form.addComponents(layoutButton);
		return form;
	}

	protected void showJobPosts() {
		if (tfHR != null)
			HRLayout.removeComponent(tfHR);
		if (_addNew != null)
			HRLayout.removeComponent(_addNew);
		if (_employeeExit != null)
			HRLayout.removeComponent(_employeeExit);

		HRLayout.removeComponent(HRHorizontalLayout2);
		HRLayout.removeComponent(HRHorizontalLayout3);


		HRHorizontalLayout2 = new HorizontalLayout();
		HRHorizontalLayout2.setSizeFull();
		HRHorizontalLayout2.setMargin(true);
		HRHorizontalLayout2.setImmediate(true);
		HRHorizontalLayout3 = new HorizontalLayout();
		HRHorizontalLayout3.setWidth("100%");
		HRHorizontalLayout3.setStyleName("jobProjectMenu");
		HRHorizontalLayout3.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		
		table = new Table("Job Posts", generateSearchedJobs(""));

		table.setSizeFull();
		table.setPageLength(10);
		table.setSelectable(true);
		table.setImmediate(true);

		tfHR = new com.vaadin.ui.TextField();
		tfHR.setInputPrompt("Search ");
		tfHR.setWidth("100%");

		tfHR.addTextChangeListener(new TextChangeListener() {
			public void textChange(TextChangeEvent event) {
				searchJobPostsRefresh(event.getText());
			}
		});
		tfHR.setTextChangeEventMode(TextChangeEventMode.EAGER);

		_addNew = new Button("Add New Job Post");
		_addNew.setWidth("70%");
		
		addNewForm = getAddJobPostPanel();

		popUpWindow = new Window("Please enter all necessary things for adding new job post");

		_addNew.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {

				getSession().setAttribute("_userName", null);
				popUpWindow.setHeight("600px");
				popUpWindow.setWidth("600px");
				popUpWindow.center();
				popUpWindow.setContent(addNewForm);
				popUpWindow.setModal(true);
				addWindow(popUpWindow);
			}
		});

		_addNew.setEnabled(true);

		HRHorizontalLayout2.addComponent(table);
		HRHorizontalLayout2.setExpandRatio(table, 1);

		HRHorizontalLayout3.addComponents(tfHR, _addNew);
		HRLayout.addComponents(HRHorizontalLayout3, HRHorizontalLayout2);
		HRLayout.setExpandRatio(HRHorizontalLayout2, 1);

	}

	private FormLayout getAddJobPostPanel() {

		FormLayout form = new FormLayout();

		final TextField position = new TextField("Job Position");
		form.addComponent(position);

		final TextField projectName = new TextField("Project Name");
		form.addComponent(projectName);

		final TextField type = new TextField("Job Type");
		form.addComponent(type);

		final DateField endDate = new DateField("Closing date");
		form.addComponent(endDate);

		final TextField description = new TextField("Description");
		form.addComponent(description);

		final TextField number = new TextField("Number of positions");
		form.addComponent(number);

		_save = new Button("Save", new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				new HRServiceImp().addNewJobPost(position.getValue(), projectName.getValue(), type.getValue(),
						Integer.parseInt(number.getValue()), endDate.getValue(), description.getValue());
				popUpWindow.close();
				UI.getCurrent().removeWindow(popUpWindow);
				searchJobPostsRefresh("");
			}
		});

		_cancel = new Button("Cancel", new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				popUpWindow.close();
				UI.getCurrent().removeWindow(popUpWindow);

			}
		});

		HorizontalLayout layoutButton = new HorizontalLayout();

		layoutButton.addComponents(_save, _cancel);
		form.addComponent(layoutButton);
		return form;
	}

	private void searchJobPostsRefresh(String term) {
		HRHorizontalLayout2.removeComponent(table);
		table = new Table("Projects", generateSearchedJobs(term));
		HRHorizontalLayout2.addComponent(table);
	}

	private IndexedContainer generateSearchedJobs(String term) {
		IndexedContainer container = new IndexedContainer();

		container.addContainerProperty("Id", String.class, null);
		container.addContainerProperty("Job Position", String.class, null);
		container.addContainerProperty("Project Name", String.class, null);
		container.addContainerProperty("Job Type", String.class, null);
		container.addContainerProperty("Number of positions", String.class, null);
		container.addContainerProperty("Closing Date", String.class, null);
		container.addContainerProperty("Description", String.class, null);

		addSearchedJobPosts(container, term);

		return container;
	}

	@SuppressWarnings("unchecked")
	private void addSearchedJobPosts(IndexedContainer cont, String term) {

		HashSet<JobPost> jobPosts = new HRServiceImp().showSearchedJobPosts(term);

		Iterator<JobPost> iterator = jobPosts.iterator();

		String generate;

		while (iterator.hasNext()) {
			final JobPost jobPost = iterator.next();
			Object itemId = cont.addItem();

			generate = jobPost.getId() + "";
			cont.getItem(itemId).getItemProperty("Id").setValue(generate);

			generate = jobPost.getProjectName();
			cont.getItem(itemId).getItemProperty("Project Name").setValue(generate);

			generate = jobPost.getJobDescription() + "";
			cont.getItem(itemId).getItemProperty("Description").setValue(generate);

			generate = jobPost.getJobType() + "";
			cont.getItem(itemId).getItemProperty("Job Type").setValue(generate);

			generate = jobPost.getNoOfPositions() + "";
			cont.getItem(itemId).getItemProperty("Number of positions").setValue(generate);

			generate = jobPost.getJobPosition() + "";
			cont.getItem(itemId).getItemProperty("Job Position").setValue(generate);

			generate = jobPost.getClosingDate() + "";
			cont.getItem(itemId).getItemProperty("Closing Date").setValue(generate);
		}
	}

	private void searchJobCandidateRefresh(String term) {
		HRHorizontalLayout2.removeComponent(table);
		table = new Table("Job candidate", generateContentForSearchedJobCandidate(term));
		table.setSizeFull();
		table.setPageLength(10);
		table.setSelectable(true);
		table.setImmediate(true);
		HRHorizontalLayout2.addComponent(table);
	}

	private IndexedContainer generateContentForSearchedJobCandidate(String term) {

		IndexedContainer container = new IndexedContainer();

		container.addContainerProperty("Id", String.class, null);
		container.addContainerProperty("First Name", String.class, null);
		container.addContainerProperty("Last Name", String.class, null);
		container.addContainerProperty("Email", String.class, null);
		container.addContainerProperty("Job position", String.class, null);
		container.addContainerProperty("CV", Button.class, null);
		
		addContentForSearchedJobCandidateTable(container, term);

		return container;
	}

	@SuppressWarnings("unchecked")
	private void addContentForSearchedJobCandidateTable(IndexedContainer cont, String term) {

		HashSet<JobCandidate> jobCandidate = new HRServiceImp().showSearchedJobCandidate(term);

		Iterator<JobCandidate> iterator = jobCandidate.iterator();

		String generate;

		while (iterator.hasNext()) {
			final JobCandidate candidate = iterator.next();
			Object itemId = cont.addItem();

			generate = candidate.getId() + "";
			cont.getItem(itemId).getItemProperty("Id").setValue(generate);

			generate = candidate.getFirstName();
			cont.getItem(itemId).getItemProperty("First Name").setValue(generate);

			generate = candidate.getLastName();
			cont.getItem(itemId).getItemProperty("Last Name").setValue(generate);

			generate = candidate.getEmail();
			cont.getItem(itemId).getItemProperty("Email").setValue(generate);

			generate = candidate.getJobPosition();
			cont.getItem(itemId).getItemProperty("Job position").setValue(generate);

			Button cv = new Button("CV");
			cv.setData(candidate.getcv());
			
			cv.addClickListener(new ClickListener() {
				public void buttonClick(ClickEvent event) {
					Button download = event.getButton();
					String path =(String) download.getData();
					FileResource res = new FileResource(new File(path));
				    Page.getCurrent().open(res, null, false);
				}
			});
			
			cont.getItem(itemId).getItemProperty("CV").setValue(cv);
		}

	}
}
