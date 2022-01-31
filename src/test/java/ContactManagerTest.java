import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// makes sure test class was instantiated only once
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTest {

    ContactManager contactManager;

    // @BeforeAll, @BeforeEach - used to perform initialization tasks for tests; runs first
    // @AfterAll, @AfterEach - used for cleanup tasks for tests
    @BeforeAll
    public void setupAll(){
        System.out.println("Should print before all tests");
    }

    // This part is really cool! Removes any redundant initializations
    @BeforeEach
    public void setup(){
        contactManager = new ContactManager();
    }

    @Test
    // Is the list empty after we try to create a contact?
    public void shouldCreateContact(){
        contactManager.addContact("Homi","Bodhanwala", "0123456789");

        // Checks if the list is empty
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        // Checks if the list is the correct size after insertion
        Assertions.assertEquals(1,contactManager.getAllContacts().size());
        // Checks if the data we entered was stored in the list correctly
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .anyMatch(contact -> contact.getFirstName().equals("Homi") &&
                        contact.getLastName().equals("Bodhanwala") &&
                        contact.getPhoneNumber().equals("0123456789")));
    }

    @Test
    @DisplayName("Should Not Create Contact When First Name is Null")
    public void shouldThrowExceptionFirstNameNull(){
        // This assert makes sure the correct exception was thrown
        Assertions.assertThrows(RuntimeException.class,() -> {
            contactManager.addContact(null,"Bodhanwala", "0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When Last Name is Null")
    public void shouldThrowExceptionLastNameNull(){
        Assertions.assertThrows(RuntimeException.class,() -> {
            contactManager.addContact("Homi", null, "0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When Phone Number is Null")
    public void shouldThrowExceptionPhoneNoNull(){
        Assertions.assertThrows(RuntimeException.class,() -> {
            contactManager.addContact("Homi", "Bodhanwala", null);
        });
    }

    @AfterEach
    public void tearDown(){
        System.out.println("Should execute after each test");
    }

    @AfterAll
    public void tearDownAll(){
        System.out.println("Should be executed at the end of testing");
    }

    // Platform Specific Tests!
    @Test
    @DisplayName("Should Create contact only on Mac OS ")
    @EnabledOnOs(value = OS.MAC, disabledReason = "Enabled only on MAC OS")
    public void shouldCreateContactOnlyOnMac(){
        contactManager.addContact("Homi","Bodhanwala", "0123456789");

        // Checks if the list is empty
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        // Checks if the list is the correct size after insertion
        Assertions.assertEquals(1,contactManager.getAllContacts().size());
        // Checks if the data we entered was stored in the list correctly
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .anyMatch(contact -> contact.getFirstName().equals("Homi") &&
                        contact.getLastName().equals("Bodhanwala") &&
                        contact.getPhoneNumber().equals("0123456789")));
    }

    @Test
    @DisplayName("Should Not Create Contact On Windows OS ")
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disabled only on Windows OS")
    public void shouldNotCreateContactOnlyOnWindows(){
        contactManager.addContact("Homi","Bodhanwala", "0123456789");

        // Checks if the list is empty
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        // Checks if the list is the correct size after insertion
        Assertions.assertEquals(1,contactManager.getAllContacts().size());
        // Checks if the data we entered was stored in the list correctly
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .anyMatch(contact -> contact.getFirstName().equals("Homi") &&
                        contact.getLastName().equals("Bodhanwala") &&
                        contact.getPhoneNumber().equals("0123456789")));
    }

    // This test would only work on my specific machine
    @Test
    @DisplayName("Test Contact Creation on Developer Machine")
    public void shouldTestContactCreationOnDEV(){
        // Change DEV to something else to make the assumption false.
        // To make "DEV" work, edit build config to: -ea -DENV=DEV
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));
        contactManager.addContact("Homi","Bodhanwala", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }

    // Repeated Tests:
    @DisplayName("Repeat Contact Creation Test 5 times")
    @RepeatedTest(value = 5, name = "Repeating Contact Creation Test " +
                                    "{currentRepetition} of {totalRepetitions}")
    public void shouldTestContactCreationRepeatedly(){
        contactManager.addContact("Homi","Bodhanwala", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }

    // Parameterized Testing
    @DisplayName("Contact Creation Using Different Parameters")
    @ParameterizedTest
    @ValueSource(strings = {"0123456789", "0123456789", "0123456789"})
    public void shouldTestContactCreationUsingValueSource(String phoneNumber){
        contactManager.addContact("Homi","Bodhanwala", phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }

    // Parameterized Testing w/ Method Source
    @DisplayName("Contact Creation Using Method Source")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void shouldTestContactCreationUsingMethodSource(String phoneNumber){
        contactManager.addContact("Homi","Bodhanwala", phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }
    // method used in the test above ^^^
    private static List<String> phoneNumberList(){
        return Arrays.asList("0123456789", "0123456987", "0123456897");
    }

    // Parameterized Testing w/ CSV Source
    @DisplayName("Contact Creation Using Method Source")
    @ParameterizedTest
    @CsvSource({"0123456789", "0123456987", "0123456897"})
    public void shouldTestContactCreationUsingCSVSource(String phoneNumber){
        contactManager.addContact("Homi","Bodhanwala", phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }

    // Parameterized Testing w/ CSV file Source
    @DisplayName("Contact Creation Using Method Source")
    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void shouldTestContactCreationUsingCSVFileSource(String phoneNumber){
        contactManager.addContact("Homi","Bodhanwala", phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1,contactManager.getAllContacts().size());
    }

}