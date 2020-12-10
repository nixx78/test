package lv.nixx.poc.mockito.finalservice;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ExternalFinalService.class })
public class InternalServiceTest {
	
    private final ExternalFinalService externalService = PowerMockito.mock(ExternalFinalService.class);
    private final InternalService internalService = new InternalService(externalService);

    @Before
    public void before() {
        reset(externalService);
    }

    @Test
    public void doWorkTest() {
        when(externalService.doSomething("1")).thenReturn("100");
        when(externalService.doSomething("2")).thenReturn("200");

        String r = internalService.doWork("1");
        
        verify(externalService).doSomething("1");
        assertEquals("result:100", r);
        
        r = internalService.doWork("2");
        assertEquals("result:200", r);

    }
}