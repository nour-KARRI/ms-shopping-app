package com.nour.ms.order;

import com.nour.ms.order.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

import static org.hamcrest.MatcherAssert.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

	String requestBody;
	@ServiceConnection
	static MySQLContainer  mySQLContainer = new MySQLContainer<>("mysql:8.3.0");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static{
		mySQLContainer.start();
	}

	@AfterEach
	void tearDown() {
		requestBody = "";
	}

	/*@Test
	void shouldPlaceOrder() {

		System.out.println("requestBody should pass: "+ requestBody);
	 requestBody = """
				   {
			          "skuCode":"1111",
			          "price":"50",
			          "quantity":1,
			          "userDetails":{
			          	"email":"nour@atos.net"
			          }
				   }
			""";

	InventoryClientStub.stubInventoryCall("1111", 1);

	var responseBodyString = RestAssured.given()
			.contentType("application/json")
			.body(requestBody)
			.when()
			.post("/api/order")
			.then()
			.statusCode(201)
			.extract()
			.body().asString();

	assertThat(responseBodyString, Matchers.is("Order placed Successfully"));
	}*/


	@Test
	void shouldFailedWhenProductIsNotInStock() {

		System.out.println("requestBody failed: "+ requestBody);

		String requestBody = """
				   {
			          "skuCode":"1111",
			          "price":"23",
			          "quantity":2025,
			          "userDetails":{
			          	"email":"nour@atos.net"
			          }
				   }
			""";

		InventoryClientStub.failedStubInventoryCall("1111", 2025);

		var responseBodyString = RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/order")
				.then()
				.statusCode(500)
				.extract()
				.body().asString();

		assertThat(responseBodyString, Matchers.is("Ordering failed"));
	}

}
