package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

	static Object[][] sulfurasData() {
		return new Object[][] { { new Item("Sulfuras, Hand of Ragnaros", 0, 80) },
				{ new Item("Sulfuras, Hand of Ragnaros", 10, 80) },
				{ new Item("Sulfuras, Hand of Ragnaros", -5, 80) } };
	}

	@ParameterizedTest
	@MethodSource("sulfurasData")
	void test_parametrizado_Sulfuras(Item item) {
		Item[] items = new Item[] { item };
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		assertAll("Validar propiedades", () -> assertEquals(item.name, app.items[0].name),
				() -> assertEquals(item.sellIn, app.items[0].sellIn),
				() -> assertEquals(item.quality, app.items[0].quality));
	}

	// Test sin controlar las excepciones
	// Se pasea por todas las partes de que están mencionadas en el Item[]
	@Test
	void AllTest() {
		Item[] items = new Item[] { new Item("+5 Dexterity Vest", 10, 20), //
				new Item("Aged Brie", 2, 0), //
				new Item("Elixir of the Mongoose", 5, 7), //
				new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
				new Item("Sulfuras, Hand of Ragnaros", -1, 80),
				new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
				new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
				new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
				new Item("Conjured Mana Cake", 3, 6) };

		GildedRose app = new GildedRose(items);
		app.updateQuality();
	}
	
	

}