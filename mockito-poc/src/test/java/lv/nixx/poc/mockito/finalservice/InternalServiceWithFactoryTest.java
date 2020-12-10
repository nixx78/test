package lv.nixx.poc.mockito.finalservice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ExternalServiceFactory.class, ExternalFinalService.class })
public class InternalServiceWithFactoryTest {
    private final ExternalFinalService externalService = PowerMockito.mock(ExternalFinalService.class);
    private final ExternalServiceFactory externalServiceFactory;
    private final InternalServiceWithFactory internalService;

    public InternalServiceWithFactoryTest() throws Exception {
        PowerMockito.whenNew(ExternalFinalService.class)
                    .withNoArguments()
                    .thenReturn(externalService);

        externalServiceFactory = new ExternalServiceFactory();
        internalService = new InternalServiceWithFactory(externalServiceFactory);
    }

    @Before
    public void before() {
        Mockito.reset(externalService);
    }

    @Test
    public void doWorkTest() {
        internalService.doWork("100");

        Mockito.verify(externalService).doSomething("100");
    }
}