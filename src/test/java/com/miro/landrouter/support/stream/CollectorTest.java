package com.miro.landrouter.support.stream;

import java.util.List;
import java.util.stream.Stream;

import com.miro.landrouter.util.stream.Collector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Data;

class CollectorTest {

	@Test
	void testEmpty() {
		Assertions.assertTrue(
			Stream.empty()
				.collect(Collector.reversed())
				.isEmpty());
	}

	@Test
	void testIntegers() {
		Assertions.assertEquals(
			List.of(3, 2, 1),
			List.of(1, 2, 3)
				.stream()
				.collect(Collector.reversed()));
	}

	@Test
	void testObjects() {
		Assertions.assertEquals(
			List.of(new Pojo(3, "three"), new Pojo(2, "two"), new Pojo(1, "one")),
			List.of(new Pojo(1, "one"), new Pojo(2, "two"), new Pojo(3, "three"))
				.stream()
				.collect(Collector.reversed()));
	}

	@Data
	@AllArgsConstructor
	static class Pojo {
		int id;
		String name;
	}
}