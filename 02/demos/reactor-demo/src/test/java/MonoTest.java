import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class MonoTest {
    
    @Test
    void firstMono() {
        Mono.just("A")
            .log()
            .subscribe();
    }
    
    @Test
    void monoWithConsumer() {
        Mono.just("A")
            .log()
            .subscribe(System.out::println);
    }
    
    @Test
    void monoWithDoOn() {
        Mono.just("Mon(a)d?")
            .log()
            .doOnSubscribe(sub -> System.out.println("Subscribed: " + sub))
            .doOnRequest(req -> System.out.println("Request: " + req))
            .doOnSuccess(res -> System.out.println("Complete: " + res))
            .subscribe(System.out::println);
    }
    
    @Test
    void emptyMono() {
        Mono.empty()
            .log()
            .subscribe(System.out::println);
    }
    
    @Test
    void emptyCompleteConsumerMono() {
        Mono.empty()
            .log()
            .subscribe(System.out::println,
                       null,
                       () -> System.out.println("Done")
            );
    }
    
    @Test
    void errorRuntimeExceptionMono() {
        Mono.error(new RuntimeException())
            .log()
            .subscribe();
    }
    
    @Test
    void errorExceptionMono() {
        Mono.error(new Exception())
            .log()
            .subscribe();
    }
    
    @Test
    void errorConsumerMono() {
        Mono.error(new Exception())
            .log()
            .subscribe(System.out::println,
                       Throwable::printStackTrace
            );
    }
    
    @Test
    void errorDoOnErrorMono() {
        Mono.error(new Exception())
            .doOnError(e -> System.out.println("Error: " + e))
            .log()
            .subscribe();
    }
    
    @Test
    void errorOnErrorResumeMono() {
        Mono.error(new Exception())
            .onErrorResume(e -> {
                System.out.println("Caught: " + e);
                return Mono.just("B");
            })
            .log()
            .subscribe();
    }
    
    @Test
    void errorOnErrorReturnMono() {
        Mono.error(new Exception())
            .onErrorReturn("B")
            .log()
            .subscribe();
    }
}
