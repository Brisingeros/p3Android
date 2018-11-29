package dadm.scaffold.dummy;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dadm.scaffold.R;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Integer, DummyItem> ITEM_MAP = new HashMap<>();

    static {

            addItem(createDummyItem(R.drawable.ship,"ship1"));
            addItem(createDummyItem(R.drawable.ship2,"ship2"));
            addItem(createDummyItem(R.drawable.ship7,"ship3"));
            addItem(createDummyItem(R.drawable.ship4,"ship4"));
            addItem(createDummyItem(R.drawable.ship5,"ship5"));
            addItem(createDummyItem(R.drawable.ship10,"ship6"));

    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int imageId, String nameShip) {
        return new DummyItem(imageId, nameShip);
    }
    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final int id;
        public String name;


        public DummyItem(int image, String n) {
            this.id = image;
            this.name = n;
        }

        @Override
        public String toString() {
            return this.name + ": " + String.valueOf(id);
        }
    }
}
