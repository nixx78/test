package lv.nixx.poc.mockito.statictest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ClassWithStaticMethod.class)
public class StaticMethodsTest {

	@Test
	public void test(){
        PowerMockito.stub(PowerMockito.method(ClassWithStaticMethod.class, "getValue")).toReturn("mappedValueFor20");
        assertEquals("#mappedValueFor20#", ClassWithStaticMethod.map("20"));
	}
	

}
