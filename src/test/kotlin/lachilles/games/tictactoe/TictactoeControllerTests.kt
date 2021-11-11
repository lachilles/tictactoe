package lachilles.games.tictactoe

import com.fasterxml.jackson.databind.ObjectMapper
import lachilles.games.tictactoe.service.TicTacToeService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.beans.BeanProperty
import java.util.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [TicTacToeController::class, TicTacToeService::class])
class TictactoeControllerTests {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper


    // assert that a gameId was returned using a map with valid gameIds
    // add createPlayer method add playernames to the data object.
    @Test
    fun testCreate() {
        val game = createGame()
        assertNotNull(UUID.fromString(game.id))
        assertTrue(game.players.isEmpty())
    }

    @Test
    fun testCreateAndAddTwoPlayers() {
        val game = createGame()
        addPlayerToGame(game, "lianne")
        val gameWithPlayers = addPlayerToGame(game, "paul")
        assertTrue(gameWithPlayers.players.contains(PlayerResponse("lianne")))
        assertTrue(gameWithPlayers.players.contains(PlayerResponse("paul")))
    }

    @Test
    fun testCreateAndAddTwoLiannes() {
        val game = createGame()
        addPlayerToGame(game, "lianne")
        mockMvc.perform(post("/createPlayer/${game.id}?playerName=lianne").contentType("application/json"))
                .andExpect(status().is4xxClientError())
                .andReturn()
    }

    @Test
    fun testAddPlayerToInvalidGame() {
        mockMvc.perform(post("/createPlayer/0325-32452-262626-2362?playerName=lianne").contentType("application/json"))
                .andExpect(status().is4xxClientError())
                .andReturn()
    }

    @Test
    fun testAddThreePlayers() {
        val game = createGame()
        addPlayerToGame(game, "lianne")
        addPlayerToGame(game, "paul")
        mockMvc.perform(post("/createPlayer/${game.id}?playerName=john").contentType("application/json"))
                .andExpect(status().is4xxClientError())
                .andReturn()
    }

    private fun createGame() : GameResponse {
        val result = mockMvc.perform(post("/createGame").contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
        return objectMapper.readValue<GameResponse>(result.response.contentAsString, GameResponse::class.java)
    }

    private fun addPlayerToGame(game: GameResponse, playerName: String) : GameResponse {
        val result = mockMvc.perform(post("/createPlayer/${game.id}?playerName=$playerName").contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
        return objectMapper.readValue<GameResponse>(result.response.contentAsString, GameResponse::class.java)

    }
}
