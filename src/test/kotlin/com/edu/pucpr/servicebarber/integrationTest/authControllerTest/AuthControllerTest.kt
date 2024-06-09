package com.edu.pucpr.servicebarber.integrationTest.authControllerTest

import com.edu.pucpr.servicebarber.dtos.SignInDTO
import com.edu.pucpr.servicebarber.dtos.SignInResponseDTO
import com.edu.pucpr.servicebarber.integrationTest.config.TestConfig
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("local")
@TestMethodOrder(OrderAnnotation::class)
class AuthenticationControllerTest {

    companion object {
        private const val SIGN_IN = "/api/auth/v1/sign-in"
        private const val AUTH_BY_REFRESH_TOKEN = "/api/auth/v1/sign-up-with-refresh-token"
        private lateinit var tokenADMIN: SignInResponseDTO
    }

    @Test
    @Order(0)
    fun authenticationByUsernameAndPassword() {
        tokenADMIN = given()
            .basePath(SIGN_IN)
            .port(TestConfig.SERVER_PORT)
            .contentType(TestConfig.CONTENT_TYPE_JSON)
            .body(SignInDTO("admin", "admin"))
            .`when`()
            .post()
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(SignInResponseDTO::class.java)

        validateSignResponseDTO()
    }

    @Test
    @Order(1)
    fun authenticationByRefreshToken() {
        tokenADMIN = given()
            .param("refreshToken", tokenADMIN.refreshToken)
            .post("http://localhost:4100/api/auth/v1/sign-up-with-refresh-token")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(SignInResponseDTO::class.java)

        validateSignResponseDTO()
    }

    @Test
    @Order(2)
    fun authenticationByUsernameAndPasswordInvalid() {
        val content = given()
            .basePath(SIGN_IN)
            .port(TestConfig.SERVER_PORT)
            .contentType(TestConfig.CONTENT_TYPE_JSON)
            .body(SignInDTO("invalidusername", "invalidpassword"))
            .`when`()
            .post()
            .then()
            .statusCode(401)
            .extract()
            .body()
            .asString()

        assertTrue(content.contains("invalid username/password supplied!"))
    }

    private fun validateSignResponseDTO() {
        assertNotNull(tokenADMIN)
        assertNotNull(tokenADMIN.username)
        assertTrue(tokenADMIN.authenticated)
        assertNotNull(tokenADMIN.created)
        assertNotNull(tokenADMIN.expiration)
        assertNotNull(tokenADMIN.accessToken)
        assertNotNull(tokenADMIN.refreshToken)
    }
}
