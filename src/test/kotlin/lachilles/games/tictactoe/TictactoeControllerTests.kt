package lachilles.games.tictactoe

import com.fasterxml.jackson.databind.ObjectMapper
import lachilles.games.tictactoe.api.GameResponse
import lachilles.games.tictactoe.api.PlayerResponse
import lachilles.games.tictactoe.api.TicTacToeController
import lachilles.games.tictactoe.service.TicTacToeService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
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
        assertTrue(gameWithPlayers.players.contains(PlayerResponse("lianne", 1)))
        assertTrue(gameWithPlayers.players.contains(PlayerResponse("paul", 2)))

        val g = getGameState(game.id)
        assertEquals(gameWithPlayers, g)
    }

    @Test
    fun testTakeTurn() {
        val game = createGame()
        addPlayerToGame(game, "lianne")
        addPlayerToGame(game, "paul")
        val game2 = takeTurn(game.id, 1,1,1)
        println(game2.board)
        assertEquals("X", game2.board?.elements?.get(4)?.display)
    }

    @Test
    fun testDetectWinner() {
        val game = createGame()
        addPlayerToGame(game, "lianne")
        addPlayerToGame(game, "paul")
        takeTurn(game.id, 1,1,1)
        takeTurn(game.id, 1,2,2)
        takeTurn(game.id, 0,0,1)
        takeTurn(game.id, 1,0,2)
        val endGame = takeTurn(game.id, 2,2,1)
        println(endGame.board)
        assertEquals("lianne", endGame.winner?.name)
        mockMvc.perform(put("/takeTurn/${endGame.id}/2/0?playerId=2").contentType
        ("application/json"))
                .andExpect(status().is4xxClientError())
                .andReturn()
    }

    @Test
    fun testSamePlayerTakesTurn() {
        val game = createGame()
        addPlayerToGame(game, "lianne")
        addPlayerToGame(game, "paul")
        takeTurn(game.id, 1,1,1)
        mockMvc.perform(put("/takeTurn/${game.id}/1/1?playerId=1").contentType
        ("application/json"))
                .andExpect(status().is4xxClientError())
                .andReturn()
    }

    @Test
    fun testPlayerTakesSameMove() {
        val game = createGame()
        addPlayerToGame(game, "lianne")
        addPlayerToGame(game, "paul")
        takeTurn(game.id, 1,1,1)
        mockMvc.perform(put("/takeTurn/${game.id}/1/1?playerId=2").contentType
        ("application/json"))
                .andExpect(status().is4xxClientError())
                .andReturn()
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

    private fun getGameState(gameId: String): GameResponse {
        val result = mockMvc.perform(get("/getGameState/${gameId}")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
        return objectMapper.readValue<GameResponse>(result.response.contentAsString, GameResponse::class.java)
    }

    private fun takeTurn(gameId: String, row: Int, col: Int, playerId: Int): GameResponse {
        val result = mockMvc.perform(put("/takeTurn/${gameId}/${row}/${col}?playerId=$playerId")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
        return objectMapper.readValue<GameResponse>(result.response.contentAsString, GameResponse::class.java)
    }
}
