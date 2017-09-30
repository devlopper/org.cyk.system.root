package org.cyk.system.root.business.impl.integration;

import java.util.Arrays;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.business.api.geography.ElectronicMailAddressBusiness;
import org.cyk.system.root.business.api.geography.PhoneNumberBusiness;
import org.cyk.system.root.business.impl.AbstractBusinessTestHelper.TestCase;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.persistence.api.geography.ContactDao;
import org.cyk.utility.common.Constant;
import org.junit.Test;

public class ContactCollectionBusinessIT extends AbstractBusinessIT {

    private static final long serialVersionUID = -6691092648665798471L;
 
    @Test
    public void emailBadFormat(){
    	TestCase testCase = instanciateTestCase();
    	ContactCollection contactCollection = new ContactCollection();
    	contactCollection.add(new ElectronicMailAddress(contactCollection, "a..@m.com"));
    	contactCollection.setItemsSynchonizationEnabled(Boolean.TRUE);
    	testCase.create(contactCollection,"Adresse : a..@m.com n'est pas une adresse de courrier électronique bien formée");    	
    }
    
    @Test
    public void crudContactCollection(){
    	TestCase testCase = instanciateTestCase();
    	ContactCollection contactCollection = inject(ContactCollectionBusiness.class).instanciateOne();
    	testCase.create(contactCollection);
    	testCase.clean();
    }
    
    @Test
    public void crudContactCollectionWithPhoneNumbers(){
    	TestCase testCase = instanciateTestCase();
    	ContactCollection contactCollection = inject(ContactCollectionBusiness.class).instanciateOne();
    	contactCollection.setCode("mc01");
    	/*
    	PhoneNumber phoneNumber = new PhoneNumber();
    	contactCollection.addPhoneNumbers(Arrays.asList(phoneNumber));
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE);
    	*/
    	testCase.create(contactCollection);
    	
    	contactCollection = testCase.read(ContactCollection.class, "mc01");
    	PhoneNumber phoneNumber = new PhoneNumber();
    	contactCollection.addPhoneNumbers(Arrays.asList(phoneNumber));
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE);
    	testCase.update(contactCollection,null,"1 Type : ne peut pas être nul"+Constant.LINE_DELIMITER+"2 Pays : ne peut pas être nul"
    			+Constant.LINE_DELIMITER+"3 Numéro : ne peut pas être nul");
    	
    	testCase.clean();
    }
    
    @Test
    public void crudContactCollectionWithOnlyOneElectronicMail(){
    	TestCase testCase = instanciateTestCase();
    	String code001 = generateRandomCode();
    	//create
    	ContactCollection contactCollection = inject(ContactCollectionBusiness.class).instanciateOne();
    	contactCollection.setCode(code001);
    	contactCollection.setItemsSynchonizationEnabled(Boolean.TRUE);
    	testCase.create(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(ElectronicMailAddressBusiness.class).instanciateOne(contactCollection, (String)null);
    	contactCollection.removeItemAt(ElectronicMailAddress.class, 0);
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(ElectronicMailAddressBusiness.class).instanciateOne(contactCollection, "m1@mail.com");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"m1@mail.com"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.getItemAt(ElectronicMailAddress.class,0).setAddress("m12@mail.com");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"m12@mail.com"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.getItemAt(ElectronicMailAddress.class,0).setAddress("another@gmail.com");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"another@gmail.com"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.removeItemAt(ElectronicMailAddress.class, 0);
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(ElectronicMailAddressBusiness.class).instanciateOne(contactCollection, "another@gmail.com");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"another@gmail.com"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.getItemAt(ElectronicMailAddress.class,0).setAddress("another1@yo.net");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"another1@yo.net"});
    	
    	testCase.clean();
    }
    
    @Test
    public void crudContactCollectionWithOnlyOnePhoneNumber(){
    	TestCase testCase = instanciateTestCase();
    	String code001 = generateRandomCode();
    	//create
    	ContactCollection contactCollection = inject(ContactCollectionBusiness.class).instanciateOne();
    	contactCollection.setCode(code001);
    	contactCollection.setItemsSynchonizationEnabled(Boolean.TRUE);
    	testCase.create(contactCollection);
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(PhoneNumberBusiness.class).instanciateOne(contactCollection, (String)null,(String)null, (String)null);
    	contactCollection.removeItemAt(PhoneNumber.class, 0);
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(PhoneNumberBusiness.class).instanciateOne(contactCollection, "11223344");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"11223344"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.getItemAt(PhoneNumber.class,0).setNumber("15482659");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"15482659"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.getItemAt(PhoneNumber.class,0).setNumber("5213");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"5213"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.removeItemAt(PhoneNumber.class, 0);
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(PhoneNumberBusiness.class).instanciateOne(contactCollection, RootConstant.Code.Country.COTE_DIVOIRE,RootConstant.Code.PhoneNumberType.LAND,"777");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"777"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.getItemAt(PhoneNumber.class,0).setNumber("mynumber");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"mynumber"});
    	
    	testCase.clean();
    }
    
    @Test
    public void crudContactCollectionWithManyElectronicMails(){
    	TestCase testCase = instanciateTestCase();
    	String code001 = generateRandomCode();
    	//create
    	ContactCollection contactCollection = inject(ContactCollectionBusiness.class).instanciateOne();
    	contactCollection.setCode(code001);
    	contactCollection.setItemsSynchonizationEnabled(Boolean.TRUE);
    	inject(ElectronicMailAddressBusiness.class).instanciateOne(contactCollection, "m1@mail.com");
    	inject(ElectronicMailAddressBusiness.class).instanciateOne(contactCollection, "m2@mail.com");
    	testCase.create(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"m1@mail.com","m2@mail.com"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(ElectronicMailAddressBusiness.class).instanciateOne(contactCollection, (String)null);
    	contactCollection.removeItemAt(ElectronicMailAddress.class, 2);
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"m1@mail.com","m2@mail.com"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(ElectronicMailAddressBusiness.class).instanciateOne(contactCollection, "m3@mail.com");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"m1@mail.com","m2@mail.com","m3@mail.com"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.getItemAt(ElectronicMailAddress.class,1).setAddress("m12@mail.com");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"m1@mail.com","m12@mail.com","m3@mail.com"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.getItemAt(ElectronicMailAddress.class,0).setAddress("another@gmail.com");
    	contactCollection.getItemAt(ElectronicMailAddress.class,2).setAddress("another1@gmail.com");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"another@gmail.com","m12@mail.com","another1@gmail.com"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.removeItemAt(ElectronicMailAddress.class, 1);
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"another@gmail.com","another1@gmail.com"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(ElectronicMailAddressBusiness.class).instanciateOne(contactCollection, "m12@gmail.com");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"another@gmail.com","another1@gmail.com","m12@gmail.com"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(ContactCollectionBusiness.class).remove(contactCollection, ElectronicMailAddress.class);
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(ElectronicMailAddressBusiness.class).instanciateOne(contactCollection, "c@gmail.com");
    	inject(ElectronicMailAddressBusiness.class).instanciateOne(contactCollection, "a@gmail.com");
    	inject(ElectronicMailAddressBusiness.class).instanciateOne(contactCollection, "b@gmail.com");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"c@gmail.com","a@gmail.com","b@gmail.com"});
    	
    	testCase.clean();
    }
    
    @Test
    public void crudContactCollectionWithManyPhoneNumbers(){
    	TestCase testCase = instanciateTestCase();
    	String code001 = generateRandomCode();
    	//create
    	ContactCollection contactCollection = inject(ContactCollectionBusiness.class).instanciateOne();
    	contactCollection.setCode(code001);
    	contactCollection.setItemsSynchonizationEnabled(Boolean.TRUE);
    	inject(PhoneNumberBusiness.class).instanciateOne(contactCollection, "111");
    	inject(PhoneNumberBusiness.class).instanciateOne(contactCollection, "222");
    	testCase.create(contactCollection);
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"111","222"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(PhoneNumberBusiness.class).instanciateOne(contactCollection, (String)null);
    	contactCollection.removeItemAt(PhoneNumber.class, 2);
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"111","222"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(PhoneNumberBusiness.class).instanciateOne(contactCollection, "333");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"111","222","333"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.getItemAt(PhoneNumber.class,1).setNumber("444");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"111","444","333"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.getItemAt(PhoneNumber.class,0).setNumber("1212");
    	contactCollection.getItemAt(PhoneNumber.class,2).setNumber("3232");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"1212","444","3232"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.removeItemAt(PhoneNumber.class, 1);
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"1212","3232"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(PhoneNumberBusiness.class).instanciateOne(contactCollection, "8989");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"1212","3232","8989"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(ContactCollectionBusiness.class).remove(contactCollection, PhoneNumber.class);
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(PhoneNumberBusiness.class).instanciateOne(contactCollection, "1");
    	inject(PhoneNumberBusiness.class).instanciateOne(contactCollection, "22");
    	inject(PhoneNumberBusiness.class).instanciateOne(contactCollection, "123");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"1","22","123"});
    }
    
    @Test
    public void crudContactCollectionWithOnlyOneElectronicMailAndPhoneNumber(){
    	TestCase testCase = instanciateTestCase();
    	String code001 = generateRandomCode();
    	//create
    	ContactCollection contactCollection = inject(ContactCollectionBusiness.class).instanciateOne();
    	contactCollection.setCode(code001);
    	contactCollection.setItemsSynchonizationEnabled(Boolean.TRUE);
    	testCase.create(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(ElectronicMailAddressBusiness.class).instanciateOne(contactCollection, (String)null);
    	contactCollection.removeItemAt(ElectronicMailAddress.class, 0);
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(ElectronicMailAddressBusiness.class).instanciateOne(contactCollection, "m1@mail.com");
    	inject(PhoneNumberBusiness.class).instanciateOne(contactCollection, "11223344");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"m1@mail.com"});
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"11223344"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.getItemAt(ElectronicMailAddress.class,0).setAddress("m12@mail.com");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"m12@mail.com"});
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"11223344"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.getItemAt(ElectronicMailAddress.class,0).setAddress("another@gmail.com");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"another@gmail.com"});
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"11223344"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.removeItemAt(ElectronicMailAddress.class, 0);
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{});
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"11223344"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(ElectronicMailAddressBusiness.class).instanciateOne(contactCollection, "another@gmail.com");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"another@gmail.com"});
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"11223344"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.getItemAt(ElectronicMailAddress.class,0).setAddress("another1@yo.net");
    	contactCollection.removeItemAt(PhoneNumber.class, 0);
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"another1@yo.net"});
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{});
    	
    	testCase.clean();
    }
    
    @Test
    public void crudContactCollectionWithManyElectronicMailsAndPhoneNumbers(){
    	TestCase testCase = instanciateTestCase();
    	String code001 = generateRandomCode();
    	//create
    	ContactCollection contactCollection = inject(ContactCollectionBusiness.class).instanciateOne();
    	contactCollection.setCode(code001);
    	contactCollection.setItemsSynchonizationEnabled(Boolean.TRUE);
    	testCase.create(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(ElectronicMailAddressBusiness.class).instanciateOne(contactCollection, (String)null);
    	contactCollection.removeItemAt(ElectronicMailAddress.class, 0);
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(ElectronicMailAddressBusiness.class).instanciateOne(contactCollection, "m1@mail.com");
    	inject(PhoneNumberBusiness.class).instanciateOne(contactCollection, "11223344");
    	inject(ElectronicMailAddressBusiness.class).instanciateOne(contactCollection, "m12@mail.com");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"m1@mail.com","m12@mail.com"});
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"11223344"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.getItemAt(ElectronicMailAddress.class,0).setAddress("m12a@mail.com");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"m12a@mail.com","m12@mail.com"});
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"11223344"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.getItemAt(ElectronicMailAddress.class,0).setAddress("another@gmail.com");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"another@gmail.com","m12@mail.com"});
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"11223344"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.removeItemAt(ElectronicMailAddress.class, 0);
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"m12@mail.com"});
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"11223344"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	inject(ElectronicMailAddressBusiness.class).instanciateOne(contactCollection, "another@gmail.com");
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"m12@mail.com","another@gmail.com"});
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{"11223344"});
    	//update
    	contactCollection = testCase.read(ContactCollection.class, code001);
    	contactCollection.getItems().setSynchonizationEnabled(Boolean.TRUE).setElements(inject(ContactDao.class).readByCollection(contactCollection));
    	contactCollection.getItemAt(ElectronicMailAddress.class,0).setAddress("another1@yo.net");
    	contactCollection.removeItemAt(PhoneNumber.class, 0);
    	testCase.update(contactCollection);
    	testCase.assertContactCollectionElectronicMails(code001, new String[]{"another1@yo.net","another@gmail.com"});
    	testCase.assertContactCollectionPhoneNumbers(code001, new String[]{});
    	
    	testCase.clean();
    }
    
}
