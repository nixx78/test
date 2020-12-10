package lv.nixx.poc.mockito.finalservice;

public class InternalService {
	
	private final ExternalFinalService externalService;
	
	public InternalService(final ExternalFinalService externalService) {
		this.externalService = externalService;
	}

	public String doWork(String param) {
		return "result:" + externalService.doSomething(param);
	}
}