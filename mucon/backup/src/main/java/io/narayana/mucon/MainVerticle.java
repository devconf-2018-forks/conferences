package io.narayana.mucon;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

    private static FlightService flightService;

    public static void main(String[] args) {
        flightService = new FlightServiceImpl();

        Vertx.vertx().deployVerticle(MainVerticle.class.getName(), new DeploymentOptions().setInstances(10));
    }

    @Override
    public void start() {
        Router router = Router.router(vertx);

        router.post("/api").handler(request -> {
            flightService.createBooking("BA123");
            request.response().end(Thread.currentThread().getName() + ": Booking Count: " + flightService.getNumberOfBookings());
        });

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }

}
