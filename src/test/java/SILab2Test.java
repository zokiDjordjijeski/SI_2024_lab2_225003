import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class SILab2Test {
    private Item createItem(String name, String barcode, int price, float discount) {
        return new Item(name, barcode, price, discount);
    }

    @Test
    public void testEveryBranch() {
        List<Item> items;
        boolean result;

        // Test case: allItems list is null
        try {
            SILab2.checkCart(null, 100);
            fail("Expected RuntimeException for null allItems list");
        } catch (RuntimeException e) {
            assertEquals("allItems list can't be null!", e.getMessage());
        }

        // Test case: item with null name
        items = Arrays.asList(createItem(null, "0123456789", 100, 0.0f));
        result = SILab2.checkCart(items, 100);
        assertTrue(result);
        assertEquals("unknown", items.get(0).getName());

        // Test case: item with invalid barcode
        items = Arrays.asList(createItem("Item1", "01234A6789", 100, 0.0f));
        try {
            SILab2.checkCart(items, 100);
            fail("Expected RuntimeException for invalid barcode");
        } catch (RuntimeException e) {
            assertEquals("Invalid character in item barcode!", e.getMessage());
        }

        // Test case: item without barcode
        items = Arrays.asList(createItem("Item1", null, 100, 0.0f));
        try {
            SILab2.checkCart(items, 100);
            fail("Expected RuntimeException for no barcode");
        } catch (RuntimeException e) {
            assertEquals("No barcode!", e.getMessage());
        }

        // Test case: item with discount
        items = Arrays.asList(createItem("Item1", "0123456789", 100, 0.1f));
        result = SILab2.checkCart(items, 90);
        assertTrue(result);

        // Test case: item with price > 300 and discount
        items = Arrays.asList(createItem("Item1", "0123456789", 400, 0.1f));
        result = SILab2.checkCart(items, 320);
        assertTrue(result);

        // Test case: payment less than total price after discount
        items = Arrays.asList(createItem("Item1", "0123456789", 100, 0.0f));
        result = SILab2.checkCart(items, 80);
        assertFalse(result);

        // Test case: valid items and payment is enough
        items = Arrays.asList(createItem("Item1", "0123456789", 100, 0.0f));
        result = SILab2.checkCart(items, 100);
        assertTrue(result);
    }

    @Test
    public void testMultipleCondition() {
        List<Item> items;
        boolean result;

        // Test case: all conditions false
        items = Arrays.asList(createItem("Item1", "1123456789", 100, 0.0f));
        result = SILab2.checkCart(items, 100);
        assertTrue(result);

        // Test case: first condition true, others false
        items = Arrays.asList(createItem("Item1", "0123456789", 301, 0.0f));
        result = SILab2.checkCart(items, 301);
        assertTrue(result);

        // Test case: first and second conditions true, third false
        items = Arrays.asList(createItem("Item1", "0123456789", 301, 1.0f));
        result = SILab2.checkCart(items, 300);
        assertTrue(result);

        // Test case: all conditions true
        items = Arrays.asList(createItem("Item1", "0123456789", 301, 1.0f));
        result = SILab2.checkCart(items, 270);
        assertTrue(result);
    }
}
