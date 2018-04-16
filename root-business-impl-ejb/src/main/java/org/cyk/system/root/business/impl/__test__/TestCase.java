package org.cyk.system.root.business.impl.__test__;

import static org.cyk.system.root.model.RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_DAUGHTER;
import static org.cyk.system.root.model.RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_FATHER;
import static org.cyk.system.root.model.RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_MOTHER;
import static org.cyk.system.root.model.RootConstant.Code.PersonRelationshipTypeRole.FAMILY_PARENT_SON;
import static org.cyk.system.root.model.RootConstant.Code.PersonRelationshipTypeRole.FAMILY_SPOUSE_HUSBAND;
import static org.cyk.system.root.model.RootConstant.Code.PersonRelationshipTypeRole.FAMILY_SPOUSE_WIFE;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessThrowable;
import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.api.party.person.PersonRelationshipBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.ElectronicMailAddress;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransfer;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferAcknowledgement;
import org.cyk.system.root.model.party.BusinessRole;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.system.root.model.party.person.Sex;
import org.cyk.system.root.model.pattern.tree.NestedSet;
import org.cyk.system.root.model.pattern.tree.NestedSetNode;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriodCollection;
import org.cyk.system.root.persistence.api.AbstractCollectionItemDao;
import org.cyk.system.root.persistence.api.TypedDao;
import org.cyk.system.root.persistence.api.geography.ElectronicMailAddressDao;
import org.cyk.system.root.persistence.api.geography.PhoneNumberDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementCollectionDao;
import org.cyk.system.root.persistence.api.mathematics.movement.MovementDao;
import org.cyk.system.root.persistence.api.party.PartyIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.party.person.PersonDao;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipDao;
import org.cyk.system.root.persistence.api.party.person.PersonRelationshipTypeRoleDao;
import org.cyk.system.root.persistence.api.pattern.tree.NestedSetNodeDao;
import org.cyk.system.root.persistence.impl.PersistenceInterfaceLocator;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.ArrayHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.MethodHelper;
import org.cyk.utility.common.helper.NumberHelper;
import org.cyk.utility.common.helper.TimeHelper;
import org.exolab.castor.types.DateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class TestCase extends org.cyk.utility.common.test.TestCase implements Serializable {
	private static final long serialVersionUID = -6026836126124339547L;

	{
		this.defaultThrowableClass = BusinessThrowable.class;
	}
	
	public void assertNotNullPartyIdentifiableGlobalIdentifier(String partyCode,String businessRoleCode,Class<? extends AbstractIdentifiable> identifiableClass,String identifiableCode) {
		assertNotNull("party identifiable global identifier ("+partyCode+","+businessRoleCode+","+identifiableClass+","+identifiableCode+") is null",inject(PartyIdentifiableGlobalIdentifierDao.class).readByPartyByIdentifiableGlobalIdentifierByRole(getByIdentifierWhereValueUsageTypeIsBusiness(Party.class, partyCode,Boolean.TRUE)
    			, getByIdentifierWhereValueUsageTypeIsBusiness(identifiableClass,identifiableCode,Boolean.TRUE).getGlobalIdentifier()
    			, getByIdentifierWhereValueUsageTypeIsBusiness(BusinessRole.class,businessRoleCode,Boolean.TRUE)));
	}
	
	public <T> T readCollectionItem(Class<T> aClass,Class<? extends AbstractCollection<?>> collectionClass,String collectionCode,String code){
		return read(aClass,RootConstant.Code.generate((AbstractCollection<?>)read(collectionClass,collectionCode), code));
	}		
	
	/**/
	
	public Person createOnePerson(String code,String firstname,String lastnames,String email){
		Person person = inject(PersonBusiness.class).instanciateOne().setCode(code).setName(firstname).setLastnames(lastnames).addElectronicMail(email);
		if(person.getContactCollection()!=null)
			person.getContactCollection().getItems().setSynchonizationEnabled(Boolean.TRUE);
		create(person);
		if(StringUtils.isNotBlank(email))
			assertElectronicMail(code, email);
		return person;
	}
	
	public Person createOnePersonRandomly(String code){
		return create(inject(PersonBusiness.class).instanciateOneRandomly(code));
	}
	
	public void createManyPersonRandomly(String...codes){
		for(Person person : inject(PersonBusiness.class).instanciateManyRandomly(new HashSet<>(Arrays.asList(codes))))
			create(person);
	}
	
	public PersonRelationship createOnePersonRelationship(String person1Code,String role1Code,String person2Code,String role2Code){
		return create((PersonRelationship) inject(PersonRelationshipBusiness.class).instanciateOne(person1Code,role1Code,person2Code,role2Code));
	}
	
	public void createParentChildrenPersonRelationship(String parentPersonCode,String parentRoleCode,String[] sonPersonCodes,String[] daughterPersonCodes){
		if(StringUtils.isBlank(parentPersonCode))
			return;
		if(sonPersonCodes!=null)
			for(String sonPersonCode : sonPersonCodes)
				createOnePersonRelationship(parentPersonCode, parentRoleCode, sonPersonCode, FAMILY_PARENT_SON);
		
		if(daughterPersonCodes!=null)
			for(String daughterPersonCode : daughterPersonCodes)
				createOnePersonRelationship(parentPersonCode, parentRoleCode, daughterPersonCode, FAMILY_PARENT_DAUGHTER);
	}
	
	public void createFamilyPersonRelationship(String fatherPersonCode,String motherPersonCode,String[] sonPersonCodes,String[] daughterPersonCodes){
		for(String personCode : ArrayUtils.addAll(ArrayUtils.addAll(sonPersonCodes, daughterPersonCodes),new String[]{fatherPersonCode,motherPersonCode})){
			if(inject(PersonDao.class).read(personCode)==null)
				createOnePersonRandomly(personCode);
		}
		createOnePersonRelationship(fatherPersonCode,FAMILY_SPOUSE_HUSBAND,motherPersonCode,FAMILY_SPOUSE_WIFE);
		createParentChildrenPersonRelationship(fatherPersonCode, FAMILY_PARENT_FATHER, sonPersonCodes, daughterPersonCodes);
		createParentChildrenPersonRelationship(motherPersonCode, FAMILY_PARENT_MOTHER, sonPersonCodes, daughterPersonCodes);
					
		assertPersonRelationship(fatherPersonCode, FAMILY_PARENT_FATHER, FAMILY_PARENT_SON, sonPersonCodes);
		assertPersonRelationship(motherPersonCode, FAMILY_PARENT_MOTHER, FAMILY_PARENT_SON, sonPersonCodes);
		assertPersonRelationship(fatherPersonCode, FAMILY_PARENT_FATHER, FAMILY_PARENT_DAUGHTER, daughterPersonCodes);
		assertPersonRelationship(motherPersonCode, FAMILY_PARENT_MOTHER, FAMILY_PARENT_DAUGHTER, daughterPersonCodes);
	}
	
	/**/
	
	public void assertPersonRelationship(String person1Code,String role1Code,String role2Code,String[] expectedPersonCodes){
		__assertPersonRelationship__(person1Code, role1Code, role2Code, expectedPersonCodes,Boolean.TRUE);
		if(expectedPersonCodes!=null)
			for(String expectedPersonCode : expectedPersonCodes)
				__assertPersonRelationship__(expectedPersonCode, role2Code, role1Code, new String[]{person1Code},Boolean.FALSE);
	}
	
	private void __assertPersonRelationship__(String person1Code,String role1Code,String role2Code,String[] expectedPersonCodes,Boolean assertCount){
		Person person1 = inject(PersonDao.class).read(person1Code);
		PersonRelationshipTypeRole role1 = inject(PersonRelationshipTypeRoleDao.class).read(role1Code);
		PersonRelationshipTypeRole role2 = inject(PersonRelationshipTypeRoleDao.class).read(role2Code);
		Collection<PersonRelationship> personRelationships = inject(PersonRelationshipDao.class).readByPersonByRoleByOppositeRole(person1, role1 , role2);
		
		if(Boolean.TRUE.equals(assertCount)){
			Integer count = inject(PersonRelationshipDao.class).countByPersonByRoleByOppositeRole(person1, role1, role2).intValue();
			assertEquals("Number of "+role2Code+" of "+person1Code, expectedPersonCodes==null ? 0 : expectedPersonCodes.length, personRelationships.size());
			assertEquals("Database count "+count+" is not equals to list size "+personRelationships.size(), count, personRelationships.size());
		}
		
		if(expectedPersonCodes!=null){
			Set<String> codes = new HashSet<>();
			for(Person person : inject(PersonRelationshipBusiness.class).getRelatedPersons(personRelationships, person1))
				codes.add(person.getCode());
			for(String code : expectedPersonCodes)
				assertTrue(code+" is not "+role2Code+" of "+person1Code, codes.contains(code));	
		}
	}
	
	public void assertElectronicMail(String personCode,String email){
		Collection<ElectronicMailAddress> electronicMailAddresses = commonUtils.castCollection(
				inject(ElectronicMailAddressDao.class).readByCollection(inject(PersonDao.class).read(personCode).getContactCollection()),ElectronicMailAddress.class);
		assertEquals("Electronic mail", email, electronicMailAddresses.isEmpty() ? Constant.EMPTY_STRING : electronicMailAddresses.iterator().next().getAddress());
	}
	
	public void assertContactCollectionElectronicMails(String contactCollectionCode,String[] electronicMailAddresses){
		ContactCollection contactCollection = read(ContactCollection.class, contactCollectionCode);
		Collection<ElectronicMailAddress> electronicMails = CollectionHelper.getInstance().cast(ElectronicMailAddress.class
				,inject(ElectronicMailAddressDao.class).readByCollection(contactCollection));
		assertList(CollectionHelper.getInstance().createList(MethodHelper.getInstance().callGet(electronicMails, String.class, ElectronicMailAddress.FIELD_ADDRESS))
				, ArrayHelper.getInstance().isEmpty(electronicMailAddresses) ? new ArrayList<>() : Arrays.asList(electronicMailAddresses));
	}
	
	public void assertContactCollectionPhoneNumbers(String contactCollectionCode,String[] phoneNumberValues){
		ContactCollection contactCollection = read(ContactCollection.class, contactCollectionCode);
		Collection<PhoneNumber> phoneNumbers = CollectionHelper.getInstance().cast(PhoneNumber.class
				,inject(PhoneNumberDao.class).readByCollection(contactCollection));
		assertList(CollectionHelper.getInstance().createList(MethodHelper.getInstance().callGet(phoneNumbers, String.class, PhoneNumber.FIELD_NUMBER))
				, ArrayHelper.getInstance().isEmpty(phoneNumberValues) ? new ArrayList<>() : Arrays.asList(phoneNumberValues));
	}
	
	public <T extends AbstractIdentifiable> void assertWhereExistencePeriodFromDateIsLessThanCount(final Class<T> aClass,final String code,Integer count){
		T identifiable = getBusiness(aClass).find(code);
		assertWhereExistencePeriodFromDateIsLessThanCount(aClass, identifiable, count);
	}
	
	public <T extends AbstractIdentifiable> void assertWhereExistencePeriodFromDateIsLessThanCount(Class<T> aClass,T identifiable,Integer count){
		Collection<T> collection = getBusiness(aClass).findWhereExistencePeriodFromDateIsLessThan(identifiable);
		Long dbCount = getBusiness(aClass).countWhereExistencePeriodFromDateIsLessThan(identifiable);
		//System.out.println(toString(identifiable, EXISTENCE_PERIOD_FROM_DATE_IS_LESS_THAN)+" , Childrens - collection size = "+collection.size()+" , count from db = "+dbCount);
		assertEquals("collection and count", dbCount.intValue(),collection.size());
		assertEquals("Collection size", count, collection.size());
		assertEquals("count", count, dbCount.intValue());
	}
	
	public <T extends AbstractIdentifiable> void assertFirstWhereExistencePeriodFromDateIsLessThan(final Class<T> aClass,final String code,String firstPreviousCode,Integer numberOfPrevious){
		T identifiable = read(aClass, code);
		T previous = inject(PersistenceInterfaceLocator.class).injectTyped(aClass).readFirstWhereExistencePeriodFromDateIsLessThan(identifiable);
		String name = toString(identifiable, EXISTENCE_PERIOD_FROM_DATE_IS_LESS_THAN);
		assertEquals("Number of previous of "+name, numberOfPrevious, getPersistence(aClass).countWhereExistencePeriodFromDateIsLessThan(identifiable).intValue());
		if(previous==null){
			assertTrue("No previous found for "+name, previous==null && StringUtils.isBlank(firstPreviousCode));
		}else{
			T firstPrevious = inject(PersistenceInterfaceLocator.class).injectTyped(aClass).read(firstPreviousCode);
			assertEquals("Previous of "+name+" is "+firstPreviousCode+"("+firstPrevious.getBirthDate()+")", previous, firstPrevious);	
		}
		
	}
	
	public <T extends AbstractIdentifiable> void assertOrderBasedOnExistencePeriodFromDate(final Class<T> aClass,Boolean firstIsRoot,final String...codes){
		if(Boolean.TRUE.equals(firstIsRoot)){
			assertFirstWhereExistencePeriodFromDateIsLessThan(aClass, codes[0], null,0);
		}
		
		if(codes!=null && codes.length > 1)
			for(int i = 0 ; i < codes.length - 1 ; i++){
				String code = codes[i+1];
				assertFirstWhereExistencePeriodFromDateIsLessThan(aClass, code, codes[i],i+1);
			}
	}
	
	public <T extends AbstractIdentifiable> void assertOrderBasedOnExistencePeriodFromDate(final Class<T> aClass,final String...codes){
		assertOrderBasedOnExistencePeriodFromDate(aClass, Boolean.TRUE, codes);
	}
	
	public <T extends AbstractIdentifiable> TestCase assertIdentifiable(Class<T> identifiableClass,String code,Map<String,Object> fieldMap){
		T identifiable = read(identifiableClass, code);
		if(identifiable!=null && fieldMap!=null){
			for(Entry<String, Object> entry : fieldMap.entrySet()){
				Object value = FieldHelper.getInstance().read(identifiable, entry.getKey());
				String message = identifiable.getClass().getSimpleName()+" "+entry.getKey()+" is not correct";
				if(value==null)
					assertNull(message, entry.getValue());
				else{
					assertEquals(message,entry.getValue(), value);
				}
			}
		}
		return this;
	}
	
	public <T extends AbstractIdentifiable> TestCase assertIdentifiable(Class<T> identifiableClass,String code,String expectedName){
		Map<String,Object> map = new LinkedHashMap<>();
		map.put(FieldHelper.getInstance().buildPath(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME), expectedName);
		return assertIdentifiable(identifiableClass, code, map);
	}
	
	public TestCase assertPerson(String code,String expectedName,String expectedLastnames,String expectedSexCode,String expectedDateOfBirth,String expectedPlaceOfBirth){
		Person person = read(Person.class,code);
		assertIdentifiable(Person.class, code, expectedName);
		Map<String,Object> map = new LinkedHashMap<>();
		map.put(Person.FIELD_LASTNAMES, expectedLastnames);
		assertEquals("sex is not equals", read(Sex.class, expectedSexCode), person.getSex());
		Date d1 = new TimeHelper.Builder.String.Adapter.Default(expectedDateOfBirth).execute();
		
		assertEquals("year of birth is not equals", new DateTime(d1).getYear(), new DateTime(person.getBirthDate()).getYear());
		assertEquals("month of birth is not equals", new DateTime(d1).getMonth(), new DateTime(person.getBirthDate()).getMonth());
		assertEquals("day of birth is not equals", new DateTime(d1).getDay(), new DateTime(person.getBirthDate()).getDay());
		assertEquals("place of birth is not equals", expectedPlaceOfBirth, person.getBirthLocation() == null ? null : person.getBirthLocation().getOtherDetails());
		return assertIdentifiable(Person.class, code, map);
	}
	
	public <T extends AbstractIdentifiable> TestCase assertPersonRelarionship(String person1Code,String person1RoleCode,String person2RoleCode,String expectedPerson2Code){
		PersonRelationship personRelationship = inject(PersonRelationshipDao.class).readByPersonByRoleByOppositePerson(read(Person.class, person1Code)
				, read(PersonRelationshipTypeRole.class,person1RoleCode), read(Person.class,person2RoleCode));
		assertNotNull("relation ship does not exist", personRelationship);
		assertEquals("opposite role code is not correct", expectedPerson2Code, person1Code.equals(personRelationship.getExtremity1().getRole().getCode()) 
				? personRelationship.getExtremity2().getRole().getCode() : personRelationship.getExtremity1().getRole().getCode());
		return this;
	}
	
	public TestCase assertNestedSet(String setCode,String expectedRootCode,Long expectedChildrenCount){
		NestedSet set = read(NestedSet.class, setCode);
		assertEquals("root is not equal", StringUtils.isBlank(expectedRootCode) ? null : read(NestedSetNode.class, expectedRootCode), set.getRoot());
		assertEquals("children count is not equal", expectedChildrenCount, inject(NestedSetNodeDao.class).countBySet(set));
		return this;
	}
	
	public TestCase assertNestedSetNode(String nodeCode,String expectedSetCode,String expectedParentCode,Integer expectedLeftIndex,Integer expectedRightIndex,Long expectedDirectChildrenCount,Long expectedChildrenCount){
		NestedSetNode node = read(NestedSetNode.class, nodeCode);
		assertEquals("set is not equal", StringUtils.isBlank(expectedSetCode) ? null : read(NestedSet.class, expectedSetCode), node.getSet());
		assertEquals("parent is not equal", StringUtils.isBlank(expectedParentCode) ? null : read(NestedSetNode.class, expectedParentCode), node.getParent());
		assertEquals("left index is not equal", expectedLeftIndex, node.getLeftIndex());
		assertEquals("right index is not equal", expectedRightIndex, node.getRightIndex());
		assertEquals("direct children count is not equal", expectedDirectChildrenCount, inject(NestedSetNodeDao.class).countDirectChildrenByParent(node));
		assertEquals("children count is not equal", expectedChildrenCount, inject(NestedSetNodeDao.class).countByParent(node));
		return this;
	}
	
	public <T extends AbstractIdentifiable> TestCase assertParents(Class<T> aClass,String code,Integer levelLimitIndex,String...parentsCodes){
		T identifiable = read(aClass, code);
    	inject(BusinessInterfaceLocator.class).injectTyped(aClass).setParents(identifiable,levelLimitIndex);
    	List<AbstractIdentifiable> parents = new ArrayList<>();
    	if(ArrayHelper.getInstance().isNotEmpty(parentsCodes))
    		for(String index : parentsCodes)
    			parents.add(read(aClass, index));
    	assertList(parents, (List<AbstractIdentifiable>)identifiable.getParents());
    	return this;
	}
	
	public <T extends AbstractIdentifiable> TestCase assertParents(Class<T> aClass,String code,String...parentsCodes){
		return assertParents(aClass, code, null, parentsCodes);
	}
	
	public <T extends AbstractCollection<I>,I extends AbstractCollectionItem<T>> void assertCollection(Class<T> aClass,Class<I> itemClass,String collectionCode,String expectedNumberOfItem){
		assertCollection(aClass, itemClass, read(aClass, collectionCode), expectedNumberOfItem);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractCollection<I>,I extends AbstractCollectionItem<T>> TestCase assertCollection(Class<T> aClass,Class<I> itemClass,T collection,Object expectedNumberOfItem){
		assertEqualsNumber("number of "+itemClass.getSimpleName()+" is not equal", NumberHelper.getInstance().get(Long.class, expectedNumberOfItem), ((AbstractCollectionItemDao<I, T>)getPersistence(itemClass)).countByCollection(collection));
		return this;
	}
	
	public TestCase assertMovementCollection(String code,Object expectedValue,Object expectedNumberOfMovement){
    	assertMovementCollection(inject(MovementCollectionDao.class).read(code), expectedValue,expectedNumberOfMovement);
    	return this;
    }
	
	public TestCase assertMovementCollection(MovementCollection movementCollection,Object expectedValue,Object expectedNumberOfMovement){
    	assertEqualsNumber("Collection value",NumberHelper.getInstance().get(BigDecimal.class, expectedValue, null), movementCollection.getValue());
    	assertCollection(MovementCollection.class, Movement.class, movementCollection, expectedNumberOfMovement);
    	return this;
    }
	
	public TestCase assertMovement(String code,String expectedValue,String expectedCumul,Boolean increment,String expectedSupportingDocumentProvider,String expectedSupportingDocumentIdentifier){
    	assertMovement(read(Movement.class, code), expectedValue,expectedCumul, increment, expectedSupportingDocumentProvider, expectedSupportingDocumentIdentifier);
    	return this;
    }
	
	public TestCase assertMovement(String code,String expectedValue,String expectedCumul,Boolean increment){
		assertMovement(code, expectedValue,expectedCumul, increment, null, null);
		return this;
	}
	
	public TestCase assertMovement(String code,String expectedValue,String expectedCumul){
		assertMovement(code, expectedValue,expectedCumul, null);
		return this;
	}
	
	public TestCase assertMovement(Movement movement,String expectedValue,String expectedCumul,Boolean expectedIncrement,String expectedSupportingDocumentProvider,String expectedSupportingDocumentIdentifier){
		assertEqualsNumber("Movement value not equal",new BigDecimal(expectedValue), movement.getValue());
		assertEqualsNumber("Movement cumul not equal",new BigDecimal(expectedCumul), movement.getCumul());
    	assertEquals("Movement action not equal",expectedIncrement == null ? null : (Boolean.TRUE.equals(expectedIncrement) ? movement.getCollection().getType().getIncrementAction() : movement.getCollection().getType().getDecrementAction()), movement.getAction());
    	//assertEquals("Supporting Document Provider",expectedSupportingDocumentProvider, movement.getSupportingDocumentProvider());
    	//assertEquals("Supporting Document Identifier",expectedSupportingDocumentIdentifier, movement.getSupportingDocumentIdentifier());
    	return this;
    }
	
	public TestCase assertMovements(MovementCollection movementCollection,Collection<String[]> movementsArray){
		Movement.Filter filter = new Movement.Filter();
		filter.addMaster(movementCollection);
		final Collection<Movement> movements = inject(MovementDao.class).readByFilter(filter, null);
		//System.out.println("AbstractBusinessTestHelper.TestCase.assertMovements()");
		//for(Movement m :movements)
		//	System.out.println(m.getBirthDate()+" : "+m.getValue()+" : "+m.getCumul());
		
		new CollectionHelper.Iterator.Adapter.Default<String[]>(movementsArray){
			private static final long serialVersionUID = 1L;

			protected void __executeForEach__(String[] array) {
				if(ArrayHelper.getInstance().isNotEmpty(array)){
					Integer index = NumberHelper.getInstance().getInteger(array[0], 0);
					Movement movement = CollectionHelper.getInstance().getElementAt(movements, index);
					
					assertMovement(movement, array[1], array[2], array[3] == null ? null : Boolean.parseBoolean(array[3]), null, null);	
				}
			}
		}.execute();
		return this;
	}
	
	public TestCase assertMovements(String collectionCode,Collection<String[]> movementsArray){
		assertMovements(read(MovementCollection.class, collectionCode), movementsArray);
		return this;
	}
	
	public TestCase assertMovements(MovementCollection movementCollection,String[]...movementsArrays){
		if(ArrayHelper.getInstance().isNotEmpty(movementsArrays))
			assertMovements(movementCollection, Arrays.asList(movementsArrays));
		return this;
	}
	
	public TestCase assertMovements(String movementCollectionCode,String[]...movementsArrays){
		assertMovements(read(MovementCollection.class, movementCollectionCode), movementsArrays);
		return this;
	}
	
	public TestCase assertCreateMovements(Date identifiablePeriodBirthDate,Date identifiablePeriodDeathDate,Object[][] arrays){
    	String movementCollectionCode = getRandomHelper().getAlphabetic(5);
    	create(instanciateOne(MovementCollection.class,movementCollectionCode).setValue(BigDecimal.ZERO));
    	
    	String identifiablePeriodCollectionCode = getRandomHelper().getAlphabetic(5);
    	create(instanciateOne(IdentifiablePeriodCollection.class,identifiablePeriodCollectionCode));
    	
    	IdentifiablePeriod identifiablePeriod = instanciateOneWithRandomIdentifier(IdentifiablePeriod.class)
    			.setBirthDate(identifiablePeriodBirthDate).setDeathDate(identifiablePeriodDeathDate).setCollectionFromCode(identifiablePeriodCollectionCode);
		create(identifiablePeriod);
		
		for(Object[] array : arrays) {
			Movement movement = instanciateOne(Movement.class).setCollectionFromCode(movementCollectionCode)
	    			.setActionFromIncrementation(array[0] == null ? null : Boolean.parseBoolean((String)array[0]))
	    			.set__identifiablePeriod__(identifiablePeriod).setValue(NumberHelper.getInstance().get(BigDecimal.class, array[2],null));
			if(array[1]!=null){
				movement.__setBirthDateComputedByUser__(Boolean.TRUE);
				movement.setBirthDate((Date)array[1]);
			}
			create(movement);
	    	
	    	assertMovementCollection(movementCollectionCode,(String)array[3], (String)array[4]);
	    	assertMovements(movementCollectionCode, (String[][])array[5]);
		}
		
		return this;
    }
	
	 public TestCase assertComputedChanges(Movement movement,String previousCumul,String cumul){
    	if(movement.getCollection()==null || movement.getCollection().getValue()==null){
    		AbstractBusinessTestHelper.assertNull("expected movement previous cumul is not null",previousCumul); 
    		AbstractBusinessTestHelper.assertNull("actual movement previous cumul is not null",movement.getPreviousCumul());
    		
    		AbstractBusinessTestHelper.assertNull("expected movement cumul is not null",cumul);
    		AbstractBusinessTestHelper.assertNull("actual movement cumul is not null",movement.getCumul());
    	}else{
    		assertEqualsNumber("movement previous cumul is not equal",new BigDecimal(previousCumul), movement.getPreviousCumul());
    		
    		if(movement.getValue() == null){
    			AbstractBusinessTestHelper.assertNull("expected movement cumul is not null",cumul);
    			AbstractBusinessTestHelper.assertNull("actual movement cumul is not null",movement.getCumul());
    		}else{
    			assertEqualsNumber("movement cumul is not equal",new BigDecimal(cumul), movement.getCumul());
    		}
    	}
    	return this;
    }
	
	 public void assertOneMovementCollectionValuesTransferWithOneItemAndItsAcknowledgement(Object transferValue,Object acknownledgementValue){
    	countAll(Movement.class);
    	
    	String sourceMovementCollectionCode = getRandomAlphabetic();
    	create(instanciateOne(MovementCollection.class, sourceMovementCollectionCode));
    	
    	String tempMovementCollectionCode = getRandomAlphabetic();
    	create(instanciateOne(MovementCollection.class, tempMovementCollectionCode));
    	
    	String destinationMovementCollectionCode = getRandomAlphabetic();
    	create(instanciateOne(MovementCollection.class, destinationMovementCollectionCode));
    	
    	String movementsTransferCode = getRandomAlphabetic();
    	MovementCollectionValuesTransfer movementsTransfer = instanciateOne(MovementCollectionValuesTransfer.class,movementsTransferCode);
    	movementsTransfer.getItems().addBySourceMovementCollectionCodeByDestinationMovementCollectionCodeByValue(sourceMovementCollectionCode, tempMovementCollectionCode, transferValue);
    	create(movementsTransfer);
    	
    	assertCountAll(Movement.class,2);
    	
    	assertMovements(sourceMovementCollectionCode
    			, new String[]{"0","-"+transferValue,"-"+transferValue,"false"}
    	);
    	
    	assertMovements(tempMovementCollectionCode
    			, new String[]{"0",""+transferValue,""+transferValue,"true"}
    	);
    	
    	String movementsTransferAcknowledgementCode = getRandomAlphabetic();
    	MovementCollectionValuesTransferAcknowledgement movementsTransferAcknowledgement = instanciateOne(MovementCollectionValuesTransferAcknowledgement.class,movementsTransferAcknowledgementCode)
    			.setTransferFromCode(movementsTransferCode);
    	movementsTransferAcknowledgement.getItems().addBySourceMovementCollectionCodeByDestinationMovementCollectionCodeByValue(tempMovementCollectionCode, destinationMovementCollectionCode
    			, acknownledgementValue);
    	create(movementsTransferAcknowledgement);
    	
    	Number gap = NumberHelper.getInstance().subtract(NumberHelper.getInstance().get(transferValue),NumberHelper.getInstance().get(acknownledgementValue));
    	
    	if(NumberHelper.getInstance().isGreaterThanZero(gap)){
    		assertCountAll(Movement.class,6);
    	}else {
    		assertCountAll(Movement.class,4);
    	}
    	
    	assertMovements(sourceMovementCollectionCode
    			, new String[]{"0","-"+transferValue,"-"+transferValue,"false"}
    	);
    	
    	assertMovements(tempMovementCollectionCode
    			, new String[]{"0",""+transferValue,""+transferValue,"true"}
    			, new String[]{"1","-"+acknownledgementValue,""+gap,"false"}
    	);
    	
    	if(NumberHelper.getInstance().isGreaterThanZero(gap)){
    		Object sourceMovementCollectionValue = NumberHelper.getInstance().add(NumberHelper.getInstance().negate(NumberHelper.getInstance().get(transferValue)),gap);
    		assertMovementCollection(sourceMovementCollectionCode, sourceMovementCollectionValue, 2);
    		assertMovements(sourceMovementCollectionCode
        			, new String[]{"1",""+gap,""+sourceMovementCollectionValue,"true"}
        	);
    		
    		assertMovementCollection(tempMovementCollectionCode, 0, 3);
    		assertMovements(tempMovementCollectionCode
        			, new String[]{"2","-"+gap,"0","false"}
        	);
    	}
    	
    	assertMovements(destinationMovementCollectionCode
    			, new String[]{"0",""+acknownledgementValue,""+acknownledgementValue,"true"}
    	);
    	
    	clean();
    	deleteAll(Movement.class);
    } 
	 
	/**/
	
	public <T extends AbstractIdentifiable> void crud(final Class<T> aClass,T instance,Object[][] values){
		create(instance);
    	String code = inject(PersistenceInterfaceLocator.class).injectTyped(aClass).read(instance.getIdentifier()).getCode();
    	read(aClass,code);
    	//update(aClass,code, values);    	
    	deleteByCode(aClass,code);
    	clean();
    }
	
	/**/
	
	protected <T extends AbstractIdentifiable> TypedBusiness<T> getBusiness(Class<T> aClass) {
		return inject(BusinessInterfaceLocator.class).injectTyped(aClass);
	}
	
	protected <T extends AbstractIdentifiable> TypedDao<T> getPersistence(Class<T> aClass) {
		return inject(PersistenceInterfaceLocator.class).injectTyped(aClass);
	} 
	
	@SuppressWarnings("unchecked")
	@Override
	public void deleteInstances(Collection<?> instances) {
		super.deleteInstances(instances);
		inject(GenericBusiness.class).delete((Collection<AbstractIdentifiable>)instances);
	}

	/**/
	
	protected String toString(AbstractIdentifiable identifiable,Integer actionIdentifier){
		//if(actionIdentifier==EXISTENCE_PERIOD_FROM_DATE_IS_LESS_THAN)
		//	return identifiable.getCode()+"("+Constant.DATE_TIME_FORMATTER.format(identifiable.getBirthDate())+")";
		return null;
	}
	
	/**/
	
	protected static final Integer EXISTENCE_PERIOD_FROM_DATE_IS_LESS_THAN = 0;
}