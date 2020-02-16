/*
 * Copyright (c) 2019, LSafer, All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * -You can edit this file (except the header).
 *  -If you have change anything in this file. You
 *    shall mention that this file has been edited.
 *    By adding a new header (at the bottom of this header)
 *    with the word "Editor" on top of it.
 */

package cufy.beans;

import cufy.lang.Value;
import org.cufy.lang.JSONConverter;
import org.junit.Assert;
import org.junit.Test;

import static cufy.beans.Bean.Property.*;

@SuppressWarnings({"JavaDoc"})
public class BeanTest {
	@Test
	public void forInstance_getNull_remove() {
		Object object = new Object() {
			@Bean.Property(onRemove = DEFAULT, defaultValue = @Value(value = "12", type = Integer.class, converter = JSONConverter.class))
			private Integer defaultRemove = 0;

			@Bean.Property(onGetNull = DEFAULT, defaultValue = @Value(value = "14", type = Integer.class, converter = JSONConverter.class))
			private Integer nullRemove = 0;

			@Bean.Property(onRemove = THROW, onGetNull = THROW)
			private Integer throwRemove = null;
		};
		Bean<Object, Object> bean = Bean.forInstance(object);

		bean.remove("defaultRemove");
		Assert.assertEquals("not set to default value", 12, bean.get("defaultRemove"));

		bean.remove("nullRemove");
		Assert.assertEquals("expecting the default value ", 14, bean.get("nullRemove"));

		try {
			bean.remove("throwRemove");
			Assert.fail("expecting throwing an exception as specified");
		} catch (UnsupportedOperationException ignored) {
		}

		try {
			bean.get("throwRemove");
			Assert.fail("expecting throwing an exception as specified");
		} catch (IllegalArgumentException ignored) {
		}
	}

	@Test
	public void forInstance_put_get_size() {
		Object object = new Object() {
			@Bean.Property(key = @Value(value = "false", type = Boolean.class, converter = JSONConverter.class), onTypeMismatch = CONVERT, converter = JSONConverter.class)
			private int property0 = 90;
		};
		Bean<Object, Object> bean = Bean.forInstance(object);

		bean.put(false, "700");

		//state
		Assert.assertEquals("Wrong size calc", 1, bean.size());

		//key = false
		Assert.assertNotNull("Field value can't be reached", bean.get(false));
		Assert.assertNotEquals("Field value not update", 90, bean.get(false));
		Assert.assertEquals("Field value stored wrongly", 700, bean.get(false));
	}

	@Test
	public void getNull_remove() {
		Bean<Object, Object> bean = new Bean<Object, Object>() {
			@Property(onRemove = DEFAULT, defaultValue = @Value(value = "12", type = Integer.class, converter = JSONConverter.class))
			private Integer defaultRemove = 0;

			@Property(onGetNull = DEFAULT, defaultValue = @Value(value = "14", type = Integer.class, converter = JSONConverter.class))
			private Integer nullRemove = 0;

			@Property(onRemove = THROW, onGetNull = THROW)
			private Integer throwRemove = null;
		};

		bean.remove("defaultRemove");
		Assert.assertEquals("not set to default value", 12, bean.get("defaultRemove"));

		bean.remove("nullRemove");
		Assert.assertEquals("expecting the default value ", 14, bean.get("nullRemove"));

		try {
			bean.remove("throwRemove");
			Assert.fail("expecting throwing an exception as specified");
		} catch (UnsupportedOperationException ignored) {
		}

		try {
			bean.get("throwRemove");
			Assert.fail("expecting throwing an exception as specified");
		} catch (IllegalArgumentException ignored) {
		}
	}

	@Test()
	public void struct_put_get_size() {
		Bean<Object, Object> bean = new Bean<Object, Object>() {
			@Property(key = @Value(value = "false", type = Boolean.class, converter = JSONConverter.class), onTypeMismatch = CONVERT, converter = JSONConverter.class)
			private int property0 = 90;
		};

		bean.put(false, "700");

		//state
		Assert.assertEquals("Wrong size calc", 1, bean.size());

		//key = false
		Assert.assertNotNull("Field value can't be reached", bean.get(false));
		Assert.assertNotEquals("Field value not update", 90, bean.get(false));
		Assert.assertEquals("Field value stored wrongly", 700, bean.get(false));
	}
}
