package racing

import racing.application.RacingCarFactory
import racing.participant.RacingGroup
import racing.result.GameResult
import racing.result.toGameResult


class RacingGame(
    private val cars: RacingGroup
) {
    private var result: GameResult? = null

    fun start(roundCount: Int) {
        if (result != null) {
            throw IllegalStateException("이미 수행한 게임입니다.")
        }

        this.result = (1..roundCount)
            .map { round -> cars.race(round) }
            .toGameResult()
    }

    fun getGameResult(): GameResult {
        return result ?: throw IllegalStateException("게임을 진행하지 않았습니다.")
    }
}

fun main() {
    val form = InputView.writeApplicationForm()
    val group = RacingCarFactory.generate(form)

    val game = RacingGame(group)
    game.start(InputView.inputRunCount())

    OutputView.printResult(game.getGameResult())
}
