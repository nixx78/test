package lv.nixx.poc.mockito.finalservice;

public class InternalServiceWithFactory {

	private final ExternalServiceFactory externalServiceFactory;

	public InternalServiceWithFactory(final ExternalServiceFactory externalServiceFactory) {
		this.externalServiceFactory = externalServiceFactory;
	}

	public void doWork(String param) {
		externalServiceFactory.createExternalService().doSomething(param);
	}
	
}