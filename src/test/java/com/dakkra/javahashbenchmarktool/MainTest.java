package com.dakkra.javahashbenchmarktool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

	@Test
	void generateDaraBadSizeTest() {
		int sizeNegativeOne = -1;
		assertThrows( IllegalArgumentException.class, () -> Main.generateRandomData( sizeNegativeOne ) );

		int sizeZero = 0;
		assertThrows( IllegalArgumentException.class, () -> Main.generateRandomData( sizeZero ) );
	}

	@Test
	void generateDataLengthTest() {
		int size = 1024;
		String result = Main.generateRandomData( size );
		assertEquals( size, result.length() );

		size = 512;
		result = Main.generateRandomData( size );
		assertEquals( size, result.length() );

		size = 1;
		result = Main.generateRandomData( size );
		assertEquals( size, result.length() );
	}
}