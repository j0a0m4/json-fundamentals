import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;

public class FluxTest {
    
    @Test
    void firstFlux() {
        Flux.just("LIGHTS", "Siberia", "Flux and Flow")
            .log()
            .subscribe();
    }
    
    @Test
    void fluxFromIterable() {
        Flux.fromIterable(Arrays.asList("Peace Sign", "Toes", "Cactus In The Valley"))
            .log()
            .subscribe();
    }
    
    @Test
    void fluxFromRange() {
        Flux.range(10, 5)
            .log()
            .subscribe();
    }
    
    @Test
    void fluxFromInterval() throws Exception {
        Flux.interval(Duration.ofSeconds(1))
            .log()
            .take(2)
            .subscribe();
        
        Thread.sleep(5000);
    }
    
    @Test
    void fluxRequest() {
        Flux.range(1, 5)
            .log()
            .subscribe(null,
                       null,
                       null,
                       subscription -> subscription.request(3)
            );
    }
    
    @Test
    void fluxLimitRate() {
        Flux.range(1, 5)
            .log()
            .limitRate(3)
            .subscribe();
    }
}
